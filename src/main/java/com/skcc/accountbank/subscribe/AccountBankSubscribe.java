package com.skcc.accountbank.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.event.channel.AccountBankInputChannel;
import com.skcc.accountbank.service.AccountBankService;

@Component
@EnableBinding(AccountBankInputChannel.class)
public class AccountBankSubscribe {

	private AccountBankService accountBankService;
	
	@Autowired
	public AccountBankSubscribe(AccountBankService accountBankService) {
		this.accountBankService = accountBankService;
	}
	
	@StreamListener(AccountBankInputChannel.accountCreated)
	public void createAccountBank(AccountEvent accountEvent) {
		this.accountBankService.createAccountBankAndCreatePublishEvent(accountEvent);
	}
	
}
