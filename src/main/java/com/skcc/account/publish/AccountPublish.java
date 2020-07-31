package com.skcc.account.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.skcc.account.event.channel.AccountOutputChannel;
import com.skcc.account.event.message.AccountEvent;

@Component
@EnableBinding(AccountOutputChannel.class)
public class AccountPublish {
	
	private AccountOutputChannel accountOutputChannel;
	
	@Value("${domain.account.name}")
	private String domain;
	
	private static final Logger log = LoggerFactory.getLogger(AccountPublish.class);

	@Autowired
	public AccountPublish(AccountOutputChannel accountOutputChannel) {
		this.accountOutputChannel = accountOutputChannel;
	}
	
	public boolean send(AccountEvent accountEvent) {
		log.info("in publish accountEvent : {}", accountEvent.toString());
		return this.accountOutputChannel.getMessageChannel().send(MessageBuilder.withPayload(accountEvent).setHeader("routeTo", domain + "." + accountEvent.getEventType()).build());
	}
	
}
