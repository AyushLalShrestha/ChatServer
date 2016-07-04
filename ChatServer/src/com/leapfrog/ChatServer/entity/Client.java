package com.leapfrog.ChatServer.entity;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    String userName;
    Socket socket;

    public Client() {
    }

    public Client(String userName, Socket socket) {
        this.userName = userName;
        this.socket = socket;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
