package com.skcc.accountbank.repository;

import com.skcc.accountbank.domain.AccountBank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBankRepository extends JpaRepository<AccountBank, Long>{
	public AccountBank findById(long id);
	public AccountBank findAccountBankByAccountId(long id);
}

