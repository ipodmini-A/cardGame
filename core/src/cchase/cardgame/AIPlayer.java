package cchase.cardgame;

import java.util.Random;

public class AIPlayer extends Player
{
    private final Random rand = new Random();
    private Board boardInPlay;
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
            if (boardInPlay.zone[tempIndex].isCardPlaced())
            {
                for (int i = 4; i < boardInPlay.zone.length; i++)
                {
                    if (!boardInPlay.zone[i].isCardPlaced())
                    {
                        boardInPlay.zone[i].setActiveCard(placeCardFromHand());
                        boardInPlay.zone[i].setCardPlaced(true); // rand.nextInt(3, 8)
                        break;
                    }
                }
            }else
            {
                boardInPlay.zone[tempIndex].setActiveCard(placeCardFromHand());
                boardInPlay.zone[tempIndex].setCardPlaced(true); // rand.nextInt(3, 8)
            }

            for (int i = 0; i < 4; i++)
            {
                try
                {
                    if (boardInPlay.zone[i].getActiveCard().getHealth() > lowestHealth) {
                        lowestHealth = boardInPlay.zone[i].getActiveCard().getHealth();
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
                    if (boardInPlay.zone[i].getActiveCard().getAttack() > maxAttack)
                    {
                        maxAttack = boardInPlay.zone[i].getActiveCard().getAttack();
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
                boardInPlay.zone[zoneWithLowestHealth].getActiveCard().setHealth(boardInPlay.zone[zoneWithLowestHealth].getActiveCard().getHealth()
                        - boardInPlay.zone[zoneWithHighestAttack].getActiveCard().getAttack());
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
                if (boardInPlay.zone[i].isCardPlaced())
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
