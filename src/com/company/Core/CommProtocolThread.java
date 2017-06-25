package com.company.Core;

import com.company.Enums.PawnColor;
import com.company.Enums.PlayerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Praca on 2017-06-18.
 */
public class CommProtocolThread extends Thread{

    PawnColor pawnColor;
    PlayerSide playerSide;
    private Socket socket = null;

    CommProtocolThread(Socket socket)
    {
        super("CommProtocolThread");
        this.socket = socket;
    }

    public void run()
    {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream()); //output send to the client
            BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream()))); //input from the client
            socket.close();


        } catch (IOException e)
        {
            e.printStackTrace();


        } finally {
        }


    }








}
