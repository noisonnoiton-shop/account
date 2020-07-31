package com.skcc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.skcc.account.domain.Account;
import com.skcc.account.domain.Account.AccountBuilder;
import com.skcc.account.repository.AccountMapper;
import com.skcc.account.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.yml")
public class JsAccountServiceApplicationTests {

	@Test
	public void contextLoads() {
	}
	
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testAccountRepository(){
    	long id = 1;
//        Account account = accountMapper.findById(id);
//        System.out.println(account);

		Account account = Account.builder().id(id).username("a").name("a").mobile("a").address("a").password("1")
				.scope("customer").build();
    	accountRepository.save(account);
        
        System.out.println("==============================");
        Account account1 = accountRepository.findById(id);
        System.out.println(account1);

//        assertThat(account.getName(), is("크리스"));
//        assertThat(account.getMobile(), is("010-1224-1225"));
        
        System.out.println(account1.getName() + ":" + account1.getMobile());
    }

}
