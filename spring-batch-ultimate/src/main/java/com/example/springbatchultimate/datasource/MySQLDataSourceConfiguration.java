package com.example.springbatchultimate.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MySQLDataSourceConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig sqlDataSourceProperties(){
        return  new HikariConfig();
    }
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource mySqlDataSource(){
        return  new HikariDataSource(sqlDataSourceProperties());
    }

    @Bean
    public JdbcTemplate sqlJdbcTemplate(){
        return new JdbcTemplate(mySqlDataSource());
    }



}
