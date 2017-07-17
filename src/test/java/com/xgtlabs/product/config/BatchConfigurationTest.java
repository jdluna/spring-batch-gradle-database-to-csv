package com.xgtlabs.product.config;

import javax.sql.DataSource;

import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@Configuration
public class BatchConfigurationTest {

	@Autowired
	DataSource dataSource;
	
	@Bean
	public PostgresqlDataTypeFactory sqlDataTypeFactory() {
		return new PostgresqlDataTypeFactory();
	}
	
	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig() {
		DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
		
		databaseConfigBean.setDatatypeFactory(sqlDataTypeFactory());
		databaseConfigBean.setQualifiedTableNames(Boolean.TRUE);
		
		return databaseConfigBean;
	}
	
	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
		
		DatabaseDataSourceConnectionFactoryBean factory = new DatabaseDataSourceConnectionFactoryBean();
		
		factory.setDataSource(dataSource);
		factory.setDatabaseConfig(dbUnitDatabaseConfig());
		
		return factory;
	}
	
}
