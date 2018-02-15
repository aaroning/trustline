package com.trustline.config.guice;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.trustline.client.TrustlineClient;

public class TrustlineClientModule extends AbstractModule {
	
	private static final Logger LOG = LogManager.getLogger();

	@Override
	protected void configure() {
		Properties userRegistry = new Properties();
		try {
			userRegistry.load(new FileInputStream("./src/main/resources/userRegistry.properties"));
		} catch (Exception e) {
			LOG.error("Error loading user registry", e);
		} 
		bind(Properties.class).toInstance(userRegistry);
		bind(TrustlineClient.class).in(Singleton.class);

		
	}
}
