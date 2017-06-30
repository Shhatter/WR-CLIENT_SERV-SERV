package checkers.classes;

import java.util.ArrayList;


 class Board {


  public int whitePawnAmmount ;
  public int blackPawnAmmount ;
  //ArrayList<Pawn> pawnList = new ArrayList<>();

  public Pawn [][] pawnList;


  public Board(int whitePawnAmmount, int blackPawnAmmount, Pawn[][] pawnList) {
   this.whitePawnAmmount = whitePawnAmmount;
   this.blackPawnAmmount = blackPawnAmmount;
   this.pawnList = pawnList;
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
