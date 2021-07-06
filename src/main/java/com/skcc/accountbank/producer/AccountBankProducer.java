package com.skcc.accountbank.producer;

import com.skcc.accountbank.event.message.AccountBankEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountBankProducer {

  private final StreamBridge streamBridge;

  @Value("${domain.account.name}")
  private String domain;

  public boolean send(AccountBankEvent accountBankEvent) {
    log.info("routeTo" + domain + "." + accountBankEvent.getEventType());

    return this.streamBridge.send("accountBankTopic", MessageBuilder.withPayload(accountBankEvent)
        .setHeader("routeTo", domain + "." + accountBankEvent.getEventType()).build());
  }
}