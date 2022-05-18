package com.felipepossari.schoolregistration.integration;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class DatabaseContainerSetup {

    static final MySQLContainer DATABASE = new MySQLContainer(MySQLContainer.NAME);

    static {
        DATABASE.start();
    }

    @DynamicPropertySource
    static void setDatabaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
        registry.add("spring.datasource.username", DATABASE::getUsername);
        registry.add("spring.datasource.password", DATABASE::getPassword);
    }
}
