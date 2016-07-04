package com.leapfrog.ChatServer.handler;

import com.leapfrog.ChatServer.dao.UserDAO;
import com.leapfrog.ChatServer.dao.impl.UserDAOImpl;
import com.leapfrog.ChatServer.entity.Client;
import com.leapfrog.ChatServer.entity.User;
import com.leapfrog.ChatServer.entity.oneToOne;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

public class ClientListener extends Thread {

    private Socket socket;
    private ClientHandler handler;
    private Client client;
    private UserDAO userDAO = new UserDAOImpl();
    private PrintStream pstream;
    private BufferedReader reader;

    public ClientListener(Socket client, ClientHandler handler) throws IOException {
        this.socket = client;
        this.handler = handler;
        pstream = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            pstream.println("You are now connected to the server, server time " + new Date());
            while (!doLogin()) {
                pstream.println("Invalid userName or password or not Activated ");
            }

            while (true) {
                pstream.print(" > ");
                String line = reader.readLine();
                String[] messageArray = line.split(";;");

                if (messageArray[0].equalsIgnoreCase("pm")) {
                    try {
                        Client destination = handler.getByUserName(messageArray[1]);
                        if (checkIfAvailable(destination, client)) {
                            handler.broadcastPrivateMessage(client, destination, client.getUserName() + " personally says " + messageArray[2]);
                        }
                    } catch (NullPointerException ex) {
                        pstream.println("No such Clients exists");
                    }

                } else if (messageArray[0].equalsIgnoreCase("list")) {
                    List<Client> c = handler.getAll();
                    for (Client cl : c) {
                        pstream.println(cl.getUserName() + " is @ " + cl.getSocket().getInetAddress());
                    }

                } else if (messageArray[0].equalsIgnoreCase("email")) {
                    Client destination = handler.getByUserName(messageArray[1]);
                    handler.broadcastPrivateMessage(client, destination, "You got an Email from this person : " + client.getUserName());
                    pstream.println("Your Email has been sent");

                } else if (messageArray[0].equalsIgnoreCase("url")) {
                    grabAndWrite(messageArray[1]);
                  

                } else if (messageArray[0].equalsIgnoreCase("block")) {
                    pstream.println("Blocking function is not available");

                } else if (messageArray[0].equalsIgnoreCase("mute")) {
                    try {
                        Client blockSource = client;
                        Client blockDestination = handler.getByUserName(messageArray[1]);
                        handler.insertTOBlockList(new oneToOne(blockSource, blockDestination));
                        pstream.println(handler.getByUserName(messageArray[1] + " will not be able to message you now "));

                    } catch (NullPointerException ex) {
                        pstream.println("No such client exists that can be blocked");
                    }

                } else {
                    handler.broadcastMessage(client, client.getUserName() + " says > " + line);
                }

            }
        } catch (IOException e) {
            pstream.println(e.getMessage());
        }
    }

// ===============================================================//
    private boolean doLogin() throws IOException {
        pstream.println("Enter the userName : ");
        String userName = reader.readLine();
        pstream.println("Enter the password : ");
        String password = reader.readLine();

        User user = userDAO.login(userName, password);
        if (user == null) {
            return false;
        } else if (user.isStatus() == false) {
            return false;
        }

        client = new Client(userName, socket);
        handler.addClient(client);
        return true;
    }

    //========================================================================//
    public boolean checkIfAvailable(Client source, Client destination) {
        try {
            List<oneToOne> oto = handler.getBlockList();
            for (int i = 0; i < handler.getBlockList().size(); i++) {
                if (oto.get(i).getBlockSource().equals(source) && oto.get(i).getBlockDestination().equals(destination)) {
                    return false;
                }

            }
        } catch (NullPointerException ex) {
            return true;
        }
        return true;
    }

    //=========================================================================//
    private void grabAndWrite(String u) {
        try {
            URL url = new URL(u);
            // pstream.println(u);
            URLConnection conn = url.openConnection();
            StringBuilder content = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while (r.readLine() != null) {
                content.append(r.readLine());
            }
            pstream.println(content);
            /*FileWriter writer = new FileWriter("d:/Browser.txt");
             writer.write("Oe batti khai");
             writer.close();  */
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " Could not Connect");

        }
    }

}
