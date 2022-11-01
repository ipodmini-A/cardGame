package cchase.cardgame;

import java.util.LinkedList;

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
         * Pushes the passed through card into the stack.
         */
        push(c);
    }
}
