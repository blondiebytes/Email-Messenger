package chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {

    private Socket client;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket) {
        //Set up reference to associated socket...
        client = socket;
        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void run() {
        String received;
        do {
            //Accept message from client on
            //the socket's input stream...
            received = input.nextLine();

            // DO MESSAGE SENDING & OTHER WORK HERE HERE
            output.println(received);

        //Repeat above until 'QUIT' sent by client...
        } while (!received.equals("BYE"));

        try {
            if (client != null) {
                System.out.println("Closing down connection...");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }
}
