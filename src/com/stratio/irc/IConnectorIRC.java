package com.stratio.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;

public interface IConnectorIRC {

    Socket openConnection(String server);

    BufferedWriter getWriter(Socket socket);

    BufferedReader getReader(Socket socket);

    boolean connectToChannel(BufferedWriter writer, String channel);

    boolean connectToIRC(BufferedWriter writer, BufferedReader reader, String login, String nick);

    boolean writeIntoChannel(BufferedWriter writer, String channel, String message);

    boolean readFromChannel(BufferedWriter writer, String line, String channel);

    List<Message> getLogChannel(String channel);

}
