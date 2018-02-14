package com.trustline.client;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trustline.runtime.Trustline;

public class TrustlineClient {
	
	private static final Logger LOG = LogManager.getLogger();
	
	Properties props = new Properties();
	
	public TrustlineClient() {
		try {
			props.load(new FileInputStream("./src/main/resources/userRegistry.properties"));
		} catch (Exception e) {
			LOG.error("Error loading user registry", e);
		} 
	}
	
	public void sendMoney(String recipient, int payment) {
		try {
			String targetPort = props.getProperty(recipient);
			if (targetPort == null) {
				throw new RuntimeException("user not found in registry");
			}
			URL url = new URL("http://localhost:" + targetPort + "/trustline/account");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("content-Type", "application/json");
			con.setDoOutput(true);
			String body = "{\"amount\":" + payment + "}";
			con.setRequestProperty("Content-Length", Integer.toString(body.length()));
			con.getOutputStream().write(body.getBytes("UTF8"));
			con.getOutputStream().close();
			con.disconnect();
			if (con.getResponseCode() == 200) {
				LOG.info("Sent");
			}
			else {
				throw new RuntimeException("cannot connect to server at + url.toString()");
			}

		} catch (Exception e) {
			LOG.error("Error creating client connection", e);
		}

	}
}
