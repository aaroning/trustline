package com.trustline.config.guice;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Sets the classes in the com.trustline.controller to be jersey resources
 * 
 * @author aingber
 *
 */
public class JerseyResourcesModule extends ServletModule {
   
	@Override
    protected void configureServlets() {
        bindResources();
        serveBoundResources();
    }

    private void bindResources() {
        for (Class<?> resource : lookupResources()) {
            bind(resource);
        }
    }

    private Set<Class<?>> lookupResources() {
        PackagesResourceConfig resourceConfig = new PackagesResourceConfig("com.trustline.controller");
        return resourceConfig.getClasses();
    }

    private void serveBoundResources() {
    	Map<String, String> options = new HashMap<>();
        options.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve("/*").with(GuiceContainer.class, options);
    }
}
