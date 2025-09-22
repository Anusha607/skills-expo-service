package com.example.skills_expo_service.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.skills_expo_service",includeFilters = @ComponentScan.Filter(com.example.skills_expo_service.config.ReadOnlyRepository.class),entityManagerFactoryRef = "readOnlyEntityManagerFactory")
public class ReadOnlyEntityManagerConfiguration {
    @Value("${app.readonly.datasource.url}")
    private String urlName;

    @Value("${app.readonly.datasource.username}")
    private String userName;

    @Value("${app.readonly.datasource.password}")
    private String password;

    @Value("${app.readonly.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @ConfigurationProperties("app.readonly.datasource")
    public DataSource readOnlyDataSource(){
        return DataSourceBuilder.create().driverClassName(driverClassName).url(urlName).username(userName).password(password).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory(EntityManagerFactoryBuilder builder , @Qualifier("readOnlyDataSource") DataSource dataSource){
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        return builder.dataSource(dataSource).packages("com.example.skills_expo_service").persistenceUnit("read").properties(jpaProperties).build();
    }

}
