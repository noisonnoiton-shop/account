package com.skcc;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

import com.skcc.account.domain.Account;
import com.skcc.account.repository.AccountRepository;

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
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}
	
	@Bean
	public String createSampleData(AccountRepository accountRepository) {
		long id = 1;
		Account account = Account.builder().id(id).username("a").name("a").mobile("a").address("a").password("1")
				.scope("customer").build();
    	accountRepository.save(account);
    	
    	return "Created,,";
	}
}
