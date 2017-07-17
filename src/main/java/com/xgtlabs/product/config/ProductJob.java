package com.xgtlabs.product.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.xgtlabs.product.bo.CatalogBO;
import com.xgtlabs.product.job.processor.CustomItemProcessor;
import com.xgtlabs.product.listener.DataBaseToCsvStepListener;


/**
 * Configuration du batch
 * 
 * @author javadev
 *
 */
@Configuration
public class ProductJob {

	private static final String SQL_CATALOG = ""
		+ "SELECT prod.id AS product_id, "
		+ "       prod.description AS product_description, "
		+ "       prod.product_image, "
		+ "       cait.id AS catalog_item_id, "
		+ "       cait.item_number, "
		+ "       cait.price "
		+ "FROM product.product AS prod "
		+ "INNER JOIN product.catalog_item AS cait ON prod.id = cait.fk_product_id "
		+ "INNER JOIN product.genre AS genr ON cait.fk_genre_id = genr.id "
		+ "WHERE 1 = 1";
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	Environment environment;
	
	@Value("${chunk.size}")
	private Integer chunkSize;
	
	/**
	 * lecture en base de données
	 * 
	 * @return 
	 */
	@Bean(destroyMethod="")
	public ItemReader<CatalogBO> jdbcItemReader() {
		
		JdbcCursorItemReader<CatalogBO> reader = new JdbcCursorItemReader<CatalogBO>();
		reader.setDataSource(this.dataSource);
		reader.setRowMapper(new BeanPropertyRowMapper<CatalogBO>(CatalogBO.class));
		reader.setSql(SQL_CATALOG);
		
		return reader;
		
	}
	
	/**
	 * définiton de règle de gestion
	 * 
	 * @return CustomItemProcessor
	 */
	@Bean(destroyMethod="")
	public ItemProcessor<CatalogBO, CatalogBO> processor() {
		return new CustomItemProcessor();
	}
	
	/**
	 * ecriture du fichier csv
	 * 
	 * @return StaxEventItemWriter<Report>
	 */
	@Bean(destroyMethod="")
	public ItemWriter<CatalogBO>csvItemWriter() {
		
		FlatFileItemWriter<CatalogBO> writer = new FlatFileItemWriter<CatalogBO>();
		writer.setResource(new FileSystemResource(this.environment.getRequiredProperty("csv.out.file")));
		writer.setLineAggregator(this.lineAggregator());
		
		return writer;
	}
	

	/**
	 * renvoie la step dataBaseToXmlStep
	 * 
	 * @return Step
	 */
	@Bean(destroyMethod="")
	public Step dataBaseToCsvStep() {
		return this.stepBuilderFactory.get("dataBaseToCsvStep")
				.<CatalogBO, CatalogBO>chunk(this.chunkSize)
				.reader(this.jdbcItemReader())
				.processor(this.processor())
				.writer(this.csvItemWriter())
				.listener(this.dataBaseToCsvStepListener())
				.build();
	}
	
	/**
	 * renvoie le Job dataBaseToCsvJob
	 * 
	 * @return Job
	 */
	@Bean(destroyMethod="")
	public Job dataBaseToCsvJob() {
		return this.jobBuilderFactory.get("dataBaseToCsvJob")
				.incrementer(new RunIdIncrementer())
				.flow(this.dataBaseToCsvStep())
				.end()
				.build();
	}
	
	/**
	 * 	
	 * @return lineAggregator
	 */
	private LineAggregator<CatalogBO> lineAggregator() {
		DelimitedLineAggregator<CatalogBO> lineAggregator = new DelimitedLineAggregator<CatalogBO>();
		lineAggregator.setDelimiter(this.environment.getRequiredProperty("csv.delimiter"));
		lineAggregator.setFieldExtractor(this.fieldExtractor());
		return lineAggregator;		
	}
	
	/**
	 * 
	 * @return fieldExtractor
	 */
	private FieldExtractor<CatalogBO> fieldExtractor() {
		BeanWrapperFieldExtractor<CatalogBO> fieldExtractor = new BeanWrapperFieldExtractor<CatalogBO>();
		fieldExtractor.setNames(new String[] {"productId", "productDescription", "productImage", "catalogItemId", "itemNumber", "price"});
		return fieldExtractor;
	}
	
	/**
	 * StepExecutionListener
	 * 
	 * @return CvsToXmlStepListener
	 */
	private StepExecutionListener dataBaseToCsvStepListener() {
		DataBaseToCsvStepListener dataBaseToCsvStepListener = new DataBaseToCsvStepListener();
		return dataBaseToCsvStepListener;
	}
	

	
}
