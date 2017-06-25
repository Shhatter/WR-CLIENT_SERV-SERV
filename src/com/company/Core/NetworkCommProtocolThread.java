package com.company.Core;

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

    public MoveTransfer moveTransfer= new MoveTransfer();
    public Socket socket = null;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public String currentThreadID;
    //public Consumer<Serializable> consumer;



    public void sendData(Serializable data) throws Exception
    {
        out.writeObject(data);
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
        System.out.println("Dziala watek Klienta");

        try {


            System.out.println("Dziala try Klienta ");

/*          PrintWriter out = new PrintWriter(socket.getOutputStream()); //output send to the client
            BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream()))); //input from the client
            socket.close();*/
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.socket = socket;
            this.out = out;

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
//        socket.close();

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
