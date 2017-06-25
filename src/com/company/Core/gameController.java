package com.company.Core;

import java.util.ArrayList;

/**
 * Created by Praca on 2017-06-25.
 */
public class gameController extends Thread
{
    public  boolean portListening = true;
    public  int threadNumber = 0;



    public String ip; // unused - localhost for now
    public int port = 5555; // default used port for the test purpose
    public int loggedUsers;
    public Board board;
    public String currentPlayerMove;
    public MoveTransfer moveTransfer;
    public String [] commands = {"LOCK_LAYOUT","UNLOCK_LAYOUT","START_YOUR_MOVE","MOVE_ENDED","YOU_WIN","YOU_LOOSE"};
    ArrayList<NetworkConnection> networkConnections = new ArrayList<NetworkConnection>();



    public void startGame()
    {


    }

    public void gameInProgress()
    {

    }


    public void endGame() {
    }


    public void run()
    {


    }
}
