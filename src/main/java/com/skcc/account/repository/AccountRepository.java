package com.skcc.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skcc.account.domain.Account;

import io.opentelemetry.instrumentation.annotations.WithSpan;

public interface AccountRepository extends JpaRepository<Account, Long>{
	// @WithSpan
	public Account findById(long id);
	public Account findByUsername(String username);
}

