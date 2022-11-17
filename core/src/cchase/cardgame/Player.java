package cchase.cardgame;

public class Player
{
    private String name;
    private int health = -1;
    private Deck deck;
    Hand hand;
    DiscardPile discardPile;
    int zoneCount = 0;
    int zones = 4;

    public Player()
    {
        name = "BLANK";
        health = 1;
        deck = new Deck();
        hand = new Hand();
        discardPile = new DiscardPile();
    }
    /**
     * Draws a card from the deck and places it in the hand.
     */
    public void draw()
    {
        hand.add(deck.pop());
    }

    public void remove(int c)
    {
        hand.currentHand.remove(c);
    }

    /**
     * Returns a card from hand
     */
    public Card placeCardFromHand()
    {
        return hand.currentHand.remove(0);
    }

    public Card placeCardFromHand(int index)
    {
        return hand.currentHand.remove(index);
    }

    public Card placeCard(int i)
    {
        Card cardToBeRemoved = hand.currentHand.remove(i);
        remove(i);
        return cardToBeRemoved;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
