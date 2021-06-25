package com.skcc.config;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericJsonConverter<T> implements AttributeConverter<T, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(T tData) {

		try {
			return objectMapper.writeValueAsString(tData);
		} catch (JsonProcessingException e) {
			log.error("fail to serialize as object into Json : {}", tData, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public T convertToEntityAttribute(String jsonStr) {

		try {
			return objectMapper.readValue(jsonStr, new TypeReference<T>() {});
		} catch (IOException e) {
			log.error("fail to deserialize as Json into Object : {}", jsonStr, e);
			throw new RuntimeException(e);
		}
	}
}
