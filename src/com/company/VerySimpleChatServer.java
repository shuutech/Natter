package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class VerySimpleChatServer {

    ArrayList clientOutputStream;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocklet) {
            try {
                sock = clientSocklet;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read" + message);
                    tellEveryone(message);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new VerySimpleChatServer().go();
    }

    public void go(){
        clientOutputStream = new ArrayList();
        String filename = "C:\\NotBackedUp\\apache-tomcat-9.0.13\\webapps\\Natter\\chat.txt";
        File file = new File(filename);
        try {
            ServerSocket serverSock = new ServerSocket(5000);
             while (true){
                 Socket clientSocket = serverSock.accept();
                 PrintWriter writer = new PrintWriter(((Socket) clientSocket).getOutputStream());
                 clientOutputStream.add(writer);

                 Thread t = new Thread(new ClientHandler(clientSocket));
                 t.start();
                 new Chat().readMessage(file);

             }
    }
    catch (Exception ex){
        ex.printStackTrace();}
    }

    public void tellEveryone(String message){
        Iterator it = clientOutputStream.iterator();
        while(((Iterator) it).hasNext()){
            try{
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

