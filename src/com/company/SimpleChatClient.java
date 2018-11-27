package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
    String filename = "C:\\NotBackedUp\\apache-tomcat-9.0.13\\webapps\\Natter\\chat.txt";
    File file = new File(filename);

    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }

    public void go() {

        Scanner scanner = new Scanner(System.in);
        String message=null;

        try {
            while (true){
            System.out.println("Say something");
            message= scanner.nextLine();
            new Chat().sendMessage(file,message);
                setUpNetworking();

                Thread readerThread = new Thread(new IncomingReader());
                readerThread.start();}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpNetworking() {
        try {
            sock = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class IncomingReader implements Runnable {
        public void run() {
            try {
                new Chat().readMessage(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



