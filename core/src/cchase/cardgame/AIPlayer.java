package cchase.cardgame;

import java.util.Random;

public class AIPlayer extends Player
{
    Random rand = new Random();
    Board boardInPlay;
    boolean aiTurn = false;
    public AIPlayer()
    {
        super();
    }

    public void AIUpdate(Board b)
    {
        boardInPlay = b;
    }

    public void aiPlay()
    {
        if (!boardInPlay.playerTurn)
        {
            aiTurn = true;
            int tempIndex = rand.nextInt(4, 8);
            draw();
            boardInPlay.zone[tempIndex].activeCard = placeCardFromHand();
            boardInPlay.zone[tempIndex].cardPlaced = true; // rand.nextInt(3, 8)
            boardInPlay.playerTurn = true;
            aiTurn = false;
        }
    }

}
