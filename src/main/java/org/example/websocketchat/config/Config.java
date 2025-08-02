package org.example.websocketchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configures WebSocket message handling and broker settings for the chat application.
 * <p>
 * This class enables WebSocket support and defines:
 * - STOMP endpoints (used by clients to connect)
 * - Message broker destinations for sending/receiving messages
 */
@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {


    /**
     * Registers the STOMP endpoint that clients will use to connect to the WebSocket server.
     * It allows cross-origin connections from specified URLs and enables SockJS fallback.
     *
     * @param registry the registry to add STOMP endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200", "http://localhost:8080")
                .withSockJS();
    }


    /**
     * Configures the message broker used to route messages between clients and server.
     *
     * @param registry the registry to configure the message broker
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
