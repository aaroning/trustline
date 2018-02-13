package com.trustline.controller;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.trustline.domain.Payment;
import com.trustline.service.AccountService;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrustlineResourceTest {
	
	private TrustlineResource trustlineResource;
	
	@Mock
	AccountService accountService;
	
	private static final String RECIPIENT_NAME = "recipient name";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		trustlineResource = new TrustlineResource(accountService);	
	}
	
	@Test
	public void receiveFunds_calls_accountService() {
		Payment payment = new Payment();
		payment.setAmount(69);
		Response response = trustlineResource.receiveFunds(payment);
		verify(accountService).receivePayment(69);
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void sendFunds_calls_accountService() {
		Payment payment = new Payment();
		payment.setAmount(69);
		Response response = trustlineResource.sendFunds(RECIPIENT_NAME, "69");
		verify(accountService).sendPayment(RECIPIENT_NAME, 69);
		assertEquals(200, response.getStatus());
	}

}
