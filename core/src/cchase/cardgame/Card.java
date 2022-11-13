package cchase.cardgame;

import java.util.Random;

/**
 * Title: Card
 *
 * Card is a class that represents the variables within a card. Currently, only contains three variables.
 */
public class Card
{
    private String name;
    private int attack = -1;
    private int health = -1;
    Random rand = new Random();

    /**
     * Constructor.
     * Name becomes card with a random number at the end.
     * Attack is a random number between 0 - 4.
     * Health is a random number between 1 - 4.
     */
    public Card()
    {
        //Default constructor. A name of "Card" is set, and the attack and health are random numbers between 0-4
        name = "Card" + rand.nextInt(10);
        if (rand.nextInt(10) == 8)
        {
            name = "Bizwop";
        }
        attack = rand.nextInt(5);
        health = rand.nextInt(4) + 1;
    }

    /**
     * Parameterized constructor.
     * @param n is the name of the card.
     * @param a is the attack of the card.
     * @param h is the health of the card.
     */
    public Card(String n, int a, int h)
    {
        //Constructor with arguments. name, attack, and health are set to the values passed through.
        name = n;
        attack = a;
        health = h;
    }

    /**
     * Constructor that accepts a card. There's definitely a better way to do this.
     * This is really for classes that extend this class
     * @param card is a Card, and the name, attack, and health get passed down.
     */
    public Card(Card card)
    {
        name = card.getName();
        attack = card.getAttack();
        health = card.getHealth();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAttack()
    {
        return attack;
    }

    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public String toString()
    {
        return "Name: " + name + ", Attack: " + attack + ", Health: " + health;
    }
}
