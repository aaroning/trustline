package com.trustline.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.trustline.service.AccountService;
import com.trustline.service.SingleUserAccountService;

public class AccountServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AccountService.class).to(SingleUserAccountService.class).in(Singleton.class);;	
	}
}
