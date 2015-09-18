package chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {

    // Socket to talk to the client
    private Socket client;
    
    // Setting up streams
    private Scanner networkInput;
    private PrintWriter networkOutput;
    
    // Name of the chatter
    private String name;
    
    // Container of all clients in the chat room
    private ArrayList<PrintWriter> outputStreams;
    
    public ClientHandler(Socket socket, ArrayList<PrintWriter> outputStreams){
        //Set up reference to associated socket...
        this.client = socket;
        this.outputStreams = outputStreams;
        
        // Set up scanners
        try {
            networkInput =  new Scanner(client.getInputStream());
            networkOutput = new PrintWriter(client.getOutputStream(), true);
             outputStreams.add(networkOutput);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
       
        System.out.println("Streams set up");
    }

    public void run(){
          // Get the client's name
        name = networkInput.nextLine();
        
        System.out.println("The client's name is" + name);
        
        // Send out to the other clients that someone has 
        // entered the chat room
        enterChatroom();
        
        // Continue chatroom!
        String received;
        do {
           // get a message
            received = name + ": " + read();
            // add a name to the front
            // send it out to everyone
            sendOut(received);

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
    
    private void enterChatroom() {
        String helloChatter = name + " has entered the chat room";
        System.out.println("About to get in!");
        sendOut(helloChatter);
        System.out.println("In the chatroom");
    }
    
    private void sendOut(String s) {
        // send out the message to all the clients
        for (PrintWriter output : outputStreams) {
           output.println(s);
        }
        System.out.println("Sent out to all!");
    }
    
    private String read() {
         System.out.println("Read input!");
        return networkInput.nextLine();
    }
    
}
