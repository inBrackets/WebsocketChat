package org.example.websocketchat.controller;

/**
 * Enum representing the type of message being sent or received.
 */
public enum WsChatMessageType {
    /**
     * Represents a message when a user joins the chat.
     */
    JOIN,
    /**
     * Represents a message when a user leaves the chat.
     */
    LEAVE,
    /**
     * Represents a normal chat message.
     */
    CHAT

}
