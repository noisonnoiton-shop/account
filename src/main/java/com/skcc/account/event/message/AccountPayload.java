package com.skcc.account.event.message;

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
public class AccountPayload {
	
	private long id;
	private String username;
	private String name;
	private String mobile;
	private String scope;
	private String address;
	
}
