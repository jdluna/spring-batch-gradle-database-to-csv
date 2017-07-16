package com.xgtlabs.product.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * classe de configuration dataDource, dataSourcePopulator
 * 
 * @author javadev
 *
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
@ComponentScan("com.xgtlabs.product")
public class BatchConfiguration {

	@Autowired
	private Environment env;
	
	@Bean(destroyMethod = "")
	public BasicDataSource dataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(this.env.getRequiredProperty("dataSource.driverClassName"));
		dataSource.setUsername(this.env.getRequiredProperty("dataSource.username"));
		dataSource.setPassword(this.env.getRequiredProperty("dataSource.password"));
		dataSource.setUrl(this.env.getRequiredProperty("dataSource.url"));
		
		return dataSource;
		
	}
	
	@Bean(destroyMethod="")
	public DataSourceInitializer dataSourcePopulator() {
		
		DataSourceInitializer initializer = new DataSourceInitializer();
		
		initializer.setDataSource(this.dataSource());
		
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.setContinueOnError(Boolean.TRUE);
		
		databasePopulator.addScript(new ClassPathResource(this.env.getRequiredProperty("schema.postgresql")));
		databasePopulator.addScript(new ClassPathResource(this.env.getRequiredProperty("schema.batch")));
		
		initializer.setDatabasePopulator(databasePopulator);
		
		return initializer;
		
	}
	
}
