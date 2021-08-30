package de.lukasringel.economy.http.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EconomyHttpServerApplication {

    /**
     * Main entry point of the application which bootstraps our spring application
     *
     * @param args - provided startup parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(EconomyHttpServerApplication.class, args);
    }

}
