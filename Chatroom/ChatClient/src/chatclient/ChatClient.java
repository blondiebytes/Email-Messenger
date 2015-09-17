package chatclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame  implements ActionListener {

    private static ChatClient frame;
    private JTextArea chatArea;
    private JTextArea messageArea;
    private JButton sendButton;
    private String message = "";

    public static void main(String[] args) {

        frame = new ChatClient();
        frame.setTitle("A Simple Chat Client");
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        // The line below simply demonstrates how to write text
        // into the chat area.
        chatArea.append("Message written into chat area.");
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            System.out.println("User pressed SEND button.");
            message = messageArea.getText();
            System.out.println("User typed message: " + message);
            messageArea.setText(""); 
            if (message.equals("Bye")) {
                // Code to close connection if user typed "Bye".
            } else {
                // Code to send any other message to the server.
            }
        }
    }

 
}