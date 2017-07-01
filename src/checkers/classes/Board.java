package checkers.classes;

import java.util.ArrayList;


 class Board {


  public int whitePawnAmmount ;
  public int blackPawnAmmount ;
  //ArrayList<Pawn> pawnList = new ArrayList<>();

  public Pawn [][] pawnList;
  public ArrayList<PawnMoveOption> pawnMoveOptions = new ArrayList<PawnMoveOption>();


  public Board(int whitePawnAmmount, int blackPawnAmmount, Pawn[][] pawnList) {
   this.whitePawnAmmount = whitePawnAmmount;
   this.blackPawnAmmount = blackPawnAmmount;
   this.pawnList = pawnList;
  }

  public Board(Board board) {
   this.whitePawnAmmount = board.getWhitePawnAmmount();
   this.blackPawnAmmount = board.getBlackPawnAmmount();
   this.pawnList = new Pawn[8][8];
   for (int i = 0 ; i< pawnList.length;i++)
   {
    for (int j = 0;j<pawnList[j].length;j++)
    {
       pawnList[i][j] = new Pawn (board.getPawnList()[i][j].getPawnColor(),board.getPawnList()[i][j].getPlayerSide(),board.getPawnList()[i][j].isPawnKing(),board.getPawnList()[i][j].isUsedField());
    }

   }

//   this.pawnMoveOptions = pawnMoveOptions;
  }

  public int getWhitePawnAmmount() {
   return whitePawnAmmount;
  }

  public void setWhitePawnAmmount(int whitePawnAmmount) {
   this.whitePawnAmmount = whitePawnAmmount;
  }

  public int getBlackPawnAmmount() {
   return blackPawnAmmount;
  }

  public void setBlackPawnAmmount(int blackPawnAmmount) {
   this.blackPawnAmmount = blackPawnAmmount;
  }

  public Pawn[][] getPawnList() {
   return pawnList;
  }

  public void setPawnList(Pawn[][] pawnList) {
   this.pawnList = pawnList;
  }




}
