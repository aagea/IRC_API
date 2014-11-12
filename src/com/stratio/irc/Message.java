package com.stratio.irc;

import java.util.List;

public class Message {
    private String timestamp;
    private String user;
    private String message;
    private String channel;

    public Message(){

    }

    public Message(String timestamp, String user, String message, String channel) {
        this.timestamp = timestamp;
        this.user = user;
        this.message = message;
        this.channel = channel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
