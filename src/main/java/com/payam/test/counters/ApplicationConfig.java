package com.payam.test.counters;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig  extends ResourceConfig {
    public ApplicationConfig() {
        register(new MyAppBinder());
        packages(true, "com.payam.test.counters");
    }
}