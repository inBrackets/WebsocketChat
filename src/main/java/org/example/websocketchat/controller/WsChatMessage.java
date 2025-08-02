package org.example.websocketchat.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a message exchanged via WebSocket in the chat application.
 * <p>
 * This can be a normal chat message, a join notification, or a leave notification.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WsChatMessage {

    /**
     * The username of the sender.
     */
    private String sender;
    /**
     * The actual text content of the message.
     */
    private String content;
    /**
     * The type of message (e.g., JOIN, LEAVE, or CHAT).
     */
    private WsChatMessageType type;

}
