package cchase.cardgame;

/**
 * Title: Hand.java
 *
 * This class serves as the players hand. Currently it is a stack, but after thinking about it,
 * this class would do better as a list. Refactor later.
 */
public class Hand extends ArrayStack<Card>
{

    //Card[] handCollection;
    static int handSize = 10;
    public Hand()
    {
        /**
         * The constructor. handSize is pushed and becomes the size of the stack.
         * handCollection is an array, and is supposed to represent the stack with an Array.(Honestly, this might
         * be redundant.... so uh... have fun future self.
         */
        super(handSize);
    }

    public void add(Card c)
    {
        /**
         * Pushes the passed through card into the hand stack.
         */
        push(c);
    }
}
