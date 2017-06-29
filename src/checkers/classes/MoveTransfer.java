package checkers.classes;

import checkers.enums.PawnColor;
import checkers.enums.MoveTransferOrder;
import checkers.enums.PlayerSide;

import java.io.Serializable;

/**
 * Created by Praca on 2017-06-18.
 */
public class MoveTransfer implements Serializable, Cloneable
{

    int nStart,mStart,nDestination,mDestination;
    PawnColor color;
    String ownerID;
    MoveTransferOrder order;
    boolean rightToMove;
    PlayerSide playerSide;


    public MoveTransfer(int nStart, int mStart,int nDestination,int mDestination, PawnColor color, String ownerID,MoveTransferOrder order,boolean rightToMove,PlayerSide playerSide)

    {
        this.nDestination = nDestination;
        this.mDestination = mDestination;
        this.nStart = nStart;
        this.mStart = mStart;
        this.color = color;
        this.ownerID = ownerID;
        this.order = order;
        this.rightToMove = rightToMove;
        this.playerSide = playerSide;
    }

    public MoveTransfer(MoveTransfer moveT)
    {

        this.nDestination = moveT.getnDestination();
        this.mDestination = moveT.getmDestination();
        this.nStart = moveT.getnStart();
        this.mStart = moveT.getmStart();
        this.color = moveT.getColor();
        this.ownerID = moveT.getOwnerID();
        this.order = moveT.getOrder();
        this.rightToMove = moveT.rightToMove;
        this.playerSide = moveT.playerSide;

    }


    public MoveTransfer() //9
    {
        this.nDestination = 0;
        this.mDestination = 0;
        this.nStart = 0;
        this.mStart = 0;
        this.color = PawnColor.NONE;
        this.ownerID = "NONE";
        this.order = MoveTransferOrder.NO_ORDER;
        this.rightToMove = false;
        playerSide = PlayerSide.NOT_DECITED;
    }


    public void showAllData()
    {
        System.out.println("***DATA FROM OUTPUTSTREAM***");
        System.out.println(nDestination);
        System.out.println(mDestination);
        System.out.println(nStart);
        System.out.println(mStart);
        System.out.println(color);
        System.out.println(ownerID);
        System.out.println(order);
        System.out.println(rightToMove);
        System.out.println(playerSide);
        System.out.println("********************");

    }



    public int getnStart() {
        return nStart;
    }

    public void setnStart(int nStart) {
        this.nStart = nStart;
    }

    public int getmStart() {
        return mStart;
    }

    public void setmStart(int mStart) {
        this.mStart = mStart;
    }

    public int getnDestination() {
        return nDestination;
    }

    public void setnDestination(int nDestination) {
        this.nDestination = nDestination;
    }

    public int getmDestination() {
        return mDestination;
    }

    public void setmDestination(int mDestination) {
        this.mDestination = mDestination;
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public MoveTransferOrder getOrder() {
        return order;
    }

    public void setOrder(MoveTransferOrder order) {
        this.order = order;
    }

    public boolean isRightToMove() {
        return rightToMove;
    }

    public void setRightToMove(boolean rightToMove) {
        this.rightToMove = rightToMove;
    }


    public PlayerSide getPlayerSide()
    {
        return playerSide;
    }

    public void setPlayerSide(PlayerSide playerSide)
    {
        this.playerSide = playerSide;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof MoveTransfer)) return false;

        MoveTransfer that = (MoveTransfer) o;

        if (getnStart() != that.getnStart()) return false;
        if (getmStart() != that.getmStart()) return false;
        if (getnDestination() != that.getnDestination()) return false;
        if (getmDestination() != that.getmDestination()) return false;
        if (isRightToMove() != that.isRightToMove()) return false;
        if (getColor() != that.getColor()) return false;
        if (!getOwnerID().equals(that.getOwnerID())) return false;
        if (getOrder() != that.getOrder()) return false;
        return getPlayerSide() == that.getPlayerSide();
    }

    @Override
    public int hashCode()
    {
        int result = getnStart();
        result = 31 * result + getmStart();
        result = 31 * result + getnDestination();
        result = 31 * result + getmDestination();
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getOwnerID().hashCode();
        result = 31 * result + getOrder().hashCode();
        result = 31 * result + (isRightToMove() ? 1 : 0);
        result = 31 * result + getPlayerSide().hashCode();
        return result;
    }
}
