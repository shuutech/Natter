package com.company;

import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Chat  {
    public static final String NEWLINE = "\r\n";

    public void sendMessage(File file, String message) throws Exception {
        Thread.sleep(1000);
        boolean append = true;
        FileWriter fileWriter = new FileWriter(file, append);
        fileWriter.append(NEWLINE);
        fileWriter.append(message);
        fileWriter.close();

    }

    public void readMessage(File file) throws Exception {
        Thread.sleep(1000);
        FileReader fread;
        fread = new FileReader(file);
        Scanner fileScanner = new Scanner(fread);
        StringBuffer sb = new StringBuffer();
        String readLine =null;
        int i=0;
        while (fileScanner.hasNextLine()) {
             readLine = fileScanner.nextLine();
            sb.append(readLine);
        }
        System.out.println(readLine);
        i++;
        System.out.println(i);
    }

}



