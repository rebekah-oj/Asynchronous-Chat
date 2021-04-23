package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class OneShotServer {

    //declare network IO objects
    static String messageIn;
    static String messageOut;

    //declare scanner for local input
    static Scanner keyboardInput;

    //declare object to create port for incoming requests
    static ServerSocket server;

    //declare object to maintain connection
    static Socket connection;

    //declare IO pathways
    //out to client
    static ObjectOutputStream output;
    //in to client
    static ObjectInputStream input;

    //declare main method to execute server process
    public static void main(String[] args) {
        try {
            server = new ServerSocket(12345, 100);
            System.out.println("waiting for client");
            connection = server.accept();
            System.out.println("Client Connected");

            do {
                //1. Establish IO paths between client and server
                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
                input = new ObjectInputStream(connection.getInputStream());

                //3. Send message to server
//            output.writeObject(messageOut);
//            output.flush();

                //4. Wait for an incoming string
                messageIn = (String) input.readObject();

                //5. Print it to the screen
                System.out.println("CLIENT SAYS: " + messageIn);
                System.out.println("RESPONSE: ");
                keyboardInput = new Scanner(System.in);
                messageOut = keyboardInput.nextLine();

                output.writeObject(messageOut);
                output.flush();
            } while (!messageOut.equals("<OUT>"));

        } catch (SocketException e) {
            System.out.println("Oops! : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Oops! : " + e.toString());
            {
                System.out.println();
            }
        }
    }
}
