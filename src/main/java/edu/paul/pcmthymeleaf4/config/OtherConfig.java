package edu.paul.pcmthymeleaf4.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {


    private static String hostRestAPI;

    public static String getHostRestAPI() {
        return hostRestAPI;
    }

    @Value("${host.rest.api}")
    private void setHostRestAPI(String hostRestAPI) {
        OtherConfig.hostRestAPI = hostRestAPI;
    }
}
