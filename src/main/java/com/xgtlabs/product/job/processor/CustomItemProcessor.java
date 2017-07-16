package com.xgtlabs.product.job.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.xgtlabs.product.bo.CatalogBO;

/**
 * processor definition de règles de gestion
 * 
 * @author javadev
 *
 */
public class CustomItemProcessor implements ItemProcessor<CatalogBO, CatalogBO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemProcessor.class);

	@Override
	public CatalogBO process(CatalogBO catalog) throws Exception {
		LOGGER.info("Processing.... {}", catalog);
		return catalog;
	}

}
