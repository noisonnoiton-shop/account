package com.skcc.accountbank.publish;

import com.skcc.accountbank.event.channel.AccountBankOutputChannel;
import com.skcc.accountbank.event.message.AccountBankEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;

// @Component
// @EnableBinding(AccountBankOutputChannel.class)
public class AccountBankPublish {
	
	private AccountBankOutputChannel accountBankOutputChannel;
	
	@Value("${domain.accountBank.name}")
	private String domain;
	
	@Autowired
	public AccountBankPublish(AccountBankOutputChannel accountBankOutputChannel) {
		this.accountBankOutputChannel = accountBankOutputChannel;
	}
	
	public void send(AccountBankEvent accountBankEvent) {
		this.accountBankOutputChannel.getMessageChannel().send(MessageBuilder.withPayload(accountBankEvent).setHeader("routeTo", domain + "." + accountBankEvent.getEventType()).build());
	}
}
