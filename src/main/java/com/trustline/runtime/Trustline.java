package com.trustline.runtime;

import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;

import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;
import com.trustline.config.guice.InitializeModulesContextListener;

/**
 * This class is responsible for starting the service and adding the Guice filter for DI
 * 
 * @author aingber
 *
 */
public class Trustline {

	/**
	 * Start Jetty programatically.
	 * 
	 * @param port
	 * @throws Exception
	 */
	private static void startJettyContainer(int port) throws Exception {
		Server server = new Server(port);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addEventListener(new InitializeModulesContextListener());
		context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		context.addServlet(DefaultServlet.class, "/");
		try {
			server.start();
			server.join();
		}
		finally {
			server.destroy();
		}
	}

	public static void main(String[] args) throws Exception {
		String port = args[0];
		MainMapLookup.setMainArguments(args);
		startJettyContainer(Integer.parseInt(port));
	}
}
