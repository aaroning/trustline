package com.trustline.config.spring;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.trustline.service.AccountService;
import com.trustline.service.SingleUserAccountService;

public class ApplicationConfigTest {
	
	private final ApplicationConfig applicationConfig = new ApplicationConfig();
		
	@Test
	public void accountService_returns_singleUserAccountService() throws FileNotFoundException, IOException {
		AccountService result = applicationConfig.accountService();
		assertTrue(result instanceof SingleUserAccountService);
	}
	
	@Test
	public void trustlineClient_returns_client_no_exception() throws FileNotFoundException, IOException {
		assertNotNull(applicationConfig.trustlineClient());
	}
	
	@Test
	public void userRegistry_loads_registry() throws FileNotFoundException, IOException {
		Properties userRegistry = applicationConfig.userRegistry();
		String alicePort = userRegistry.getProperty("Alice");
		String bobPort = userRegistry.getProperty("Bob");
		assertEquals("8080", alicePort);
		assertEquals("8081", bobPort);
	}
}
