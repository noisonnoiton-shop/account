package com.skcc.account.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skcc.account.domain.Account;
import com.skcc.account.event.message.AccountEvent;

@Mapper
public interface AccountMapper {
	
//	@Select("select * from accounts where username=#{username}")
	public Account findByUsername(String username);
	
//	@Select("select * from accounts where id=#{id}")
	public Account findById(long id);
	
//	@Insert("insert into accounts(username, name, password, mobile, address, scope, createdAt) values(#{username}, #{name}, #{password}, #{mobile}, #{address}, #{scope}, NOW())")
	public void createAccount(Account account);
	
//	@Update("update accounts set password = #{password}, mobile = #{mobile}, address = #{address} where id = #{id}")
	public boolean editAccount(Account account);
	
//	@Select("select get_account_eventid_seq('eventId')")
	public long getAccountEventId();
	
//	@Insert("insert into account_events(id, accountId, type, payload, txId, createdAt) values(#{id}, #{accountId}, #{type}, #{payload}, #{txId}, #{createdAt})")
	public boolean createAccountEvent(AccountEvent accountEvent);
	
//	@Select("select * from account_events")
	public List<AccountEvent> getAccountEvent();
	
}
