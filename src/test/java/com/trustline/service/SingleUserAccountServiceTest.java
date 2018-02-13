package com.trustline.service;

import static org.mockito.Mockito.*;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.trustline.client.TrustlineClient;


public class SingleUserAccountServiceTest  {
	
	SingleUserAccountService singleUserAccountService;
	
	@Mock
	TrustlineClient trustlineClient;
	
	private static final String RECIPIENT_NAME = "recipient name";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		singleUserAccountService = new SingleUserAccountService(trustlineClient);
	}
	
	@Test
	public void recievePayment_increments_balance() {
		int balance = singleUserAccountService.balance.get();
		singleUserAccountService.receivePayment(10);
		assertEquals(balance + 10, singleUserAccountService.balance.get());
	}
	
	@Test
	public void sendPayment_calls_client_and_decrements_balance() {
		int balance = singleUserAccountService.balance.get();
		singleUserAccountService.sendPayment(RECIPIENT_NAME, 20);
	}
	
}
