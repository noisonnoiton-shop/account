package com.skcc.account.repository;

import com.skcc.account.event.message.AccountEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEventRepository extends JpaRepository<AccountEvent, Long>{
	public AccountEvent findById(long id);
}

