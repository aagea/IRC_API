package com.stratio.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectorIRCHelper implements IConnectorIRC {

    private Map<String, List<Message>> messages = new HashMap<>();
    String nick;

    @Override
    public Socket openConnection(String server) {
        // Connect directly to the IRC server.
        Socket socket = null;
        try {
            socket = new Socket(server, 6667);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    @Override
    public BufferedWriter getWriter(Socket socket) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public BufferedReader getReader(Socket socket) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    @Override
    public boolean connectToIRC(BufferedWriter writer, BufferedReader reader, String login, String nick) {
        boolean res = true;
        // Log on to the server.
        try {
            writer.write("NICK " + nick + "\r\n");
            writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
            writer.flush();

            // Read lines from the server until it tells us we have connected.
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("004") >= 0) {
                    // We are now logged in.
                    break;
                } else if (line.indexOf("433") >= 0) {
                    System.out.println("Nickname is already in use.");
                    return false;
                }
            }
            this.nick = nick;

        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean connectToChannel(BufferedWriter writer, String channel) {
        boolean res = true;
        // Log on to the server.
        try {
            writer.write("JOIN " + channel + "\r\n");
            writer.flush();
            List<Message> list = new ArrayList<>();
            messages.put(channel, list);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean writeIntoChannel(BufferedWriter writer, String channel, String message) {
        boolean res = true;
        if (message != null) {
            try {
                writer.write("PRIVMSG " + channel + " : " + message + "\r\n");

                List<Message> mList = messages.get(channel);
                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
                Message m = new Message();
                m.setTimestamp(timestamp.toString());
                m.setChannel(channel);
                m.setMessage(message);
                m.setUser(nick);
                mList.add(m);
                messages.put(channel, mList);
                writer.flush();
            } catch (IOException e) {
                res = false;
            }
        }
        return res;
    }

    @Override
    public boolean readFromChannel(BufferedWriter writer, String line, String channel) {
        boolean res = true;
        try {
            if (line.toLowerCase().startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush();
            } else {
                // Print the raw line received by the bot.
                try {
                    List<Message> mList = messages.get(channel);
                    Calendar calendar = Calendar.getInstance();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
                    Message m = new Message();
                    m.setTimestamp(timestamp.toString());
                    m.setChannel(channel);
                    m.setMessage(line.substring(line.indexOf(" :") + 2));
                    m.setUser(line.substring(line.indexOf(':') + 1, line.indexOf('!')));
                    mList.add(m);
                    messages.put(channel, mList);
                    System.out.println(line);
                } catch (Exception e) {

                }

            }
        } catch (IOException e) {
            res = false;
        }

        return res;
    }

    @Override
    public List<Message> getLogChannel(String channel) {
        return messages.get(channel);
    }
}
