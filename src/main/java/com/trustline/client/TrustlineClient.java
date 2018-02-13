package com.trustline.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.trustline.runtime.Trustline;

public class TrustlineClient {
	
	private static final Logger LOG = LogManager.getLogger();
	
	Client client = ClientBuilder.newClient();
	
	Properties props = new Properties();
	
	public TrustlineClient() {
		try {
			props.load(new FileInputStream("./src/main/resources/userRegistry.properties"));
		} catch (Exception e) {
			LOG.error("Error loading user registry", e);
		} 
	}
	
	public void sendMoney(String recipient) {
		try {
			//String targetPort = props.getProperty(recipient);
			String targetPort="8081";
			WebTarget webTarget = client.target("http://localhost:" + targetPort + "/trustline/account");
			Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
			Response response = invocationBuilder.get();
			
			LOG.info("Sent");

		} catch (Exception e) {
			LOG.error("Error creating client connection", e);
		}

	}
}
