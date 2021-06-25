package com.skcc.account.config;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.account.event.message.AccountPayload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountPayloadConverter implements AttributeConverter<AccountPayload, String> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(AccountPayload tData) {

    try {
      return objectMapper.writeValueAsString(tData);
    } catch (JsonProcessingException e) {
      log.error("fail to serialize as object into Json : {}", tData, e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public AccountPayload convertToEntityAttribute(String jsonStr) {

    try {
      return objectMapper.readValue(jsonStr, AccountPayload.class);
    } catch (IOException e) {
      log.error("fail to deserialize as Json into Object : {}", jsonStr, e);
      throw new RuntimeException(e);
    }
  }
}
