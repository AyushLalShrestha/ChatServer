package com.leapfrog.ChatServer;

import com.leapfrog.ChatServer.handler.ClientHandler;
import com.leapfrog.ChatServer.handler.ClientListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        int port = 5000;
        try {
            ServerSocket server = new ServerSocket(port);
            ClientHandler handler = new ClientHandler();

            System.out.println("Connection established at : " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Connection request from pc : " + client.getInetAddress());
                ClientListener listener = new ClientListener(client, handler);
                listener.start();

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
