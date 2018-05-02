package spp.lab.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        DataSource dataSource =
                DataSourceBuilder
                        .create()
                        .username("root")
                        .password("")
                        .url("jdbc:mysql://localhost:3306/java_tests")
                        .driverClassName("com.mysql.jdbc.Driver")
                        .build();
        return dataSource;
    }

}
