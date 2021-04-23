package com.company;

import javax.swing.*;
import java.io.ObjectInputStream;

public class ChatListener implements Runnable {

    private ObjectInputStream input;
    public JTextArea chatOutput;

    public ChatListener(ObjectInputStream input, JTextArea chatOutput) {
       this. input = input;
this.        chatOutput = chatOutput;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String messageIn = (String) (input.readObject());
                chatOutput.append(messageIn);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
