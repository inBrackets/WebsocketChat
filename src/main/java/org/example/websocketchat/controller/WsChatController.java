package org.example.websocketchat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Handles incoming WebSocket messages from users and broadcasts them using topics.
 * <p>
 * Uses STOMP protocol mappings to map incoming WebSocket messages to server-side methods.
 */
@Controller
public class WsChatController {


    /**
     * Handles incoming chat messages from users and broadcasts them to the "/topic/public" topic.
     *
     * @param msg the incoming chat message payload (sent by a client)
     * @return the same message that will be sent to all subscribers of "/topic/public"
     */
    @MessageMapping("chat.sendMessage")  // Maps messages sent to "chat.sendMessage" WebSocket destination
    @SendTo("/topic/public")  // Specifies that the return message will be sent to "/topic/public"
    public WsChatMessage sendMessage(@Payload WsChatMessage msg) {
        // Log the sender and content of the message for debugging
        System.out.println("Message received from " + msg.getSender() + ": " + msg.getContent());

        // Broadcast the message to all subscribers on the "/topic/public" topic
        return msg;
    }

    /**
     * Handles a user joining the chat. Adds the username to the session attributes
     * and notifies others via the "/topic/chat" topic.
     *
     * @param msg the message indicating the user has joined
     * @param headerAccessor allows modifying session attributes like storing the username
     * @return the join message to be sent to all subscribers of "/topic/chat"
     */
    @MessageMapping("chat.addUser")  // Maps messages sent to "chat.addUser" WebSocket destination
    @SendTo("/topic/chat")  // Specifies that the return message will be sent to "/topic/chat"
    public WsChatMessage addUser(@Payload WsChatMessage msg, SimpMessageHeaderAccessor headerAccessor) {
        // Store the username in the WebSocket session attributes
        headerAccessor.getSessionAttributes().put("username", msg.getSender());

        // Log when a user joins the chat
        System.out.println("User joined: " + msg.getSender());

        // Broadcast the user join event to all subscribers on the "/topic/chat" topic
        return msg;
    }

}
