package com.trustline.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TrustlineClientTest {
	
	private TrustlineClient trustlineClient;
	
	private static final String RECIPIENT = "test user";
	private static final String PORT_STRING = "8080";
	
	@Mock
	Properties properties;
	
	private static URL url; 
	
	@Mock
	HttpURLConnection connection;
	
	@Mock
	OutputStream outputStream;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		trustlineClient = spy(new TrustlineClient(properties));
		url = new URL("http://localhost:8080/trustline/account");
		doReturn(url).when(trustlineClient).createURL(PORT_STRING);
		doReturn(connection).when(trustlineClient).getConnection(url);
		doReturn(outputStream).when(connection).getOutputStream();
		doReturn("8080").when(properties).getProperty(RECIPIENT);
	}
	
	@Test
	public void sendMoney_returns_200() throws Exception {
		doReturn(Response.Status.OK.getStatusCode()).when(connection).getResponseCode();
		
		trustlineClient.sendMoney(RECIPIENT, 50);
		
		verify(connection).setRequestMethod("POST");
		verify(connection).setRequestProperty("content-Type", "application/json");
		verify(connection).setDoOutput(true);
		String payload = "{\"amount\":50}";
		verify(connection).setRequestProperty("Content-Length", Integer.toString(payload.length()));
		verify(connection, times(2)).getOutputStream();
		verify(outputStream).write(payload.getBytes("UTF8"));
		verify(outputStream).close();
		verify(connection).disconnect();
	}
	
	@Test(expected = RuntimeException.class)
	public void sendMoney_user_not_found_throws_exception() throws Exception {
		doReturn(null).when(properties).getProperty(RECIPIENT);
		
		trustlineClient.sendMoney(RECIPIENT, 50);
	}
	
	@Test(expected = RuntimeException.class)
	public void sendMoney_returns_error_code() throws Exception {
		doReturn(Response.Status.BAD_REQUEST.getStatusCode()).when(connection).getResponseCode();
		
		trustlineClient.sendMoney(RECIPIENT, 50);
		
	}
	
}
