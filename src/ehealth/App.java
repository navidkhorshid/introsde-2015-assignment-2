package ehealth;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class App
{
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
        String port_value = "5711";
        try
        {
            if (String.valueOf(System.getenv("PORT")) != "null"){

                port_value=String.valueOf(System.getenv("PORT"));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        URI BASE_URI = null;

        try
        {
            String port = ":"+port_value+"/";
            String hostname = InetAddress.getLocalHost().getHostAddress();

            BASE_URI = new URI("http://" + hostname + port+"ehealth/");
            JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");
    }

    public static ResourceConfig createApp()
    {
        MyApplicationConfig myApplicationConfig = new MyApplicationConfig();
        return myApplicationConfig;
    }
}
