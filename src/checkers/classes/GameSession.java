package checkers.classes;

import checkers.enums.PawnColor;
import checkers.enums.PlayerSide;

/**
 * Created by Praca on 2017-06-30.
 */
public class GameSession

{


    public long threadID;
    public int connectionID;
    public PawnColor pawnColor;
    public PlayerSide playerSide;
    public boolean allowedToMove = false; // Czy aby potrzebne ?

    public GameSession(long threadID, int connectionID)
    {
        this.threadID = threadID;
        this.connectionID = connectionID;
    }


    public long getThreadID()
    {
        return threadID;
    }

    public void setThreadID(long threadID)
    {
        this.threadID = threadID;
    }

    public int getConnectionID()
    {
        return connectionID;
    }

    public void setConnectionID(int connectionID)
    {
        this.connectionID = connectionID;
    }

    public PawnColor getPawnColor()
    {
        return pawnColor;
    }

    public void setPawnColor(PawnColor pawnColor)
    {
        this.pawnColor = pawnColor;
    }

    public PlayerSide getPlayerSide()
    {
        return playerSide;
    }

    public void setPlayerSide(PlayerSide playerSide)
    {
        this.playerSide = playerSide;
    }


    public boolean isAllowedToMove()
    {
        return allowedToMove;
    }

    public void setAllowedToMove(boolean allowedToMove)
    {
        this.allowedToMove = allowedToMove;
    }
}
