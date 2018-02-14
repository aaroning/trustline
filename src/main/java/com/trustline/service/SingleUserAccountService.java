package com.trustline.service;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trustline.client.TrustlineClient;
import com.trustline.runtime.Trustline;

public class SingleUserAccountService implements AccountService {
	
	AtomicInteger balance = new AtomicInteger();
	
	private static final Logger LOG = LogManager.getLogger();
	
	private final TrustlineClient client;
	
	@Inject
	public SingleUserAccountService(TrustlineClient client) {
		this.client = client;
		LOG.info("Welcome to Trustline");
		outputBalance();
	}
	
	@Override
	public void receivePayment(int payment) {
		balance.addAndGet(payment);
		LOG.info("You were paid {}!", payment);
		outputBalance();
	}

	@Override
	public void sendPayment(String recipient, int payment) throws Exception {
		LOG.info("Paying {} to {}", payment, recipient);
		client.sendMoney(recipient, payment);
		balance.addAndGet(-payment);
		outputBalance();
	}
	
	private void outputBalance() {
		LOG.info("Trustline balance is {}", balance.get());
	}
}
