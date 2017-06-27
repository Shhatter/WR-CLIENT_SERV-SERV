package com.company.Core;

import com.company.Enums.MoveTransferOrder;
import com.company.Enums.PawnColor;
import com.company.Enums.PlayerSide;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by Praca on 2017-06-18.
 */
public class NetworkCommProtocolThread extends Thread{

    PawnColor pawnColor;
    PlayerSide playerSide;
    public boolean activeSession = false; // check if client is able to start new game
    public boolean allowedToMove = false;


    public MoveTransfer moveTransfer= new MoveTransfer();
    public Socket socket = null;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    long  currentThreadID;
    //public Consumer<Serializable> consumer;



    public void sendData(MoveTransferOrder sOrder)
    {
       // out.writeObject(data);
    }
    public void sendData(MoveTransferOrder fillBoard, PlayerSide playerSide, PawnColor pawnColor, boolean allowedToMove)
    {
        moveTransfer.setColor(pawnColor);
        moveTransfer.setRightToMove(allowedToMove);
        moveTransfer.setOrder(fillBoard);



    }

/*    public Consumer<Serializable> getData() throws Exception
    {
        return consumer;
    }*/

/*    public NetworkCommProtocolThread(Socket socket, String threadNumbner)
    {
        super(threadNumbner);
        this.socket = socket;
    }*/



@Override
    public void run()
    {

        currentThreadID = Thread.currentThread().getId();
        System.out.print("Thread ID: "+currentThreadID + "has started the work");

        try {


            System.out.println("Dziala try Klienta ");

/*          PrintWriter out = new PrintWriter(socket.getOutputStream()); //output send to the client
            BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream()))); //input from the client
            socket.close();*/
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());






//            socket.setTcpNoDelay(true); może się przyda - może nie.

        while (true)
        {

            if (java.lang.Thread.activeCount()==2)
            {

                System.out.println("We have both players online !");
                Serializable data = (Serializable) in.readObject();
            }
                else
            {
                //System.out.println("Not enough players !");
            }




//            Serializable data = (Serializable) in.readObject();
//            consumer.accept(data);



        }
//           socket.close();


        } catch (IOException e)
        {
            e.printStackTrace();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  finally {



        }


    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
