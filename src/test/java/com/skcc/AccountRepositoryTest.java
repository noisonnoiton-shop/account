package com.skcc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.skcc.account.domain.Account;
import com.skcc.account.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.yml")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testAccountRepository(){
    	
        List<Account> accountList = accountRepository.findAll();
        System.out.println(accountList);

        Account account = accountList.get(0);
//        assertThat(account.getName(), is("크리스"));
//        assertThat(account.getMobile(), is("010-1224-1225"));
        
        System.out.println(account.getName() + ":" + account.getMobile());
    }

}