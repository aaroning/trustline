package com.trustline.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mock;


import com.trustline.runtime.Trustline;

import static org.mockito.Mockito.*;

public class TrustlineClientTest {
	
	private final TrustlineClient trustlineClient = new TrustlineClient();
	
	
	@Mock
	Properties properties;
	
}
