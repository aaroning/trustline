package com.trustline.domain;
import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentTest {
	private final Payment payment = new Payment();
	
	@Test
	public void testAccessors() {
		payment.amount = 10;
		assertEquals(10, payment.getAmount());
		payment.setAmount(20);
		assertEquals(20, payment.getAmount());
	}
}
