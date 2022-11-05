package cchase.cardgame;

public class Player
{
    String name;
    int health = -1;
    Deck deck;
    Hand hand;
    DiscardPile discardPile;

    public Player()
    {
        name = "BLANK";
        health = 10;
        deck = new Deck();
        hand = new Hand();
        discardPile = new DiscardPile();
    }
    /**
     * Draws a card from the deck and places it in the hand.
     */
    public void draw()
    {
        hand.push(deck.pop());
    }

    public void remove(int c)
    {
        Card cardToBeRemoved = hand.getData(c);
        Hand stackA = new Hand();
        Hand stackB = new Hand();

        while (!hand.isEmpty())
        {
            Card cardPopped = hand.pop();
            if (!cardPopped.equals(cardToBeRemoved))
            {
                stackA.push(cardPopped);
            }
        }

        while (!stackA.isEmpty())
        {
            hand.push(stackA.pop());
        }
    }

    /**
     * Returns a card from hand
     */
    public Card placeCardFromHand()
    {
        return hand.pop();
    }

    public Card placeCard(int i)
    {
        Card cardToBeRemoved = hand.getData(i);
        remove(i);
        return cardToBeRemoved;
    }

}
