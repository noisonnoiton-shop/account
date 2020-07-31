package com.skcc.account.event.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AccountOutputChannel {
	String AccountOutput = "AccountOutput";
	
	@Output(AccountOutputChannel.AccountOutput)
	MessageChannel getMessageChannel();
}
