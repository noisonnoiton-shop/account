package com.skcc.accountbank.event.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountBankPayload {
	
	private long id;
	private long accountId;
	private String bankName;
	private String bankNumber;
	private String active;
	
}
