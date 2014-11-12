package com.stratio.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class IRCConnection {
    public static void main(String[] args) throws Exception {
        IConnectorIRC irc = new ConnectorIRCHelper();

        String server = "127.0.0.1";
        String nick = "simple_bot2";
        String login = "simple_bot2";
        String channel = "#irchacks";

        Socket socket=irc.openConnection(server);

        BufferedWriter writer = irc.getWriter(socket);
        BufferedReader reader = irc.getReader(socket);

        irc.connectToIRC(writer,reader,login,nick);
        irc.connectToChannel(writer,channel);

        new IRCListener(reader,writer,channel,irc).start();

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br;
        while(true){
            br = new BufferedReader (isr);
            String message = br.readLine();
            if(message!=null) {
                irc.writeIntoChannel(writer,channel,message);
                writer.flush();
            }
        }


    }
}
