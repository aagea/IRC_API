package com.stratio.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class IRCListener extends Thread {
    BufferedReader reader;
    BufferedWriter writer;
    String channel;
    IConnectorIRC irc;

    public IRCListener(BufferedReader reader, BufferedWriter writer, String channel, IConnectorIRC irc) {
        this.reader = reader;
        this.writer = writer;
        this.channel = channel;
        this.irc=irc;
    }

    public void listener() throws Exception {
        // The server to connect to and our details.
        String line = "";
        while ((line = reader.readLine()) != null) {
            irc.readFromChannel(writer,line,channel);
        }
    }

    public void run() {
        try {
            listener();
        } catch (Exception e) {
            System.out.println("EEEEEEEERROR:  " + e.getMessage());
        }
    }

}
