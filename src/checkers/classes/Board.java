package checkers.classes;

import checkers.enums.PawnColor;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


class Board extends Thread
{

    public int tempBanN;
    public int tempBanM;
    public int whitePawnAmount;
    public int blackPawnAmount;
    public int movedPawnN;
    public int movedPawnM;
    public int cCombo = 0;
    public boolean killMove;
    public static BlockingQueue<PawnMoveOption> blockingQueue = new LinkedBlockingQueue<PawnMoveOption>();


    //ArrayList<Pawn> pawnList = new ArrayList<>();

    public Pawn[][] pawnList;
    public ArrayList<PawnMoveOption> pawnMoveOptions = new ArrayList<PawnMoveOption>();


    public Board(int whitePawnAmount, int blackPawnAmount, Pawn[][] pawnList)
    {
        this.whitePawnAmount = whitePawnAmount;
        this.blackPawnAmount = blackPawnAmount;
        this.pawnList = pawnList;
    }

    public Board(Board board, int movedPawnN, int movedPawnM)
    {
        this.whitePawnAmount = board.getWhitePawnAmount();
        this.blackPawnAmount = board.getBlackPawnAmount();
        this.pawnList = new Pawn[8][8];
        this.movedPawnN = movedPawnN;
        this.movedPawnM = movedPawnM;
        this.cCombo = board.cCombo;
        this.tempBanN = board.tempBanN;
        this.tempBanM = board.tempBanM;
        this.killMove = board.killMove;
        //this.blockingQueue = board.getBlockingQueue();
        for (int i = 0; i < pawnList.length; i++)
        {
            for (int j = 0; j < pawnList[j].length; j++)
            {
                pawnList[i][j] = new Pawn(board.getPawnList()[i][j].getPawnColor(), board.getPawnList()[i][j].getPlayerSide(), board.getPawnList()[i][j].isPawnKing(), board.getPawnList()[i][j].isUsedField());
            }

        }
    }


    public void run()
    {
        this.possibleMove();

    }
    //private void possibleMoveForPawn(Pawn pawn, Board board, int tempN, int tempM, int tempBanN, int tempBanM, int cCombo, boolean killmove)

    private void possibleMove()
    {


        if (this.killMove == true)
        {
            if (pawnList[movedPawnN][movedPawnM].isPawnKing())
            {

                possibleMoveOfKing();

            }
            else
            {
                possibleMoveOfPawn();
            }


        }
        else
        {

        }

    }


    public void possibleMoveOfPawn()
    {

    }


    private void possibleMoveOfKing()
    {


        //            LEFT UP CORNER -1 -1
        if (movedPawnN - 1 >= 0 && movedPawnN - 1 < 8 && movedPawnM - 1 >= 0 && movedPawnM - 1 < 8)
        {


            if (this.getPawnList()[movedPawnN - 1][movedPawnM - 1].getPawnColor() != pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN - 1][movedPawnM - 1].getPawnColor() != PawnColor.NONE
                    && this.getPawnList()[movedPawnN - 1][movedPawnM - 1].isUsedField() == true)
            {

                // Looking for all free places to land behind enemy pawn  - start new Threads !

                for (int n = movedPawnN - 1; n >= 0 && n < 8; n--)
                {
                    for (int m = movedPawnM - 1; m >= 0 && m < 8; m--)
                    {
                        if (this.getPawnList()[n][m].getPawnColor() == PawnColor.NONE && this.getPawnList()[n][m].isUsedField() == false)
                        {
                            try
                            {
                                blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, n, m, true, false, 1));
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println(movedPawnN + " , " + movedPawnM + " -> " + n + " , " + m + " : KILL MOVE ADDED ");
                            Board temp = new Board(this, n, m);
                            this.getPawnList()[movedPawnN][movedPawnM].clearPawnPlace();
                            this.getPawnList()[movedPawnN - 1][movedPawnM - 1].clearPawnPlace();
                            temp.killMove = true;
                            temp.tempBanN = movedPawnN;
                            temp.tempBanM = movedPawnM;
                            temp.cCombo += 1;
                            temp.start();
                        }
                    }

                }

            }

