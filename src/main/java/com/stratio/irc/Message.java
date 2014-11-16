package com.stratio.irc;

import java.util.Calendar;
import java.util.Calendar;

public class Message {
    private final Calendar timestamp;
    private final String user;
    private final String host;
    private final String channel;
    private final String message;

    public Message(String user, String host, String channel, String message) {
        timestamp = Calendar.getInstance();
        this.user = user;
        this.host = host;
        this.channel = channel;
        this.message = message;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }
}
