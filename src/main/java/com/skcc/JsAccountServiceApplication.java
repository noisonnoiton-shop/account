package com.skcc;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import com.skcc.account.domain.Account;
import com.skcc.account.repository.AccountRepository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JsAccountServiceApplication {

	@Autowired
	AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(JsAccountServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${mybatis.config-location}")
	String mybatisConfig;

	private static final Logger log = LoggerFactory.getLogger("main");

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		log.info("Database ====== " + mybatisConfig);
		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:" + mybatisConfig));

		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");

		sessionFactory.setMapperLocations(res);

		return sessionFactory.getObject();
	}

	// @Bean
	public String createSampleData(AccountRepository accountRepository) {
		
		long id = 1;
		Account account = Account.builder().id(id).username("a").name("a").mobile("a").address("a").password("asdfasdf11")
				.scope("customer").createdAt(LocalDateTime.now()).build();
		accountRepository.save(account);

		return "Created,,,,";
	}
}
