package org.example.websocketchat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocketchat.controller.WsChatMessage;
import org.example.websocketchat.controller.WsChatMessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

/**
 * Listens for WebSocket-related lifecycle events, such as user disconnections.
 * <p>
 * When a user disconnects, this listener broadcasts a LEAVE message to inform others.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WsEventListener {

    /**
     * Used to send messages to destinations (e.g., topics) over WebSocket.
     */
    private final SimpMessageSendingOperations messageSendingOperations;

    /**
     * Called automatically when a user disconnects from the WebSocket session.
     * Sends a LEAVE message to all subscribers of "/topic/public".
     *
     * @param event the session disconnect event triggered by the framework
     */
    @EventListener
    public void handleWsDisconnectListener(SessionDisconnectEvent event) {
        //To listen to another even, create the another method with NewEvent as argument.
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User disconnected: {} ", username);
            var leaveMessage = WsChatMessage.builder()
                    .type(WsChatMessageType.LEAVE)
                    .sender(username)
                    .build();
            //pass the leaveMessage to the broker specific topic : public
            // Notifies all connected clients that the user has left
            messageSendingOperations.convertAndSend("/topic/public", leaveMessage);
        }
    }

    /**
     * Called automatically when a new WebSocket connection is established.
     * Can be used to log or notify other users that someone connected.
     *
     * @param event the session connect event triggered when a user connects
     */
    @EventListener
    public void handleWsConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Optional: extract custom headers if the client sends any (e.g., username)
        String username = headerAccessor.getNativeHeader("username") != null
                ? headerAccessor.getNativeHeader("username").get(0)
                : "Anonymous";

        log.info("New WebSocket connection established. Username: {}", username);

        // Optional: store username in session attributes for later use
        headerAccessor.getSessionAttributes().put("username", username);

        // Optional: send JOIN notification to others
        WsChatMessage joinMessage = WsChatMessage.builder()
                .type(WsChatMessageType.JOIN)
                .sender(username)
                .build();

        messageSendingOperations.convertAndSend("/topic/public", joinMessage);
    }


}
