package com.skcc.accountbank.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Data
public class AccountBankPayload {
	
	private long id;
	private long accountId;
	private String bankName;
	private String bankNumber;
	private String active;
	
}
