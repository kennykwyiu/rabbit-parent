package com.kenny.rabbit.producer.config.database;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 *  BrokerMessageConfiguration
 *  This class is responsible for executing an SQL script to initialize the database schema.
 *  It helps in creating the necessary database table structure.
 *
 */
@Configuration
public class BrokerMessageConfiguration {

    @Autowired
    private DataSource rabbitProducerDataSource; // Inject the data source

    @Value("classpath:rabbit-producer-message-schema.sql")
    private Resource schemaScript; // SQL script for schema initialization

    @Bean
    public DataSourceInitializer initDataSourceInitializer() {
        System.err.println("--------------rabbitProducerDataSource-----------:" + rabbitProducerDataSource);

        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource); // Set the data source
        initializer.setDatabasePopulator(databasePopulator()); // Set the database populator
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript); // Add the SQL script to be executed
        return populator;
    }
}