            else if (this.getPawnList()[movedPawnN - 1][movedPawnM - 1].getPawnColor() == PawnColor.NONE && this.getPawnList()[movedPawnN - 1][movedPawnM - 1].isUsedField() == false)
            {

                try
                {
                    blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, movedPawnN - 1, movedPawnM - 1, false, false, 0));
                    System.out.println(movedPawnN + " , " + movedPawnM + " -> " + (movedPawnN - 1) + " , " + (movedPawnM - 1) + " : MOVE ADDED ");
                } catch (InterruptedException e)
                {
                    System.out.println("LEFT UP CORNER -1 -1");
                    e.printStackTrace();
                }

            }

            else if (this.getPawnList()[movedPawnN - 1][movedPawnM - 1].getPawnColor() == pawnList[movedPawnN][movedPawnM].getPawnColor() && this.getPawnList()[movedPawnN - 1][movedPawnM - 1].isUsedField() == true)
            {
                System.out.println(movedPawnN + " , " + movedPawnM + " " + "END OF MOVES ON THIS CORNER ");
            }

        }


        // LEFT DOWN CORNER +1 -1

        if (movedPawnN + 1 >= 0 && movedPawnN + 1 < 8 && movedPawnM - 1 >= 0 && movedPawnM - 1 < 8)
        {


            if (this.getPawnList()[movedPawnN + 1][movedPawnM - 1].getPawnColor() != pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN + 1][movedPawnM - 1].getPawnColor() != PawnColor.NONE
                    && this.getPawnList()[movedPawnN + 1][movedPawnM - 1].isUsedField() == true)
            {

                // Looking for all free places to land behind enemy pawn  - start new Threads !

                for (int n = movedPawnN + 1; n >= 0 && n < 8; n++)
                {
                    for (int m = movedPawnM - 1; m >= 0 && m < 8; m--)
                    {
                        if (this.getPawnList()[n][m].getPawnColor() == PawnColor.NONE && this.getPawnList()[n][m].isUsedField() == false)
                        {
                            try
                            {
                                blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, n, m, true, false, 1));
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println(movedPawnN + " , " + movedPawnM + " -> " + n + " , " + m + " : KILL MOVE ADDED ");
                            Board temp = new Board(this, n, m);
                            this.getPawnList()[movedPawnN][movedPawnM].clearPawnPlace();
                            this.getPawnList()[movedPawnN + 1][movedPawnM - 1].clearPawnPlace();
                            temp.killMove = true;
                            temp.tempBanN = movedPawnN;
                            temp.tempBanM = movedPawnM;
                            temp.cCombo += 1;
                            temp.start();
                        }
                    }

                }

            }

            else if (this.getPawnList()[movedPawnN + 1][movedPawnM - 1].getPawnColor() == PawnColor.NONE && this.getPawnList()[movedPawnN + 1][movedPawnM - 1].isUsedField() == false)
            {

                try
                {
                    blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, movedPawnN + 1, movedPawnM - 1, false, false, 0));
                    System.out.println(movedPawnN + " , " + movedPawnM + " -> " + (movedPawnN + 1) + " , " + (movedPawnM - 1) + " : MOVE ADDED ");
                } catch (InterruptedException e)
                {
                    System.out.println("LEFT DOWN CORNER -1 -1");
                    e.printStackTrace();
                }

            }

            else if (this.getPawnList()[movedPawnN + 1][movedPawnM - 1].getPawnColor() == pawnList[movedPawnN][movedPawnM].getPawnColor() && this.getPawnList()[movedPawnN + 1][movedPawnM - 1].isUsedField() == true)
            {
                System.out.println(movedPawnN + " , " + movedPawnM + " " + "END OF MOVES ON THIS CORNER ");
            }

        }

//            RIGHT UP CORNER -1,+1

        if (movedPawnN - 1 >= 0 && movedPawnN - 1 < 8 && movedPawnM + 1 >= 0 && movedPawnM + 1 < 8)
        {


            if (this.getPawnList()[movedPawnN - 1][movedPawnM + 1].getPawnColor() != pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN - 1][movedPawnM + 1].getPawnColor() != PawnColor.NONE
                    && this.getPawnList()[movedPawnN - 1][movedPawnM + 1].isUsedField() == true)
            {

                // Looking for all free places to land behind enemy pawn  - start new Threads !

                for (int n = movedPawnN - 1; n >= 0 && n < 8; n--)
                {
                    for (int m = movedPawnM + 1; m >= 0 && m < 8; m++)
                    {
                        if (this.getPawnList()[n][m].getPawnColor() == PawnColor.NONE && this.getPawnList()[n][m].isUsedField() == false)
                        {
                            try
                            {
                                blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, n, m, true, false, 1));
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println(movedPawnN + " , " + movedPawnM + " -> " + n + " , " + m + " : KILL MOVE ADDED ");
                            this.getPawnList()[movedPawnN][movedPawnM].clearPawnPlace();
                            this.getPawnList()[movedPawnN - 1][movedPawnM + 1].clearPawnPlace();

                            Board temp = new Board(this, n, m);
                            temp.killMove = true;
                            temp.tempBanN = movedPawnN;
                            temp.tempBanM = movedPawnM;
                            temp.cCombo += 1;
                            temp.start();
                        }
                    }

                }

            }

            else if (this.getPawnList()[movedPawnN - 1][movedPawnM + 1].getPawnColor() == PawnColor.NONE && this.getPawnList()[movedPawnN - 1][movedPawnM + 1].isUsedField() == false)
            {

                try
                {
                    blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, movedPawnN - 1, movedPawnM + 1, false, false, 0));
                    System.out.println(movedPawnN + " , " + movedPawnM + " -> " + (movedPawnN - 1) + " , " + (movedPawnM + 1) + " : MOVE ADDED ");
                } catch (InterruptedException e)
                {
                    System.out.println("RIGHT UP CORNER -1 +1");
                    e.printStackTrace();
                }

            }

            else if (this.getPawnList()[movedPawnN - 1][movedPawnM + 1].getPawnColor() == pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN - 1][movedPawnM + 1].isUsedField() == true)
            {
                System.out.println(movedPawnN + " , " + movedPawnM + " " + "END OF MOVES ON THIS CORNER ");
            }

        }

