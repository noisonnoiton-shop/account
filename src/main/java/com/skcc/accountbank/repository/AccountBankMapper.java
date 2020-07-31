package com.skcc.accountbank.repository;

import org.apache.ibatis.annotations.Mapper;

import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.event.message.AccountBankEvent;

@Mapper
public interface AccountBankMapper {
	
//	@Select("select * from account_banks where accountId = #{accountId}")
	public AccountBank findAccountBankByAccountId(long accountId);

	//	@Select("select * from account_banks where id = #{id}")
	public AccountBank findById(long id);
	 
//	@Select("select get_account_eventid_seq('accountBankEventId')")
	public long getAccountBankEventId();
	
//	@insert("insert into account_bank_events(id, domain, accountBankId, eventType, payload, txId, createdAt) values(#{id}, #{domain}, #{accountBankId}, #{eventType}, #{payload}, #{txId}, NOW())")
	public void createAccountBankEvent(AccountBankEvent accountBankEvent);
	
//	@insert("insert into account_bank_events(accountId, bankName, bankNumber, active, createdAt) values(#{accountId}, #{bankName}, #{bankNumber}, #{active}, NOW())")
	public void createAccountBank(AccountBank accountBank);
	
}
