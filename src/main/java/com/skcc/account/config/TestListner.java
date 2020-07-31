package com.skcc.account.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class TestListner implements ServletContextListener {

	@Autowired
	ThreadPoolTaskExecutor executor;

	@Autowired
	ThreadPoolTaskScheduler scheduler;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdown();
		executor.shutdown();
	}
}