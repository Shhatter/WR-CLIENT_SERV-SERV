package checkers.classes;

import checkers.enums.MoveTransferOrder;
import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Praca on 2017-06-18.
 */
public class NetworkCommProtocolThread extends Thread{

    PawnColor pawnColor;
    PlayerSide playerSide;
    public boolean activeSession = false; // check if client is able to start new game
    public boolean allowedToMove = false;
    boolean newDataToSend = false;
    boolean newDataToReceive = false;
    public BlockingQueue <MoveTransfer> blockingQueue;


    public MoveTransfer moveTransfer= new MoveTransfer();
    public MoveTransfer tempMoveTranfer = new MoveTransfer();
    public Socket socket = null;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    long  currentThreadID;

    public NetworkCommProtocolThread()
    {
        this.setDaemon(true);
        this.setName("CONNECTION THREAD");
    }

    public void sendData(MoveTransferOrder sOrder)
    {
       // out.writeObject(data);
    }
    public void sendData(MoveTransferOrder fillBoard, PlayerSide playerSide, PawnColor pawnColor, boolean allowedToMove)
    {
        moveTransfer.setColor(pawnColor);
        moveTransfer.setRightToMove(allowedToMove);
        moveTransfer.setOrder(fillBoard);
        newDataToSend = true;

    }

@Override
    public void run()
    {

        currentThreadID = Thread.currentThread().getId();
        System.out.println("Connection Thread ID: "+currentThreadID + " is working");

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socket.setTcpNoDelay(true); //może się przyda - może nie.



        while (moveTransfer.getOrder()!=MoveTransferOrder.END_OF_GAME)
        {

            if(newDataToSend == true && newDataToReceive == false && currentThreadID!=0)
            {
                out.reset();
                out.writeObject(moveTransfer);


                out.flush();
                newDataToSend = false;
                newDataToReceive = true;
                System.out.println("Data from thread " +currentThreadID + " has been sent");


            }
            else if( newDataToSend == false && newDataToReceive == true)
            {
                System.out.println("Connection Thread ID: "+currentThreadID + " is waiting for data...");
                tempMoveTranfer =(MoveTransfer)  in.readObject();
                System.out.println("Connection Thread ID: "+currentThreadID + " has received something");
                tempMoveTranfer.showAllData();
                if(tempMoveTranfer.hashCode()==moveTransfer.hashCode())
                {
                    System.out.println("Data are the same !");
                    throw new IllegalAccessException("NOPE");
                }
                else
                {
                    moveTransfer = new MoveTransfer(tempMoveTranfer);
                    this.newDataToReceive = false;
//                    blockingQueue.put( new MoveTransfer(moveTransfer));


                }
            }


        Thread.sleep(100);

        }


        } catch (IOException e)
        {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally
        {

            System.out.println("Connection of "+currentThreadID + " has ended");

        }


    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
