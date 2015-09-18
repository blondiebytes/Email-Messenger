
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private static ServerSocket serverSocket;
    private static final int PORT = 52946;
    private static ArrayList<PrintWriter> outputStreams = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("Unable to set up port!");
            System.exit(1);
        }

        do {
            //Wait for client...
            System.out.println("Waiting for a new client!");
            Socket client = serverSocket.accept();
            System.out.println("New client accepted!");
            //Create a thread to handle communication with
            //this client and pass the constructor for this
            //thread a reference to the relevant socket...
            ClientHandler handler = new ClientHandler(client, outputStreams);

            handler.start();//As usual, this method calls run.
        } while (true);
    }
    
}
