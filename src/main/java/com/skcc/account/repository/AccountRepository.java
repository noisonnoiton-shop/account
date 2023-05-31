package com.skcc.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skcc.account.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	public Account findById(long id);
	public Account findByUsername(String username);
}

