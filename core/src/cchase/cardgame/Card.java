package cchase.cardgame;

import java.util.Random;

/**
 *
 */

public class Card
{
    private String name;
    private int attack = -1;
    private int health = -1;
    Random rand = new Random();

    public Card()
    {
        //Default constructor. A name of "Card" is set, and the attack and health are random numbers between 0-4
        name = "Card";
        attack = rand.nextInt(5);
        health = rand.nextInt(4) + 1;
    }

    public Card(String n, int a, int h)
    {
        //Constructor with arguments. name, attack, and health are set to the values passed through.
        name = n;
        attack = a;
        health = h;
    }

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
