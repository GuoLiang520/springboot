package com.luo.config;

import com.luo.job.Job1;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.persistence.EntityManagerFactory;

@Configuration
public class DataSourcesConfig {

    @Bean
    public Job1 getSchedulerFactoryBean(){
        return new Job1();
    }
}
