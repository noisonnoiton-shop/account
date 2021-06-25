package com.skcc.accountbank.event.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.skcc.accountbank.config.AccountBankPayloadConverter;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Data
@Entity
public class AccountBankEvent {

	@Id
	@SequenceGenerator(name = "event_seq_gen", sequenceName = "event_seq", allocationSize = 1)

	@GeneratedValue(generator = "event_seq_gen")
	private Long id;

	@Column(length = 255)
	private String domain;

	@Column
	private Long accountBankId;

	@Column
	@Enumerated(EnumType.STRING)
	private AccountBankEventType eventType;

	@Column(columnDefinition = "TEXT")
	@Convert(converter = AccountBankPayloadConverter.class)
	private AccountBankPayload payload;

	@Column(length = 255)
	private String txId;

	@Column
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Builder
	public AccountBankEvent(Long id, String domain, Long accountBankId, AccountBankEventType eventType,
			AccountBankPayload payload, String txId, LocalDateTime createdAt) {
		this.id = id;
		this.domain = domain;
		this.accountBankId = accountBankId;
		this.eventType = eventType;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}

}
