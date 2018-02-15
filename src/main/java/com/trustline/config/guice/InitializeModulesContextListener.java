package com.trustline.config.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Initializes the juice modules
 * 
 * @author aingber
 *
 */
public class InitializeModulesContextListener extends GuiceServletContextListener {
    
	@Override
    protected Injector getInjector() {
        return Guice.createInjector(new JerseyResourcesModule(), new AccountServiceModule(), new TrustlineClientModule());
    }
}
