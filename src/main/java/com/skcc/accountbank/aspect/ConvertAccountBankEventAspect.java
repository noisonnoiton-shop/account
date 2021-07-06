package com.skcc.accountbank.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.skcc.accountbank.event.message.AccountBankEventType;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ConvertAccountBankEventAspect {
	
	@Pointcut("execution(* com.skcc.*.service.*.convertAccountBankToAccountBankEvent(..))")
	public void convertAccountBankToAccountBankEvent() {}
	
	@Around("convertAccountBankToAccountBankEvent() && args(txId, id, AccountBankEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, long id, AccountBankEventType AccountBankEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			// ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			// zuul prefilter 제거하여 수동 생성
			// txId = attr.getRequest().getHeader("X-TXID");
			UUID uuid = UUID.randomUUID();
			txId = String.format("%s-%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), uuid.toString());
		}
		
		return pjp.proceed(new Object[] {txId, id, AccountBankEventType});
	}
}
