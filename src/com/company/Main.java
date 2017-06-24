package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;





public class Main {



 public static void connect ()
{


    try
    {
        ServerSocket server = new ServerSocket(5555);
        System.out.println("Server is waiting for the connection attempt to estabilish... ");
        Socket clientSocket = server.accept();

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream()); //output send to the client
        BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream()))); //input from the client


    } catch (IOException e) {
        e.printStackTrace();
    } finally
    {

        System.out.println("Server has completed the work");
    }

}


    public static void main(String[] args)

{
    Main.connect();


}

}
