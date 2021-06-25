package com.skcc.accountbank.repository;

import com.skcc.accountbank.event.message.AccountBankEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBankEventRepository extends JpaRepository<AccountBankEvent, Long>{
	public AccountBankEvent findById(long id);
}

