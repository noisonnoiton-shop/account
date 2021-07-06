package com.skcc.account.publish;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.skcc.account.event.channel.AccountOutputChannel;
import com.skcc.account.event.message.AccountEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

// @Component
// @XRayEnabled
// @EnableBinding(AccountOutputChannel.class)
public class AccountPublish {
	
	private AccountOutputChannel accountOutputChannel;
	
	@Value("${domain.account.name}")
	private String domain;

	@Autowired
	public AccountPublish(AccountOutputChannel accountOutputChannel) {
		this.accountOutputChannel = accountOutputChannel;
	}
	
	public boolean send(AccountEvent accountEvent) {
		return this.accountOutputChannel.getMessageChannel().send(MessageBuilder.withPayload(accountEvent).setHeader("routeTo", domain + "." + accountEvent.getEventType()).build());
	}
	
}
