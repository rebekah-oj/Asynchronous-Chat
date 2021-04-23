package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class OneShotClient {

    //declare network IO objects
    static String messageIn;
    static String messageOut;

    //declare scanner for local input
    static Scanner keyboardInput;

    //declare object to make connection
    static Socket client;

    //declare IO pathways
    //out to client
    static ObjectOutputStream output;

    //in to client
    static ObjectInputStream input;

    //declare main method to execute server process
    public static void main(String[] args) {

        try {
            client = new Socket(InetAddress.getByName("127.0.0.1"), 12345);

            do {
                //1. Establish IO paths between client and server
                output = new ObjectOutputStream(client.getOutputStream());
                output.flush();
                input = new ObjectInputStream(client.getInputStream());

                //2. Wait for our client user to type a response
                System.out.println("Please type your message: ");
                keyboardInput = new Scanner(System.in);
                messageOut = keyboardInput.nextLine();

                //3. Send message to server
                output.writeObject(messageOut);
                output.flush();

                if (messageOut.equals("<OUT>")) {
                    break;
                } else if (messageOut.equals("<OVER>"))
                    break;

                //4. Wait for an incoming string object
                messageIn = (String) input.readObject();

                //5. Print it to the screen
                System.out.println("SERVER SAYS: " + messageIn);

            } while (!messageOut.equals("<OUT>"));
        } catch (SocketException e) {
            System.out.println("Oops! : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception occurred");

            {
                System.out.println();
            }
        }

    }

}




