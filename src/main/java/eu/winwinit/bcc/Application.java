package eu.winwinit.bcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("eu.winwinit.bcc.controllers")
@ComponentScan("eu.winwinit.bcc.security")
@ComponentScan("eu.winwinit.bcc.config")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
