package com.company.Core;

import com.company.Enums.PawnColor;

/**
 * Created by Praca on 2017-06-18.
 */
public class MoveTransfer
{
    int n,m;
    PawnColor color;
    String ownerID;

    public MoveTransfer(int n, int m, PawnColor color, String ownerID)
    {
        this.n = n;
        this.m = m;
        this.color = color;
        this.ownerID = ownerID;
    }

    public MoveTransfer()
    {
        n=0;
        n=m;
        color = PawnColor.NONE;
        ownerID = "Unknown";
    }

    public void setAllData(int n, int m, PawnColor color, String ownerID)
    {
        this.n = n;
        this.m = m;
        this.color = color;
        this.ownerID = ownerID;
    }

    public int getN()
    {
        return n;
    }

    public void setN(int n)
    {
        this.n = n;
    }

    public int getM()
    {
        return m;
    }

    public void setM(int m)
    {
        this.m = m;
    }

    public PawnColor getColor()
    {
        return color;
    }

    public void setColor(PawnColor color)
    {
        this.color = color;
    }

    public String getOwnerID()
    {
        return ownerID;
    }

    public void setOwnerID(String ownerID)
    {
        this.ownerID = ownerID;
    }
}
