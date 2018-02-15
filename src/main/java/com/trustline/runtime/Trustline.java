package com.trustline.runtime;

import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;

import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.trustline.config.guice.InitializeModulesContextListener;
import com.trustline.config.spring.ApplicationConfig;

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
		// change this line to use spring config instead of Guice
		initializeGuiceConfig(context);
//		initializeSpringConfig(context);
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
	
	
	/**
	 * Use Guice for DI
	 * 
	 * @param context
	 */
	private static void initializeGuiceConfig(ServletContextHandler context) {
		context.addEventListener(new InitializeModulesContextListener());
		context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
	}
	
	/**
	 * Use Spring for DI
	 * 
	 * @param context
	 */
	private static void initializeSpringConfig(ServletContextHandler context) {
		context.addEventListener(new ContextLoaderListener());
		context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation", ApplicationConfig.class.getName());
		ServletHolder servletHolder = new ServletHolder(new SpringServlet());
		servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "com.trustline.controller");
		context.addServlet(servletHolder,"/*");
	}
}
