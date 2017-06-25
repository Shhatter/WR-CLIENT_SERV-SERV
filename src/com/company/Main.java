package com.company;

import com.company.Core.CommProtocolThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {



 public static void connect ()
{


}


    public static void main(String[] args)

{
    //Main.connect();

    boolean listening = true;

    System.out.println("Default port used in program is 5555");

    try(ServerSocket server = new ServerSocket(5555))
    {

        System.out.println("Server is waiting for the connection attempt to estabilish... ");
        Socket clientSocket = null;
        while (listening) {
            new CommProtocolThread(server.accept()).start();
        }
/*

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream()); //output send to the client
        BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream()))); //input from the client
*/


    } catch (IOException e) {
        e.printStackTrace();
    } finally
    {

        System.out.println("Server has completed the work");
    }

}

}
