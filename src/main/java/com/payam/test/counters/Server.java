package com.payam.test.counters;

import java.net.URI;
import java.io.IOException;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;


public class Server {
    public static void main(String[] args) throws IOException {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        //ResourceConfig resourceConfig = new ResourceConfig(RestResource.class);
        ResourceConfig resourceConfig = new ResourceConfig(RestResource.class);
        resourceConfig.packages("com.payam.test.counters");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
        System.out.println("Enter terminates...");
        System.in.read();
    }
}