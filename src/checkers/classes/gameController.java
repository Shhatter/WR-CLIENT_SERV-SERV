package checkers.classes;

import checkers.enums.MoveTransferOrder;
import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;
import checkers.enums.ServerStatus;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Praca on 2017-06-25.
 */
public class gameController extends Thread
{
    public  boolean portListening = true;
    public  int threadNumber = 0;
    private Thread thread;
    private String threadName;
    public ServerStatus serverStatus = ServerStatus.INACTIVE;


    public int port = 5555; // default used port for the test purpose
    public int loggedUsers;
    public Board board;
    public String currentPlayerMove;

    //public String [] commands = {"LOCK_LAYOUT","UNLOCK_LAYOUT","START_YOUR_MOVE","MOVE_ENDED","YOU_WIN","YOU_LOOSE"};
    ArrayList<NetworkConnection> networkConnections = new ArrayList<NetworkConnection>();
//    ArrayList<int[]> playerSessions = new ArrayList<int[]>(); innitial implementation to support multiple sessions
    public int [] gameSession = new int[]{-9999,-9999};

public gameController()
{
    super.setDaemon(true);
}

public void start()
{
    serverStatus = ServerStatus.LOOKINGFORPLAYERS;
    super.start();
}




    public void sendDataToPlayers(ServerStatus serverStatusOrder)
    {
        System.out.println("Prepare data to send to the clients");
        switch (serverStatusOrder)
        {
            case FILLTHEBOARD:
            {
                for (int i =0;i<gameSession.length;i++) {
                    networkConnections.get(gameSession[i]).networkCommProtocolThread.sendData(MoveTransferOrder.FILL_BOARD,
                            networkConnections.get(gameSession[i]).networkCommProtocolThread.playerSide,
                            networkConnections.get(gameSession[i]).networkCommProtocolThread.pawnColor,
                            networkConnections.get(gameSession[i]).networkCommProtocolThread.allowedToMove);
                }
                break;
            }



            default:
                System.out.println("sendDataToPlayers cannot resolve this order");

        }

    }

    public void receivePlayersData()
    {

    }

    public void gameInProgress()
    {

    }


    public void endGame() {
    }


    public void run()
    {
        System.out.println("***************************************");
        System.out.println("Game Controller has started it's work !");
        System.out.println("***************************************");
        while (true)
        {
            if (serverStatus == ServerStatus.LOOKINGFORPLAYERS) {
                System.out.println("Looking for available players");
                checkFOrAvailablePlayers();
            }
            if (serverStatus == ServerStatus.FILLTHEBOARD)
            {
                System.out.println("Filling up the boards for players");
                fillTheBoardForPlayers();

            }


        }

    }

    public void checkFOrAvailablePlayers()
    {
        int playersAmount = 0;


        for (int i = 0; i<networkConnections.size();i++)
        {
            if (networkConnections.get(i).networkCommProtocolThread.currentThreadID!=0 && networkConnections.get(i).networkCommProtocolThread.activeSession == false)
            {
                gameSession[playersAmount] = i; // identification of networkConnection that participates in game session.
                    playersAmount++;
            }
            if(playersAmount == 2)
            {
                System.out.println("Found 2 players to play the game !");
                System.out.println("ThreadsID are : " +networkConnections.get(gameSession[0]).networkCommProtocolThread.currentThreadID+ " and " +networkConnections.get(gameSession[1]).networkCommProtocolThread.currentThreadID);
                serverStatus = ServerStatus.FILLTHEBOARD;
                break;
            }



        }

        if (playersAmount ==0)
        {
            for (int i = 0;i<gameSession.length;i++)
            {
                gameSession[i] = -9999;

            }
            System.out.println("Not enough players to play the game ");
        }


    }


    public void fillTheBoardForPlayers()
    {
        System.out.println("Prepare to send initial board fill data to the clients !");
        //choose the side for players
        Random generator = new Random();
        if(generator.nextBoolean()==true)
        {
            networkConnections.get(gameSession[0]).networkCommProtocolThread.playerSide = PlayerSide.BOTTOM;
            networkConnections.get(gameSession[1]).networkCommProtocolThread.playerSide = PlayerSide.TOP;
        }
        else
        {
            networkConnections.get(gameSession[1]).networkCommProtocolThread.playerSide = PlayerSide.BOTTOM;
            networkConnections.get(gameSession[0]).networkCommProtocolThread.playerSide = PlayerSide.TOP;

        }
        if(generator.nextBoolean()==true) // choose color of pawns
        {
            networkConnections.get(gameSession[0]).networkCommProtocolThread.pawnColor = PawnColor.BLACK;
            networkConnections.get(gameSession[1]).networkCommProtocolThread.pawnColor = PawnColor.WHITE;
            networkConnections.get(gameSession[1]).networkCommProtocolThread.allowedToMove = true;

        }
        else
        {
            networkConnections.get(gameSession[1]).networkCommProtocolThread.pawnColor = PawnColor.BLACK;
            networkConnections.get(gameSession[0]).networkCommProtocolThread.pawnColor = PawnColor.WHITE;
            networkConnections.get(gameSession[0]).networkCommProtocolThread.allowedToMove = true;

        }
        sendDataToPlayers(serverStatus);






        serverStatus = ServerStatus.WAITING_FOR_DATA_FROM_CLIENTS;
    }

}
