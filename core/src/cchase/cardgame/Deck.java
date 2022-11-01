package cchase.cardgame;

public class Deck extends ArrayStack<Card>
{
    static private int maximumCards = 10;

    public Deck()
    {
        super(maximumCards);

        for (int i = 0; i < maximumCards; i++)
        {
            push(new Card());
        }
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
