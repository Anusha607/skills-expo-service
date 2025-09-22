package com.example.skills_expo_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.skills_expo_service",excludeFilters = @ComponentScan.Filter(com.example.skills_expo_service.config.ReadOnlyRepository.class),entityManagerFactoryRef = "readWriteEntityManagerFactory")
public class ReadWriteEntityManagerConfiguration {
    @Value("${app.readwrite.datasource.url}")
    private String url;

    @Value("${app.readwrite.datasource.username}")
    private String userName;


    @Value("${app.readwrite.datasource.password}")
    private String password;

    @Value("${app.readwrite.datasource.driver-class-name}")
    private String driverClassName;


    @Bean
    @ConfigurationProperties("app.readwrite.datasource")
    @Primary
    public DataSource readWriteDatasource(){
        return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(userName).password(password).build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean readWriteEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("readWriteDatasource") DataSource datasource){
        return builder.dataSource(datasource).packages("com.example.skills_expo_service").persistenceUnit("main").build();
    }
}
