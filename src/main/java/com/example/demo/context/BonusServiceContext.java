package com.example.demo.context;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class BonusServiceContext {
/*        @Bean
        public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                     @Value("${spring.datasource.username}")  String user,
                                     @Value("${spring.datasource.password}") String password){
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL(url);
            jdbcDataSource.setUser(user);
            jdbcDataSource.setPassword(password);
            return jdbcDataSource;
        }*/



}
