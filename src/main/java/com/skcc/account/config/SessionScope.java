package com.skcc.account.config;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Data
public class SessionScope implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String username;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String mobile;
	
	@Getter @Setter
	private String address;
	
	@Getter @Setter
	private String scope;
	
	public String toString()
	{
		return id + " : " + username + ":" + name + ":" + mobile + ":" + address + ":" + scope;
	}
}