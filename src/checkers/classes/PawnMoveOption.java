package checkers.classes;

/**
 * Created by Praca on 2017-06-18.
 */
public class PawnMoveOption implements Cloneable{

 public static int longestRun;
 public int startNPosition;
 public int startingMPosition;

 public int endNPosition;
 public int endMPosition;

 boolean killMovee;
 boolean newKing;
 ComboMeter comboMeter = new ComboMeter();

    public PawnMoveOption(int startNPosition, int startingMPosition, int endNPosition, int endMPosition, boolean killMovee, boolean newKing, int comboMeter) {
        this.startNPosition = startNPosition;
        this.startingMPosition = startingMPosition;
        this.endNPosition = endNPosition;
        this.endMPosition = endMPosition;
        this.killMovee = killMovee;
        this.newKing = newKing;
        this.comboMeter.setcCCombo(comboMeter);
    }

    private class ComboMeter
 {

     int cCCombo;

     public int getcCCombo() {
         return cCCombo;
     }

     public void setcCCombo(int cCCombo) {
         this.cCCombo = cCCombo;
     }


 }




}
