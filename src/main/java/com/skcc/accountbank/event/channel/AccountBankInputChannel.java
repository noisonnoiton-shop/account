package com.skcc.accountbank.event.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AccountBankInputChannel {
	String accountCreated = "accountCreated";
	
	@Input(AccountBankInputChannel.accountCreated)
	SubscribableChannel productSoldOutInput();
}
