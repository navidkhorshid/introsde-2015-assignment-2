package ehealth;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("ehealth")
public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig () {
        packages("ehealth");
    }
}
