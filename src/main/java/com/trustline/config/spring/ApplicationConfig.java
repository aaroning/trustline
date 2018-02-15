package com.trustline.config.spring;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.trustline.client.TrustlineClient;
import com.trustline.service.AccountService;
import com.trustline.service.SingleUserAccountService;

/**
 * Spring application config (if we prefer to use that for DI over Guice)
 * 
 * @author aingber
 *
 */
@Configuration
@ComponentScan(value="com.trustline.controller")
public class ApplicationConfig {

	@Bean
	public AccountService accountService() throws FileNotFoundException, IOException {
		return new SingleUserAccountService(trustlineClient());
	}
	
	@Bean
	public TrustlineClient trustlineClient() throws FileNotFoundException, IOException {
		return new TrustlineClient(userRegistry());
	}
	
	@Bean
	public Properties userRegistry() throws FileNotFoundException, IOException {
		Properties userRegistry = new Properties();
		userRegistry.load(new FileInputStream("./src/main/resources/userRegistry.properties"));
		return userRegistry;
	}
}
