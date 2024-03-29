package com.skcc.accountbank.service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

// import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.event.message.AccountBankEvent;
import com.skcc.accountbank.event.message.AccountBankEventType;
import com.skcc.accountbank.event.message.AccountBankPayload;
import com.skcc.accountbank.producer.AccountBankProducer;
import com.skcc.accountbank.repository.AccountBankEventRepository;
import com.skcc.accountbank.repository.AccountBankRepository;
import com.skcc.config.OtelConfig;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

// @XRayEnabled
@Service
public class AccountBankService {

	private static final Logger log = LoggerFactory.getLogger(AccountBankService.class);

	// @Autowired
	// private AccountBankMapper accountBankMapper;

	private AccountBankRepository accountBankRepository;
	private AccountBankEventRepository accountBankEventRepository;
	// private OtelConfig otelConfig;
	private Tracer tracer;

	@Autowired
	private AccountBankProducer accountBankProducer;

	@Value("${domain.accountBank.name}")
	private String domain;

	@Autowired
	private AccountBankService accountBankService;

	@Autowired
	public AccountBankService(AccountBankRepository accountBankRepository,
			AccountBankEventRepository accountBankEventRepository,
			OtelConfig otelConfig,
			Tracer tracer
			) {
		this.accountBankRepository = accountBankRepository;
		this.accountBankEventRepository = accountBankEventRepository;
		// this.otelConfig = otelConfig;
		this.tracer = tracer;
	}

	@WithSpan
	public AccountBank findAccountBankByAccountId(long accountId, Span parentSpan, @SpanAttribute("accountId") String accountIdStr) {
	// public AccountBank findAccountBankByAccountId(long accountId) {

		// Tracer tracer = this.otelConfig.openTelemetry().getTracer("accountbank-instrumentation", "1.0.0");
		Span span = tracer.spanBuilder("account.AccountBankService.findAccountBankByAccountId").setParent(Context.current().with(parentSpan)).startSpan();
		try {
			span.setAttribute("accountId", accountId);
			return accountBankRepository.findAccountBankByAccountId(accountId);
		} finally {
			span.end();
		}

		// return accountBankMapper.findAccountBankByAccountId(accountId);
		// return accountBankRepository.findAccountBankByAccountId(accountId);
	}

	public boolean createAccountBankAndCreatePublishEvent(AccountEvent accountEvent) {
		boolean result = false;
		AccountBank accountBank = this.convertAccountEventToAccountBank(accountEvent);
		try {
			this.createAccountBankAndCreatePublishAccountBankCreatedEvent(accountEvent.getTxId(), accountBank);
			result = true;
		} catch (Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.createPublishAccountBankCreateFailedEvent(accountEvent.getTxId(), accountBank);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	@Transactional
	public void createAccountBankAndCreatePublishAccountBankCreatedEvent(String txId, AccountBank accountBank)
			throws Exception {
		AccountBank resultAccountBank = this.createAccountBank(accountBank);
		this.createPublishAccountBankEvent(txId, resultAccountBank, AccountBankEventType.AccountBankCreated);
	}

	@Transactional
	public void createPublishAccountBankCreateFailedEvent(String txId, AccountBank accountBank) {
		this.createPublishAccountBankEvent(txId, accountBank, AccountBankEventType.AccountBankCreateFailed);
	}

	public AccountBank createAccountBank(AccountBank accountBank) throws Exception {
		this.createAccountBankValidationCheck(accountBank);
		// this.accountBankMapper.createAccountBank(accountBank);
		this.accountBankRepository.save(accountBank);

		return accountBank;
	}

	public void createPublishAccountBankEvent(String txId, AccountBank accountBank,
			AccountBankEventType accountBankEventType) {
		AccountBankEvent accountBankEvent = this.accountBankService.convertAccountBankToAccountBankEvent(txId,
				accountBank.getId(), accountBankEventType);
		this.createAccountBankEvent(accountBankEvent);
		this.publishAccountBankEvent(accountBankEvent);
	}

	public void createAccountBankValidationCheck(AccountBank accountBank) throws Exception {
		// if(this.accountBankMapper.findAccountBankByAccountId(accountBank.getAccountId())
		// != null)
		// throw new Exception();
	}

	public AccountBank convertAccountEventToAccountBank(AccountEvent accountEvent) {
		log.info(accountEvent.toString());

		AccountBank accountBank = new AccountBank();
		accountBank.setAccountId(accountEvent.getAccountId());
		accountBank.setActive("active");
		accountBank.setBankName("kb");
		accountBank.setBankNumber(this.generateBankNumber());

		return accountBank;
	}

	public String generateBankNumber() {
		long result = ThreadLocalRandom.current().nextLong();
		if (result < 0)
			result = result * (-1);

		return (String) String.valueOf(result).subSequence(0, 14);
	}

	public void createAccountBankEvent(AccountBankEvent accountBankEvent) {
		// this.accountBankMapper.createAccountBankEvent(accountBankEvent);
		this.accountBankEventRepository.save(accountBankEvent);
	}

	public void publishAccountBankEvent(AccountBankEvent accountBankEvent) {
		// this.accountBankPublish.send(accountBankEvent);
		this.accountBankProducer.send(accountBankEvent);
	}

	public AccountBankEvent convertAccountBankToAccountBankEvent(String txId, long id,
			AccountBankEventType accountBankEventType) {
		log.info("in service txId : " + txId);

		AccountBank accountBank = this.findById(id);

		AccountBankEvent accountBankEvent = new AccountBankEvent();
		// accountBankEvent.setId(this.accountBankMapper.getAccountBankEventId());
		accountBankEvent.setAccountBankId(accountBank.getId());
		accountBankEvent.setDomain(domain);
		accountBankEvent.setEventType(accountBankEventType);
		accountBankEvent.setCreatedAt(LocalDateTime.now());
		accountBankEvent.setPayload(new AccountBankPayload(accountBank.getId(), accountBank.getAccountId(),
				accountBank.getBankName(), accountBank.getBankNumber(), accountBank.getActive()));
		accountBankEvent.setTxId(txId);

		log.info("in service accountBankEvent : " + accountBankEvent.toString());

		return accountBankEvent;
	}

	public AccountBank findById(long id) {
		return this.accountBankRepository.findById(id);
	}

}
