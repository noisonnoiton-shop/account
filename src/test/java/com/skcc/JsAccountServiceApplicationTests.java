package com.skcc;

import com.skcc.account.domain.Account;
import com.skcc.account.repository.AccountRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "classpath:application.yml")
public class JsAccountServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger("main");

	@Test
	public void contextLoads() {
	}
	
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testAccountRepository(){
    	long id = 1;

		Account account = Account.builder().id(id).username("a").name("a").mobile("a").address("a").password("1")
				.scope("customer").build();
    	accountRepository.save(account);
        
        Account account1 = accountRepository.findById(id);
        log.info(account1.toString());
    }

}
