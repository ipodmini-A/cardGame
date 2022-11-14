package cchase.cardgame;

/**
 * Title: Deck.java
 * This class serves as the Deck to the player.
 */
public class Deck extends ArrayStack<Card>
{
    static private int maximumCards = 20;

    public Deck()
    {
        //Constructor. The super establishes how many cards are allowed in a deck before a error is thrown.
        //Without any paramaters, a new card is created and passed into the Deck.
        super(maximumCards);

        for (int i = 0; i < maximumCards; i++)
        {
            push(new Card());
        }
    }

    public Card draw()
    {
        //This will pop a card from the stack and return it as a Card.
        if (isEmpty())
        {
            throw new StackEmptyException();
        }
        return this.pop();
    }
}
