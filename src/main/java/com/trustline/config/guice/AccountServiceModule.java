package com.trustline.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.trustline.service.AccountService;
import com.trustline.service.SingleUserAccountService;


/**
 * The guice module to make any injection of account service an instance of SingleUserAccountUser. We could support multiple accounts per server in the future.
 * 
 * @author aingber
 *
 */
public class AccountServiceModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(AccountService.class).to(SingleUserAccountService.class).in(Singleton.class);
	}
}
