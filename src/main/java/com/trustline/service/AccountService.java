package com.trustline.service;

/**
 * An interface for an account service. This could be implemented for a multi-account service.
 * 
 * @author aingber
 *
 */
public interface AccountService {

	
	/**
	 * Receive a payment and credit it to the user's balance
	 * 
	 * @param payment
	 */
	void receivePayment(int payment);

	/**
	 * Send a payment and deduct it from the user's balance
	 * 
	 * @param name
	 * @param payment
	 * @throws Exception
	 */
	void sendPayment(String name, int payment) throws Exception;

}
