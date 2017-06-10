package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

enum pawnColor
{
    RED,WHITE;




}
enum playerSide
{
    TOP,BOTTOM
}



public class Main {

    public static void main(String[] args) {



        try
        {
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is waiting for the connection attempt to estabilish... ");
            Socket clientSocket = server.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));




        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {

            System.out.println("Server has completed the work");
        }


}

}
