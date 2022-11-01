package cchase.cardgame;

public class Deck extends ArrayStack<Card>
{
    static private int maximumCards = 10;

    public Deck()
    {
        super(maximumCards);
    }

    public Card draw()
    {
        if (isEmpty())
        {
            throw new StackEmptyException();
        }
        return this.pop();
    }

}
