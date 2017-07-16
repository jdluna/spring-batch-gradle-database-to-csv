package com.xgtlabs.product.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * 
 * @author javadev
 *
 */
public class DataBaseToCsvStepListener implements StepExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseToCsvStepListener.class);
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOGGER.info("AFTERSTEP1 inside");
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("stepExcecution: {}", stepExecution);
		}
		
		return stepExecution.getExitStatus();
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOGGER.info("BEFORESTEP1 inside");	
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("stepExcecution: {}", stepExecution);
		}
	}

}
