package com.trustline.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trustline.runtime.Trustline;

public class TrustlineClient {
	
	private static final Logger LOG = LogManager.getLogger();
	
	Properties userRegistry;
	
	@Inject
	public TrustlineClient(Properties userRegistry) {
		this.userRegistry = userRegistry;
	}
	
	public void sendMoney(String recipient, int payment) throws Exception {
		try {
			String targetPort = userRegistry.getProperty(recipient);
			if (targetPort == null) {
				throw new RuntimeException("user " + recipient + " not found in registry");
			}
			URL url = createURL(targetPort);
			HttpURLConnection con = getConnection(url);
			con.setRequestMethod("POST");
			con.setRequestProperty("content-Type", "application/json");
			con.setDoOutput(true);
			String body = "{\"amount\":" + payment + "}";
			con.setRequestProperty("Content-Length", Integer.toString(body.length()));
			con.getOutputStream().write(body.getBytes("UTF8"));
			con.getOutputStream().close();
			con.disconnect();
			if (con.getResponseCode() == Response.Status.OK.getStatusCode()) {
				LOG.info("Sent");
			}
			else {
				LOG.error("cannot connect to server at {}", url.toString());
				throw new RuntimeException("cannot connect to server");
			}

		} catch (Exception e) {
			LOG.error("Error creating client connection", e);
			throw e;
		}
	}
	
	URL createURL(String targetPort) throws MalformedURLException {
		return new URL("http://localhost:" + targetPort + "/trustline/account");
	}
	
	HttpURLConnection getConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}
}
