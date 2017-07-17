package com.xgtlabs.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.xgtlabs.product.bo.CatalogBO;
import com.xgtlabs.product.config.BatchConfiguration;
import com.xgtlabs.product.config.BatchConfigurationTest;
import com.xgtlabs.product.config.ProductJob;

/**
 * Classe de test du job productJob
 * 
 * @author javadev
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes= {BatchConfiguration.class, BatchConfigurationTest.class, ProductJob.class})
@TestExecutionListeners( {DependencyInjectionTestExecutionListener.class, 
	StepScopeTestExecutionListener.class,
	DbUnitTestExecutionListener.class} )
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.properties")
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT ,value = "sampleData.xml")
public class ProductJobTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductJobTest.class);
	private static final String DATABASE_TO_CSV_STEP = "dataBaseToCsvStep";
	private static final String DATABASE_TO_CSV_JOB = "dataBaseToCsvJob";
	private static final int EXPECTED_READ_COUNT = 2;
	private static final int EXPECTED_WRITE_COUNT = 2;
	private static final String ITEM_NUMBER = "QWZ5671";
	
	@Autowired
	Environment environment;
	
	@Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;
	
    @Autowired
	@Qualifier(DATABASE_TO_CSV_JOB)
	Job job;
    
    @Autowired
    private ItemReader<CatalogBO> reader;
    
	private JobLauncherTestUtils jobLauncherTestUtils;
		
	
	/**
	 * initialisation avant exécution des tests
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception { 
		  
		this.jobLauncherTestUtils = new JobLauncherTestUtils();
	    this.jobLauncherTestUtils.setJobLauncher(this.jobLauncher);
	    this.jobLauncherTestUtils.setJobRepository(this.jobRepository);
	    this.jobLauncherTestUtils.setJob(this.job);
	    
    } 
	
	/**
	 * test du reader de la step dataBaseToCsvStep
	 * @throws Exception 
	 * @throws NonTransientResourceException 
	 * @throws ParseException 
	 * @throws UnexpectedInputException 
	 */
	@Test
	public void test1Reader() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		
		((ItemStream) this.reader).open(new ExecutionContext());
		
		CatalogBO catalog = this.reader.read();
		
		assertNotNull(catalog);
		assertEquals(ITEM_NUMBER, catalog.getItemNumber());
		
		((ItemStream) this.reader).close();
	}
	
	
	/**
	 * test de la step dataBaseToCsvStep
	 */
	@Test
	public void test3DataBaseToCsvStep() {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchStep(DATABASE_TO_CSV_STEP);
		assertEquals("dataBaseToCsvStep", BatchStatus.COMPLETED, jobExecution.getStatus());
		
		for (StepExecution stepexEcution : jobExecution.getStepExecutions()) {
			
			if (stepexEcution.getStepName().equals(DATABASE_TO_CSV_STEP)) {
				LOGGER.info(stepexEcution.getSummary());
				assertEquals("Read count", EXPECTED_READ_COUNT, stepexEcution.getReadCount());
				assertEquals("Write check", EXPECTED_WRITE_COUNT, stepexEcution.getWriteCount());
				break;
			}
			
		}
	}
	
	/**
	 * test du job dataBaseToCsvJob
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4launchJob() throws Exception {
					
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
				
		LOGGER.info("job {} instance Id: {}", DATABASE_TO_CSV_JOB, jobExecution.getJobInstance().getId());

		assertEquals("Job status", BatchStatus.COMPLETED, jobExecution.getStatus());
			
	}
	
	@After
	public void tearDown(){
	    
		LOGGER.info("suppression du fichier après l'exécution du test");
		File destroyFile = new File(this.environment.getProperty("csv.out.file"));
	    destroyFile.delete();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public StepExecution getStepExection() {
		StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        return execution;
    }
}
