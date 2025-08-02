package org.example.websocketchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Spring Boot WebSocket Chat application.
 * <p>
 * This class bootstraps the Spring application and initializes all configurations.
 */
@SpringBootApplication
public class WebsocketChatApplication {

    /**
     * The main method that launches the Spring Boot application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(WebsocketChatApplication.class, args);
    }

}
