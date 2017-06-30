package checkers.classes;


import checkers.enums.PawnColor;

import java.util.ArrayList;

public class Pawn {

   public  PawnColor pColor = PawnColor.NONE;
   public  boolean boardSide;
   public  String position;
   public  boolean pawnLocked;
   public  boolean pawnKing ; // czy pionek jest damką

   public  ArrayList <PawnMoveOption> moveOption;






    /*
    Lista dostępnych ruchów dla danego pionka do dodania - lista ? jakaś inna kolekcja ?
    */

}
