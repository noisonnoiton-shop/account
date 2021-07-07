package com.skcc.account.producer;

import com.skcc.account.event.message.AccountEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountProducer {

  private final StreamBridge streamBridge;

  @Value("${domain.account.name}")
  private String domain;

  public boolean send(AccountEvent accountEvent) {
    log.info("routeTo" + domain + "." + accountEvent.getEventType());

    return this.streamBridge.send("accountOutput", MessageBuilder.withPayload(accountEvent)
    .setHeader("routeTo", domain + "." + accountEvent.getEventType()).build());
  }
}