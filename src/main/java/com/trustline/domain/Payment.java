package com.trustline.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The domain object for the payload
 * 
 * @author aingber
 *
 */
@XmlRootElement 
public class Payment {
	int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