// RIGHT DOWN CORNER +1 +1
        if (movedPawnN + 1 >= 0 && movedPawnN + 1 < 8 && movedPawnM + 1 >= 0 && movedPawnM + 1 < 8)
        {


            if (this.getPawnList()[movedPawnN + 1][movedPawnM + 1].getPawnColor() != pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN + 1][movedPawnM + 1].getPawnColor() != PawnColor.NONE
                    && this.getPawnList()[movedPawnN + 1][movedPawnM + 1].isUsedField() == true)
            {

                // Looking for all free places to land behind enemy pawn  - start new Threads !

                for (int n = movedPawnN + 1; n >= 0 && n < 8; n++)
                {
                    for (int m = movedPawnM + 1; m >= 0 && m < 8; m++)
                    {
                        if (this.getPawnList()[n][m].getPawnColor() == PawnColor.NONE && this.getPawnList()[n][m].isUsedField() == false)
                        {
                            try
                            {
                                blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, n, m, true, false, 1));
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println(movedPawnN + " , " + movedPawnM + " -> " + n + " , " + m + " : KILL MOVE ADDED ");
                            this.getPawnList()[movedPawnN][movedPawnM].clearPawnPlace();
                            this.getPawnList()[movedPawnN + 1][movedPawnM + 1].clearPawnPlace();

                            Board temp = new Board(this, n, m);
                            temp.killMove = true;
                            temp.tempBanN = movedPawnN;
                            temp.tempBanM = movedPawnM;
                            temp.cCombo += 1;
                            temp.start();
                        }
                    }

                }

            }

            else if (this.getPawnList()[movedPawnN + 1][movedPawnM + 1].getPawnColor() == PawnColor.NONE &&
                    this.getPawnList()[movedPawnN + 1][movedPawnM + 1].isUsedField() == false)
            {

                try
                {
                    blockingQueue.put(new PawnMoveOption(movedPawnN, movedPawnM, movedPawnN + 1, movedPawnM + 1, false, false, 0));
                    System.out.println(movedPawnN + " , " + movedPawnM + " -> " + (movedPawnN + 1) + " , " + (movedPawnM + 1) + " : MOVE ADDED ");
                } catch (InterruptedException e)
                {
                    System.out.println("RIGHT DOWN CORNER +1 +1");
                    e.printStackTrace();
                }

            }

            else if (this.getPawnList()[movedPawnN + 1][movedPawnM + 1].getPawnColor() == pawnList[movedPawnN][movedPawnM].getPawnColor()
                    && this.getPawnList()[movedPawnN + 1][movedPawnM + 1].isUsedField() == true)
            {
                System.out.println(movedPawnN + " , " + movedPawnM + " " + "END OF MOVES ON THIS CORNER ");
            }

        }


    }


    public int getWhitePawnAmount()
    {
        return whitePawnAmount;
    }

    public void setWhitePawnAmount(int whitePawnAmount)
    {
        this.whitePawnAmount = whitePawnAmount;
    }

    public int getBlackPawnAmount()
    {
        return blackPawnAmount;
    }

    public void setBlackPawnAmount(int blackPawnAmount)
    {
        this.blackPawnAmount = blackPawnAmount;
    }

    public Pawn[][] getPawnList()
    {
        return pawnList;
    }

    public void setPawnList(Pawn[][] pawnList)
    {
        this.pawnList = pawnList;
    }

    public BlockingQueue<PawnMoveOption> getBlockingQueue()
    {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue<PawnMoveOption> blockingQueue)
    {
        this.blockingQueue = blockingQueue;
    }


}
