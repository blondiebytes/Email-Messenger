package emailclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class EmailClient {

    private static InetAddress host;
    private static final int PORT = 52946;
    private static String name;
    private static BufferedReader userEntry;
    private static BufferedReader in;
    private static PrintWriter out;
    public int count = 0;

    public static void main(String[] args) throws IOException {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        userEntry = new BufferedReader(
                new InputStreamReader(System.in));
        do {
            System.out.print(
                    "\nEnter name ('Dave' or 'Karen'): ");
            name = userEntry.readLine();
        } while (!name.equals("Dave") && !name.equals("Karen"));

        run();
    }

    /**
     * ******************************************************
     *
     * CREATE A SOCKET, SET UP INPUT AND OUTPUT STREAMS, ACCEPT THE USER'S
     * REQUEST, CALL UP THE APPROPRIATE METHOD (doSend OR doRead), CLOSE THE
     * LINK AND THEN ASK IF USER WANTS TO DO ANOTHER READ/SEND.
     *
     *
     *******************************************************
     */
    private static void run() throws IOException {
        String option, message, response;
        Socket clientSocket = null;
        // Creating a socket between the host and the port
        do {
        try {
            clientSocket = new Socket(host, PORT);
        // Creating streams:
            // Input stream from Server to Client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Output stream from Client to Server
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // How user enters data
            
                System.out.println("If you would like to send a message, enter S. "
                        + "If you would like to read your messages, enter R");
                if ("S".equals(userEntry.readLine())) {
                    // send
                        doSend();
                        System.out.println("You've already sent 10 messages");
                    }
                else {
                  // read
                    doRead();
               }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println("\n* Closing connection... *");
                clientSocket.close();
            } catch (IOException ioEX) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
          // Check if the user wants to do another send or read...
          System.out.println("Do you want to do another send or read? If you"
                        + " do not want to, then enter n, otherwise enter any "
                        + "other key.");
         option = userEntry.readLine();
        } while (!option.equals("n"));
       
    }

    private static void doSend() throws IOException {
        System.out.println("\nEnter 1-line message: ");
        String message = userEntry.readLine();
        out.println(name);
        out.println("send");
        out.println(message);
    }

    
    
   /**
         * *******************************
         * BODY OF THIS METHOD REQUIRED *******************************
         */
    private static void doRead() throws IOException {
        out.println(name);
        out.println("read");
        // Add read and name println
        int count = Integer.parseInt(in.readLine());
        while (count > 0) {
            // Read
            System.out.println(in.readLine());
            count--;
        }
        System.out.println("Finished reading messages.");
    }
}
