package com.skcc.accountbank.controller;

// import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.skcc.account.event.message.AccountEvent;
import com.skcc.accountbank.domain.AccountBank;
import com.skcc.accountbank.service.AccountBankService;
import com.skcc.config.OtelConfig;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapSetter;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @XRayEnabled
@RestController
@RequestMapping("/v1")
public class AccountBankController {

	private static final Logger log = LoggerFactory.getLogger(AccountBankController.class);

	private AccountBankService accountBankService;
	// private OtelConfig otelConfig;
	private Tracer tracer;

	@Autowired
	public AccountBankController(AccountBankService accountBankService,
			OtelConfig otelConfig,
			Tracer tracer
		) {
		this.accountBankService = accountBankService;
		// this.otelConfig = otelConfig;
		this.tracer = tracer;
	}

	TextMapGetter<HttpHeaders> getter = new TextMapGetter<HttpHeaders>() {
		@Override
		public String get(HttpHeaders headers, String s) {
			assert headers != null;
			// return headers.getHeaderString(s);
			// return headers.firstValue(s).toString();
			return headers.getFirst(s);
		}

		@Override
		public Iterable<String> keys(HttpHeaders headers) {
			List<String> keys = new ArrayList<>();
			// MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
			// Map<String, List<String>> requestHeaders = headers.map()
			headers.forEach((k, v) -> {
				keys.add(k);
			});
			return keys;
		}
	};

	@GetMapping(value = "/accountbanks/{accountId}")
	public AccountBank findAccountBankByAccountId(@PathVariable long accountId, @RequestHeader HttpHeaders headers)
			throws Exception {
		log.info(String.valueOf(accountId));

		// Context extractedContext = otelConfig.openTelemetry().getPropagators().getTextMapPropagator()
		// 		.extract(Context.current(), headers, getter);

		// Tracer tracer = this.otelConfig.openTelemetry().getTracer("accountbank-instrumentation", "1.0.0");
		Span span = tracer.spanBuilder("account.AccountBankController.findAccountBankByAccountId").setSpanKind(SpanKind.SERVER).startSpan();

		// try (Scope scope = extractedContext.makeCurrent()) {
		try {
			span.setAttribute(SemanticAttributes.HTTP_METHOD, "GET");
			span.setAttribute("accountId", accountId);

			return accountBankService.findAccountBankByAccountId(accountId, span);
		} finally {
			span.end();
		}

		// return accountBankService.findAccountBankByAccountId(accountId, span);
	}

	@PostMapping(value = "/accountbanks")
	public boolean createAccountBank(@RequestBody AccountEvent accountEvent) {
		return this.accountBankService.createAccountBankAndCreatePublishEvent(accountEvent);
	}

}
