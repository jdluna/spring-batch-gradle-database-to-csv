package com.xgtlabs.product;

import static org.junit.Assert.assertEquals;

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
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xgtlabs.product.config.BatchConfiguration;
import com.xgtlabs.product.config.ProductJob;

/**
 * Classe de test du job productJob
 * 
 * @author javadev
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes= {BatchConfiguration.class, ProductJob.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.properties")
public class ProductJobTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductJobTest.class);
	private static final String DATABASE_TO_CSV_STEP = "dataBaseToCsvStep";
	private static final String DATABASE_TO_CSV_JOB = "dataBaseToCsvJob";
	private static final int EXPECTED_READ_COUNT = 2;
	private static final int EXPECTED_WRITE_COUNT = 2;
	
	@Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;
	
    @Autowired
	@Qualifier(DATABASE_TO_CSV_JOB)
	Job job;
    
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
	 * test de la step dataBaseToCsvStep du job dataBaseToCsvJob
	 */
	@Test
	public void test1DataBaseToCsvStep() {
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
}
