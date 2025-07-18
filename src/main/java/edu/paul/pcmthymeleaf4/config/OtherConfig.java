package edu.paul.pcmthymeleaf4.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {


    private static String hostRestAPI;
    private static String enableAutomationTesting;

    public static String getHostRestAPI() {
        return hostRestAPI;
    }

    @Value("${host.rest.api}")
    private void setHostRestAPI(String hostRestAPI) {
        OtherConfig.hostRestAPI = hostRestAPI;
    }

    public static String getEnableAutomationTesting() {
        return enableAutomationTesting;
    }

    @Value("${enable.automation.testing}")
    private void setEnableAutomationTesting(String enableAutomationTesting) {
        OtherConfig.enableAutomationTesting = enableAutomationTesting;
    }
}
