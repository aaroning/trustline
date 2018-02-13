package com.trustline.service;

public interface AccountService {

	void receivePayment(int payment);

	void sendPayment(String name, int payment);

}
