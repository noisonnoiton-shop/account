package com.skcc.account.event.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.skcc.account.config.AccountPayloadConverter;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class AccountEvent {

	@Id
	@SequenceGenerator( name = "event_seq_gen", sequenceName = "event_seq", allocationSize = 1 )

	@GeneratedValue(generator="event_seq_gen")
	private Long id;

	@Column(length = 255)
	private String domain;

	@Column
	private Long accountId;

	@Column
	@Enumerated(EnumType.STRING)
	private AccountEventType eventType;

	@Column(columnDefinition = "TEXT")
	@Convert(converter = AccountPayloadConverter.class)
	private AccountPayload payload;

	@Column(length = 255)
	private String txId;

	@Column
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Builder
	public AccountEvent(Long id, String domain, Long accountId, AccountPayload payload, String txId, LocalDateTime createdAt) {
		this.id = id;
		this.domain = domain;
		this.accountId = accountId;
		this.payload = payload;
		this.txId = txId;
		this.createdAt = createdAt;
	}

}
