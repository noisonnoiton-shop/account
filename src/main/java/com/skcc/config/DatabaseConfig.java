package com.skcc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.skcc.account.repository", "com.skcc.accountbank.repository" })
public class DatabaseConfig {

  @Value("${spring.datasource.driver-class-name}")
  String driverClassName;
  @Value("${spring.datasource.url}")
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String password;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {

    return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password)
        .build();
  }
}