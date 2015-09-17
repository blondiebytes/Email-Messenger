package chatclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame  implements ActionListener {

    // Sockets & Streams
    private static InetAddress host;
    private static final int PORT = 52946;
    private static Socket socket;
    private static Scanner networkInput;
    private static PrintWriter networkOutput;
    
    
    
    // UI
    private static ChatClient frame;
    private JTextArea chatArea;
    private JTextArea messageArea;
    private JButton sendButton;
    private String message = "";

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        
        frame = new ChatClient();
        frame.setUpStreams();
        frame.setTitle("A Simple Chat Client");
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        while(true) {
           frame.readMessage();
        }
        
    }



    public ChatClient() {
        chatArea = new JTextArea(20, 50);
        chatArea.setWrapStyleWord(true);
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        JLabel prompt = new JLabel("Enter Message: ");
        messageArea = new JTextArea(5, 50);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        sendPanel.add(prompt, BorderLayout.WEST);
        sendPanel.add(messageArea, BorderLayout.CENTER);
        sendPanel.add(sendButton, BorderLayout.EAST);
        add(sendPanel, BorderLayout.SOUTH);

        String handle = JOptionPane.showInputDialog(frame,
                                                    "Enter Your Nickname: ",
                                                    "Name Entry",
                                                    JOptionPane.PLAIN_MESSAGE);
        System.out.println("User name dialog input was: " + handle);

       // Code to connect with server and set up client
        // thread for receiving and displaying messages
        // from server.
        setUpStreams();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            System.out.println("User pressed SEND button.");
            message = messageArea.getText();
            System.out.println("User typed message: " + message);
            messageArea.setText(""); 
            if (message.equals("Bye")) {
                // Code to close connection if user typed "Bye".
                try {
                    System.out.println("Closing connection...");
                    socket.close();
                } catch (IOException ioEx) {
                    System.out.println("Unable to disconnect!");
                    System.exit(1);
                }
            } else {
                // Code to send any other message to the server.
                sendMessage();
            }
        }
    }
    
    // Code to connect with server and set up client
    // thread for receiving and displaying messages
    // from server.
    public void setUpStreams() {
         try {
             // Making a socket
            socket = new Socket(host, PORT);
            // Setting up I/O Streams
            networkInput = new Scanner(socket.getInputStream());
            networkOutput = new PrintWriter(socket.getOutputStream(), true);
            
            // Let's get some chat messages!
//             String message, response;
//            do {
//                System.out.print("Enter message ('QUIT' to exit): ");
//                message = userEntry.nextLine();
//                networkOutput.println(message);
//                response = networkInput.nextLine();
//                System.out.println("SERVER> " + response);
//            } while (!message.equals("QUIT"));
            
            
         } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
    
    public void sendMessage() {
        System.out.println("Sending Message...");
        networkOutput.println(message);
    }
    
    public void readMessage() {
        // getting a response
        String response = networkInput.nextLine();
        if (!response.isEmpty()) {
            // appending to the chat area
            chatArea.append(response);
        }
    }

 
}