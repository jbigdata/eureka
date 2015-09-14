package us.pdavidson;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class Server1Application {

    private static final Logger log = LoggerFactory.getLogger(Server1Application.class);

    @Autowired
    DiscoveryClient discoveryClient;

    RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("SERVER2", false);
        String homePageUrl = instance.getHomePageUrl();

        log.info("Got homepage url {}", homePageUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(homePageUrl, String.class);


        return "hello from server 1 " + response.getBody() ;
    }
}
