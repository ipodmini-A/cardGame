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
            int maxAttack = 0;
            int zoneWithHighestAttack = 0;
            int lowestHealth = 0;
            int zoneWithLowestHealth = 0;
            int tempIndex = rand.nextInt(4) + 4;
            draw();
            if (boardInPlay.zone[tempIndex].cardPlaced)
            {
                for (int i = 4; i < boardInPlay.zone.length; i++)
                {
                    if (!boardInPlay.zone[i].cardPlaced)
                    {
                        boardInPlay.zone[i].activeCard = placeCardFromHand();
                        boardInPlay.zone[i].cardPlaced = true; // rand.nextInt(3, 8)
                        break;
                    }
                }
            }else
            {
                boardInPlay.zone[tempIndex].activeCard = placeCardFromHand();
                boardInPlay.zone[tempIndex].cardPlaced = true; // rand.nextInt(3, 8)
            }

            for (int i = 0; i < 4; i++)
            {
                try
                {
                    if (boardInPlay.zone[i].activeCard.getHealth() > lowestHealth) {
                        lowestHealth = boardInPlay.zone[i].activeCard.getHealth();
                        zoneWithLowestHealth = i;
                    }
                }catch(Exception e)
                {
                    System.out.println("No cards in zone " + i);
                }
            }

            for (int i = 4; i < boardInPlay.zone.length; i++)
            {
                try
                {
                    if (boardInPlay.zone[i].activeCard.getAttack() > maxAttack)
                    {
                        maxAttack = boardInPlay.zone[i].activeCard.getAttack();
                        zoneWithHighestAttack = i;
                    }
                }catch(Exception e)
                {
                    System.out.println("No cards in zone " + i);

                }
            }

            winCheck();

            try
            {
                boardInPlay.zone[zoneWithLowestHealth].activeCard.setHealth(boardInPlay.zone[zoneWithLowestHealth].activeCard.getHealth()
                        - boardInPlay.zone[zoneWithHighestAttack].activeCard.getAttack());
            }catch (Exception e)
            {
                boardInPlay.setAiAttackForGame(true);
                System.out.println("Attack for game!");
            }



            boardInPlay.playerTurn = true;
            aiTurn = false;
        }
    }

    public void winCheck()
    {
        boolean cardFound = false;
        for (int i = 0; i < 4; i++)
        {
            try
            {
                if (boardInPlay.zone[i].cardPlaced)
                {
                    cardFound = true;
                }
            }catch(Exception e)
            {
                System.out.println("Player has a card in zone " + i);
            }
        }

        if (!cardFound)
        {
            boardInPlay.setAiAttackForGame(true);
            System.out.println("Attack for game!");
        }
    }

}
