package com.example.demo.context;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class BonusServiceContext {
        @Bean
        public DataSource dragSource(){
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL("jdbc:h2:~/test2;DB_CLOSE_ON_EXIT=FALSE");
            jdbcDataSource.setUser("sa");
            return jdbcDataSource;
        }



}
