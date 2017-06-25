package com.company.Core;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;


public class Main {

    public static void main(String[] args)

{

    gameController gameController = new gameController();

    System.out.println("Default port used in program is 5555");

    try(ServerSocket server = new ServerSocket(5555))
    {

        System.out.println("Server is waiting for the connection attempt to estabilish... ");
        while (gameController.portListening) {


              gameController.networkConnections.add(new NetworkConnection(server.accept()));
              gameController.networkConnections.get(gameController.networkConnections.size()-1).startConnection();
            /*new NetworkCommProtocolThre*///*ad(server.accept(),Integer.toString(gameController.threadNumber)).start();*/
//            new KKMultiServerThread(serverSocket.accept()).start();
            gameController.threadNumber++;
            System.out.println("New connection has been stabilished! Connection number " + gameController.threadNumber);
            if(gameController.threadNumber ==2)
            {
                gameController.startGame();
                gameController.gameInProgress();
                gameController.endGame();
//                gameController.threadNumber = 0; used to flush current game and see if there are any other connections left to play again
            }


        }
/*

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream()); //output send to the client
        BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream()))); //input from the client
*/ //smieci

    } catch (BindException e)
    {
        System.out.println("Port reserved by another program");
    } catch (IOException e) {
        e.printStackTrace();
    } finally
    {

        System.out.println("Server has completed the work");
    }




}

}
