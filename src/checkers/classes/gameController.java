package checkers.classes;

import checkers.enums.MoveTransferOrder;
import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;
import checkers.enums.ServerStatus;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
    public BlockingQueue<MoveTransfer> blockingQueue = new LinkedBlockingQueue<MoveTransfer>();;



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


        serverStatus = ServerStatus.WAITING_FOR_WHITE_MOVE;
    }


    private void setupBoard()
    {

        currentPlayerMove = PawnColor.WHITE;
        board = new Board(12,12,new Pawn[8][8]);

        for (int i =0;i<board.getPawnList().length;i++)
        {
            for (int j =0;j<board.getPawnList()[i].length;j++)
            {
                board.getPawnList()[i][j] = new Pawn();
            }


        }


        if(gameSession.get(0).getPlayerSide()==PlayerSide.TOP)
        {

            board.getPawnList()[0][1].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[0][3].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[0][5].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[0][7].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);

            board.getPawnList()[1][0].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[1][2].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[1][4].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[1][6].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);

            board.getPawnList()[2][1].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[2][3].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[2][5].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[2][7].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);



//#####################


            board.getPawnList()[5][0].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[5][2].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[5][4].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[5][6].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);


            board.getPawnList()[6][1].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[6][3].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[6][5].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[6][7].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);

            board.getPawnList()[7][0].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[7][2].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[7][4].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[7][6].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);

        }
        else
        {

            board.getPawnList()[0][1].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[0][3].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[0][5].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[0][7].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);

            board.getPawnList()[1][0].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[1][2].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[1][4].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[1][6].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);

            board.getPawnList()[2][1].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[2][3].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[2][5].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);
            board.getPawnList()[2][7].setStuff(gameSession.get(1).getPawnColor(),gameSession.get(1).getPlayerSide(),true);


            //#####################
            board.getPawnList()[5][0].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[5][2].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[5][4].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[5][6].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);


            board.getPawnList()[6][1].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[6][3].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[6][5].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[6][7].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);

            board.getPawnList()[7][0].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[7][2].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[7][4].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);
            board.getPawnList()[7][6].setStuff(gameSession.get(0).getPawnColor(),gameSession.get(0).getPlayerSide(),true);

        }



        for (int i =0;i<board.getPawnList().length;i++)
        {
            for (int j =0;j<board.getPawnList()[i].length;j++)
            {
                System.out.print(board.getPawnList()[i][j].getPawnColor()+" ");
            }
                System.out.println();

        }

        
    }



    private void checkAllMoves()
    {

        for (int i =0;i<board.getPawnList().length;i++)
        {
            for (int j =0;j<board.getPawnList()[i].length;j++)
            {

//            board.pawnMoveOptions.addAll();

                if (board.getPawnList()[i][j].getPawnColor()!=PawnColor.NONE) {
                    possibleMovesFOrPawnm(board.getPawnList()[i][j],board,i,j,-999,-999,0,false);
                }
            }
        }
    }

    private void possibleMovesFOrPawnm(Pawn pawn, Board board, int tempN, int tempM, int tempBanN, int tempBanM, int cCombo, boolean killmove)
    {
        ArrayList<PawnMoveOption> tempMoveOption = new ArrayList<PawnMoveOption>();
        // czy pionek jest damką ?

        if(pawn.isPawnKing())
        {
//            LEFT UP CORNER -1 -1


            if (tempN -1>= 0 && tempN -1 <8 && tempM -1 >=0 && tempM -1 <8 )
            {
                if (this.board.getPawnList()[tempN -1][tempM -1].getPawnColor() == PawnColor.NONE && this.board.getPawnList()[tempN -1][tempBanM -1].isUsedField() == false)
                {
                    tempMoveOption.add(new PawnMoveOption(tempN,tempM,tempN -1 ,tempM -1 ,false,false,0));
                    possibleMovesFOrPawnm(pawn, new Board(board), --tempN,--tempBanM,-999,-999,0,false);
                }
                else if(this.board.getPawnList()[tempN -1][tempM -1].getPawnColor() == pawn.getPawnColor() && this.board.getPawnList()[tempN -1][tempBanM -1].isUsedField() == true)
                {

                }


            }






//            LEFT DOWN CORNER +1 -1
            while (++tempN >= 0 && ++tempN<8 && --tempM>=0 && --tempM<8 && pawn.getPawnColor() == PawnColor.NONE && pawn.isUsedField() == false)
            {

            }

//            RIGHT UP CORNER -1,+1
            while (--tempN >= 0 && --tempN<8 && ++tempM>=0 && ++tempM<8 && pawn.getPawnColor() == PawnColor.NONE && pawn.isUsedField() == false)
            {

            }

//            RIGHT DOWN CORNER +1 +1
            while (++tempN >= 0 && ++tempN<8 && ++tempM>=0 && ++tempM<8 && pawn.getPawnColor() == PawnColor.NONE && pawn.isUsedField() == false)
            {

            }

        }
        else  // jeżeli pionek jest zwykłym pionkiem zobaczmy w którym kierunku musi się poruszać
        {

        }










/*



        if(pawn.isPawnKing())
        {



        }
        else if(pawn.getPlayerSide()==PlayerSide.BOTTOM)
        {

        }

        else
        {

        }









//            LEFT UP CORNER

        if(--tempN >= 0 && --tempN<8 && --tempM>=0 && --tempM<8)
        {
            if(board.getPawnList()[--tempN][--tempM].getPawnColor()== PawnColor.NONE)
            {
                tempMoveOption.add(new PawnMoveOption(tempN,tempM,--tempN,--tempM,false,));

            }
            else if ()
            {

            }
        }

//            LEFT DOWN CORNER



//            RIGHT UP CORNER



//            RIGHT DOWN CORNER
*/





//        return tempMoveOption;
    }

    private void countMoveComboPossibilites(ArrayList<PawnMoveOption> tempMoveOption)
    {

//            LEFT UP CORNER
//            LEFT DOWN CORNER
//            RIGHT UP CORNER
//            RIGHT DOWN CORNER
    }

}
