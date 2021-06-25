package com.skcc.account.service;

import java.time.LocalDateTime;
import java.util.List;

import com.skcc.account.controller.AccountController;
import com.skcc.account.domain.Account;
import com.skcc.account.event.message.AccountEvent;
import com.skcc.account.event.message.AccountEventType;
import com.skcc.account.event.message.AccountPayload;
import com.skcc.account.publish.AccountPublish;
import com.skcc.account.repository.AccountEventRepository;
import com.skcc.account.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
	
	// @Autowired
	// private AccountMapper accountMapper;
	
	private AccountPublish accountPublish;
	private AccountRepository accountRepository;
	private AccountEventRepository accountEventRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Value("${domain.account.name}")
	private String domainName;

	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	public AccountService(AccountRepository accountRepository, AccountEventRepository accountEventRepository, AccountPublish accountPublish) {
		this.accountRepository = accountRepository;
		this.accountEventRepository = accountEventRepository;
		this.accountPublish = accountPublish;
	}
	
	public Account login(Account account) {
		Account resultAccount = this.findByUsername(account.getUsername());
		try {
			this.checkAccount(account, resultAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return resultAccount;
	}
	
	public void checkAccount(Account account, Account resultAccount) throws Exception {
		if(account.getUsername().equals(resultAccount.getUsername())
			&& account.getPassword().equals(resultAccount.getPassword())) {
			resultAccount = account;
		} else {
			throw new Exception();
		}
	}
	
	public Account findById(long id) {
//		return this.accountMapper.findById(id);
		return this.accountRepository.findById(id);
	}
	
	public Account findByUsername(String username) {
		return this.accountRepository.findByUsername(username);
	}
	
	public boolean createAccountAndCreatePublishEvent(Account account) {
		boolean result = false;
		try {
			this.accountService.createAccountAndCreatePublishAccountCreatedEvent(account);
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
			try {
				this.accountService.CreatePublishAccountCreateFailedEvent(account);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean editAccountAndCreatePublishEvent(Account account, long id) {
		boolean result = false;
		try {
			this.accountService.editAccountAndCreatePublishAccountEditedEvent(account, id);
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
			try {
				this.accountService.CreatePublishAccountEditFailedEvent(account);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}	
		return result;
	}
	
	@Transactional
	public void createAccountAndCreatePublishAccountCreatedEvent(Account account) throws Exception{
		this.createAccountValidationCheck(account);
		Account resultAccount = this.createAccount(account);
		this.createAndPublishEvent(null, resultAccount.getId(), AccountEventType.AccountCreated);
	}
	
	@Transactional
	public void CreatePublishAccountCreateFailedEvent(Account account) throws Exception {
		this.createAndPublishEvent(null, account.getId(), AccountEventType.AccountCreateFailed);
	}
	
	@Transactional
	public void editAccountAndCreatePublishAccountEditedEvent(Account account, long id) throws Exception{
		this.editAccountValidationCheck(account, id);
		this.editAccount(account, id);
		this.createAndPublishEvent(null, account.getId(), AccountEventType.AccountEdited);
	}
	
	@Transactional
	public void CreatePublishAccountEditFailedEvent(Account account) throws Exception {
		this.createAndPublishEvent(null, account.getId(), AccountEventType.AccountEditFailed);
	}
	
	public void createAndPublishEvent(String txId, long id, AccountEventType accountEventType) {
		AccountEvent accountEvent = this.accountService.convertAccountToAccountEvent(txId, id, accountEventType);
		this.createAccountEvent(accountEvent);
		this.publishAccountEvent(accountEvent);
	}
		
	public Account createAccount(Account account) {
		account = this.accountRepository.save(account); 
		return account;
	}

	public Account editAccount(Account account, long id) throws Exception {
		// return this.accountMapper.editAccount(account);
		return this.accountRepository.save(account);
	}
	
	public void editAccountValidationCheck(Account account, long id) throws Exception {
		if(account.getId() != id)
			throw new Exception();
	}
	
	public void createAccountValidationCheck(Account account) throws Exception {
		if(this.findByUsername(account.getUsername()) != null)
			throw new Exception();
	}
	
	public AccountEvent createAccountEvent(AccountEvent accountEvent) {
		// return this.accountMapper.createAccountEvent(accountEvent);
		return this.accountEventRepository.save(accountEvent);
	}
	
	public boolean publishAccountEvent(AccountEvent accountEvent) {
		return this.accountPublish.send(accountEvent);
	}
	
	public List<AccountEvent> getAccountEvent() {
		// return this.accountMapper.getAccountEvent();
		return this.accountEventRepository.findAll();
	}
	
	public AccountEvent convertAccountToAccountEvent(String txId, long id, AccountEventType accountEventType) {
		log.info("txid : {}", txId);
		
		Account account = this.findById(id);
		
		AccountEvent accountEvent = new AccountEvent();
		// accountEvent.setId(this.accountMapper.getAccountEventId());
		accountEvent.setAccountId(account.getId());
		accountEvent.setDomain(this.domainName);
		accountEvent.setEventType(accountEventType);
		accountEvent.setCreatedAt(LocalDateTime.now());
		accountEvent.setPayload(new AccountPayload(account.getId(), account.getUsername(), account.getName(), account.getMobile(), account.getScope(), account.getAddress()));
		accountEvent.setTxId(txId);
		
		log.info(accountEvent.toString());

		return accountEvent;
	}
	
}
