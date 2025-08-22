package jke.webshopbackend2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebshopBackend2Application {

    public static void main(String[] args) {
        SpringApplication.run(WebshopBackend2Application.class, args);
    }

}
