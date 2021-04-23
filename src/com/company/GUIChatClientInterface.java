package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIChatClientInterface extends JFrame {

    private JTextArea chatInput;
    private JTextArea chatOutput;
    private JButton chatSend;
    private JPanel chatInputContainer;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;

    //constructor that displays the form when an instance is created
    public GUIChatClientInterface() {
        //set title bar
        super("Chatter");

        //set dimensions
        setSize(500, 500);

        //tell it to appear
        setVisible(true);

        //determines the layout
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        //add some controls
        chatOutput = new JTextArea();
        chatInput = new JTextArea(3, 20);
        chatSend = new JButton("Send");

        chatInputContainer = new JPanel();
        chatInputContainer.add(chatInput);
        chatInputContainer.add(chatSend);
        add(chatInputContainer, BorderLayout.SOUTH);

        chatSend.addActionListener(
                e -> sendData(chatInput)
        );

        /** add(chatOutput, BorderLayout.CENTER);
         add(chatInput, BorderLayout.SOUTH);
         add(chatSend, BorderLayout.SOUTH);**/
    }

    public static void main(String[] args) {
        GUIChatClientInterface chatter = new GUIChatClientInterface();
        chatter.connectToServer();
    }

    private void sendData(JTextArea out) {
        try {
            output.writeObject(out.getText());
        } catch (Exception e) {
            System.out.println("oops! : " + e.toString());
        }
    }

    public void connectToServer() {
        try {
            String targetIP = JOptionPane.showInputDialog("Enter server IP Address");
            connection = new Socket(targetIP, 12345);

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());

            ExecutorService listen = Executors.newFixedThreadPool(1);//creating a pool of one thread
            ChatListener cl = new ChatListener(input, chatOutput);
            listen.execute(cl);

        } catch (Exception e) {
            System.out.println("Oops! : " + e.toString());
        }
    }
}
