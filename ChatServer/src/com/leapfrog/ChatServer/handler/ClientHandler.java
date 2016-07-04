package com.leapfrog.ChatServer.handler;

import com.leapfrog.ChatServer.entity.Client;
import com.leapfrog.ChatServer.entity.oneToOne;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {

    List<Client> clients = new ArrayList<>();
    List<oneToOne> blockList;

    public void insertTOBlockList(oneToOne oto) {
        this.getBlockList().add(oto);
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<oneToOne> getBlockList() {
        return blockList;
    }

    public ClientHandler() {
    }

    public void addClient(Client client) {
        this.clients.add(client);

    }

    public Client getByUserName(String userName) {
        for (Client c : clients) {
            if (c.getUserName().equalsIgnoreCase(userName)) {
                return c;
            }
        }
        return null;
    }

    public Client getBySocket(Socket socket) {
        for (Client c : clients) {
            if (c.getSocket() == socket) {
                return c;
            }
        }
        return null;

    }

    public List<Client> getAll() {
        return clients;
    }

    public void broadcastMessage(Client client, String message) throws IOException {
        for (Client c : clients) {
            if (!c.equals(client)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);
            }
        }
    }

    public void broadcastPrivateMessage(Client client, Client destination, String message) throws IOException {
        for (Client c : clients) {
            if (c.equals(destination)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);
            } else {
                PrintStream ps = new PrintStream(client.getSocket().getOutputStream());
                ps.println("This user is not available or has muted you");
            }
        }
    }

}
