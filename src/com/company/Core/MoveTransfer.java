package com.company.Core;

import com.company.Enums.PawnColor;

import java.io.Serializable;

/**
 * Created by Praca on 2017-06-18.
 */
public class MoveTransfer implements Serializable
{

    int nStart,mStart,nDestination,mDestination;
    PawnColor color;
    String ownerID;
    String order;
    boolean rightToMove;

    public MoveTransfer(int nStart, int mStart,int nDestination,int mDestination, PawnColor color, String ownerID,String order,boolean rightToMove)

    {
        this.nDestination = nDestination;
        this.mDestination = mDestination;
        this.nStart = nStart;
        this.mStart = mStart;
        this.color = color;
        this.ownerID = ownerID;
        this.order = order;
        this.rightToMove = rightToMove;
    }

    public MoveTransfer() //9
    {
        this.nDestination = 0;
        this.mDestination = 0;
        this.nStart = 0;
        this.mStart = 0;
        this.color = PawnColor.NONE;
        this.ownerID = "NONE";
        this.order = "IDLE";
        this.rightToMove = true;
    }

    public void setAllData(int n, int m, PawnColor color, String ownerID,String order,boolean systemEnabled)
    {
        this.nStart = n;
        this.mStart = m;
        this.color = color;
        this.ownerID = ownerID;
        this.order = order;
        this.rightToMove = systemEnabled;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isRightToMove() {
        return rightToMove;
    }

    public void setRightToMove(boolean rightToMove) {
        this.rightToMove = rightToMove;
    }


}
