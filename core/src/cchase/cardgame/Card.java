package cchase.cardgame;

/**
 *
 */

public class Card
{
    private String name;
    private int attack = -1;
    private int health = -1;

    public Card()
    {
        name = "NULL";
        attack = 0;
        health = 0;
    }

    public Card(String n, int a, int h)
    {
        name = n;
        attack = a;
        health = h;
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
