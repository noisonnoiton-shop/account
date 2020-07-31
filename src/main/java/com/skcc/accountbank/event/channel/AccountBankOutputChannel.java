package com.skcc.accountbank.event.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AccountBankOutputChannel {
	String AccountBankOutput = "AccountBankOutput";
	
	@Output(AccountBankOutputChannel.AccountBankOutput)
	MessageChannel getMessageChannel();
}
