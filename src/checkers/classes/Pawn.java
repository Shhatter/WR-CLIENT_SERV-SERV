package checkers.classes;


import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;

import java.util.ArrayList;

public class Pawn {

   private  PawnColor pawnColor = PawnColor.NONE;
   private  PlayerSide playerSide;
   private  boolean pawnKing = false; // czy pionek jest damką
   private  boolean usedField = false;
//   private  ArrayList <PawnMoveOption> moveOption;


    public Pawn(){}

    public Pawn(PawnColor pawnColor, PlayerSide playerSide, boolean pawnKing, boolean usedField) {
        this.pawnColor = pawnColor;
        this.playerSide = playerSide;
        this.pawnKing = pawnKing;
        this.usedField = usedField;
    }

    public void setStuff(PawnColor pawnColor, PlayerSide playerSide, boolean usedField)
 {
     this.pawnColor = pawnColor;
     this.playerSide = playerSide;
     this.usedField = usedField;

 }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }

    public PlayerSide getPlayerSide() {
        return playerSide;
    }

    public void setPlayerSide(PlayerSide playerSide) {
        this.playerSide = playerSide;
    }

    public boolean isPawnKing() {
        return pawnKing;
    }

    public void setPawnKing(boolean pawnKing) {
        this.pawnKing = pawnKing;
    }

    public boolean isUsedField() {
        return usedField;
    }

    public void setUsedField(boolean usedField) {
        this.usedField = usedField;
    }

    /*
    public ArrayList<PawnMoveOption> getMoveOption() {
        return moveOption;
    }

    public void setMoveOption(ArrayList<PawnMoveOption> moveOption) {
        this.moveOption = moveOption;
    }*/


/*
    Lista dostępnych ruchów dla danego pionka do dodania - lista ? jakaś inna kolekcja ?
    */

}
