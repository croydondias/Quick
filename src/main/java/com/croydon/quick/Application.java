package com.croydon.quick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
//@RepositoryRestConfiguration
public class Application {
	
//	@RequestMapping("/")
//    String home() {
//        return "Hello World! xxx yyy";
//    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}