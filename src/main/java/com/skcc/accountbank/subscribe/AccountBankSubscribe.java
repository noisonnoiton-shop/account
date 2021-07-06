package com.skcc.accountbank.subscribe;

import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.service.AccountBankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component
// @EnableBinding(AccountBankInputChannel.class)
public class AccountBankSubscribe {

	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankSubscribe(AccountBankService accountBankService) {
		this.accountBankService = accountBankService;
	}
	
	// @StreamListener(AccountBankInputChannel.accountCreated)
	public void createAccountBank(AccountEvent accountEvent) {
		this.accountBankService.createAccountBankAndCreatePublishEvent(accountEvent);
	}
	
}
