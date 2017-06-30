package checkers.classes;

import checkers.enums.MoveTransferOrder;
import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;
import checkers.enums.ServerStatus;

import java.security.InvalidParameterException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Praca on 2017-06-25.
 */
public class gameController extends Thread
{
    public  boolean portListening = true;
    public  int threadNumber = 0;
    public ServerStatus serverStatus = ServerStatus.INACTIVE;
    public int port = 5555; // default used port for the test purpose

    public int whitePlayerID,blackPlayerID;
    public Board board;
    public PawnColor currentPlayerMove = PawnColor.NONE;
    ArrayList<NetworkConnection> networkConnections = new ArrayList<NetworkConnection>();


   // public GameSession  gameSession = new int[]{-9999,-9999};
    public ArrayList<GameSession>  gameSession= new ArrayList<GameSession>();

public gameController()
{
    super.setDaemon(true);
}

public void start()
{
    serverStatus = ServerStatus.LOOKINGFORPLAYERS;
    super.start();
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

                gameSession.add(new GameSession(networkConnections.get(i).networkCommProtocolThread.currentThreadID,networkConnections.get(i).connectionID));
                //gameSession[playersAmount] = i; // identification of networkConnection that participates in game session.
                    playersAmount++;
            }
            if(playersAmount == 2)
            {
                System.out.println("Found 2 players to play the game !");
                System.out.println("ThreadsID are : " +gameSession.get(0).getThreadID()+ " and " +gameSession.get(1).getThreadID());
                serverStatus = ServerStatus.FILLTHEBOARD;

                break;
            }



        }

        if (playersAmount ==0)
        {
            System.out.println("Not enough players to play the game ");
            throw new InvalidParameterException();
        }


    }


    public void sendDataToPlayers(ServerStatus serverStatusOrder)
    {
        System.out.println("Prepare data to send to the clients");
        switch (serverStatusOrder)
        {
            case FILLTHEBOARD:
            {
                for (int i =0;i<gameSession.size();i++) {
                    networkConnections.get(gameSession.get(i).getConnectionID()).networkCommProtocolThread.sendData(MoveTransferOrder.FILL_BOARD,
                            networkConnections.get(gameSession.get(i).getConnectionID()).networkCommProtocolThread.playerSide,
                            networkConnections.get(gameSession.get(i).getConnectionID()).networkCommProtocolThread.pawnColor,
                            networkConnections.get(gameSession.get(i).getConnectionID()).networkCommProtocolThread.allowedToMove);
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



    public void fillTheBoardForPlayers()
    {
        System.out.println("Prepare to send initial board fill data to the clients !");
        //choose the side for players
        Random generator = new Random();
        if(generator.nextBoolean()==true)
        {
            networkConnections.get(gameSession.get(0).getConnectionID()).networkCommProtocolThread.playerSide = PlayerSide.BOTTOM;
            gameSession.get(0).setPlayerSide(PlayerSide.BOTTOM);

            networkConnections.get(gameSession.get(1).getConnectionID()).networkCommProtocolThread.playerSide = PlayerSide.TOP;
            gameSession.get(1).setPlayerSide(PlayerSide.TOP);
        }
        else
        {
            networkConnections.get(gameSession.get(1).getConnectionID()).networkCommProtocolThread.playerSide = PlayerSide.BOTTOM;
            gameSession.get(1).setPlayerSide(PlayerSide.BOTTOM);

            networkConnections.get(gameSession.get(0).getConnectionID()).networkCommProtocolThread.playerSide = PlayerSide.TOP;
            gameSession.get(0).setPlayerSide(PlayerSide.TOP);
        }
        if(generator.nextBoolean()==true) // choose color of pawns
        {
            networkConnections.get(gameSession.get(0).getConnectionID()).networkCommProtocolThread.pawnColor = PawnColor.BLACK;
            gameSession.get(0).setPawnColor(PawnColor.BLACK);

            networkConnections.get(gameSession.get(1).getConnectionID()).networkCommProtocolThread.pawnColor = PawnColor.WHITE;
            networkConnections.get(gameSession.get(1).getConnectionID()).networkCommProtocolThread.allowedToMove = true;
            gameSession.get(1).setPawnColor(PawnColor.WHITE);
            gameSession.get(1).setAllowedToMove(true);

        }
        else
        {
            networkConnections.get(gameSession.get(1).getConnectionID()).networkCommProtocolThread.pawnColor = PawnColor.BLACK;
            gameSession.get(1).setPawnColor(PawnColor.BLACK);

            networkConnections.get(gameSession.get(0).getConnectionID()).networkCommProtocolThread.pawnColor = PawnColor.WHITE;
            networkConnections.get(gameSession.get(0).getConnectionID()).networkCommProtocolThread.allowedToMove = true;
            gameSession.get(0).setPawnColor(PawnColor.WHITE);
            gameSession.get(0).setAllowedToMove(true);
        }
        sendDataToPlayers(serverStatus);
        setupBoard();

        System.out.println("Initial board setup is done");
        checkAllMoves();
        System.out.println("########################");
        System.out.println("Now the game begins ");
        System.out.println("########################");
        System.out.println("Player move: WHITE");


        serverStatus = ServerStatus.WAITING_FOR_DATA_FROM_CLIENTS;
    }

    private void checkAllMoves() {
    }

    private void setupBoard() 
    {
        board = new Board(12,12,new Pawn[8][8]);


//
//        for (int m = 0 ; m< gameSession.length;m++)
//


        for (int i =0;i<board.getPawnList().length;i++)
        {
            for (int j =0;j<board.getPawnList()[i].length;j++)
            {
                board.getPawnList()[i][j] = new Pawn();
            }


        }

        // fill the place for top

        // fill the place for the bottom

        // fill the place for the empty




        
    }

}
