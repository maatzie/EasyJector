package com.example.app.util;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Tcp {
    private static final int SERVER_PORT = 1234;
    private static final int MSG_SIZE_LENGTH = 4;
    private static final String KEY = "secret";

    private Socket mSocket = null;
    private static BufferedWriter out;
    private static BufferedReader in;

    public Tcp() {
        try {
            InetAddress mTargetAddress = InetAddress.getByName("192.168.1.1");
            mSocket = new Socket(mTargetAddress, SERVER_PORT);

            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String xorCrypt(String message) {
        char[] charMessage = message.toCharArray();
        int keyIndex = 0;

        for (int messageIndex = 0; messageIndex < message.length(); messageIndex++) {
            charMessage[messageIndex] ^= KEY.charAt(keyIndex++);
            if (keyIndex >= KEY.length()) keyIndex = 0;
        }

        return String.valueOf(charMessage);
    }

    public void send(String message) {
        message = xorCrypt(message);
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            char[] messageLengthChar = new char[MSG_SIZE_LENGTH];
            int readChars = in.read(messageLengthChar, 0, MSG_SIZE_LENGTH);
            if (readChars != MSG_SIZE_LENGTH) {
                System.out.println("Incorrect data read");
                return null;
            }
            String messageLengthString = new String(messageLengthChar);
            messageLengthString = messageLengthString.replaceAll("[^\\d.]", "");
            int messageLength = Integer.parseInt(messageLengthString);

            readChars = 0;
            char[] message = new char[messageLength];

            while(readChars < messageLength)
                readChars += in.read(message, readChars, messageLength);

            return xorCrypt(new String(message));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

