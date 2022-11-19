package cchase.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Title: Card
 *
 * Card is a class that represents the variables within a card. Currently, only contains three variables.
 * TODO: Rotating font doesn't seem like a easy task. How about... Create images for the font... oh boy
 * TODO: Learn about scene2d... Theres probably a easier way to work this
 * TODO: Table for the cards...
 */
public class Card
{
    private String name;
    private String region;
    private String descriptionOrEffect;
    private int attack = -1;
    private int health = -1;
    Sprite cardTemplateSprite;
    SpriteBatch batch;
    SpriteBatch fontBatch;
    BitmapFont font;
    Texture texture;
    Random rand = new Random();
    private float x;
    private float y;

    /**
     * Constructor.
     * Name becomes card with a random number at the end.
     * Attack is a random number between 0 - 4.
     * Health is a random number between 1 - 4.
     */
    public Card()
    {
        //Default constructor. A name of "Card" is set, and the attack and health are random numbers between 0-4
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        texture = new Texture("CardTemplate.png");
        cardTemplateSprite = new Sprite(texture);
        font = new BitmapFont();
        name = "Card" + rand.nextInt(10);
        if (rand.nextInt(10) == 8)
        {
            name = "Bizwop";
        }
        attack = rand.nextInt(5) + 1;
        health = rand.nextInt(5) + 1;
    }

    /**
     * Parameterized constructor.
     * @param n is the name of the card.
     * @param a is the attack of the card.
     * @param h is the health of the card.
     */
    public Card(String n, int a, int h)
    {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        texture = new Texture("CardTemplate.png");
        cardTemplateSprite = new Sprite(texture);
        font = new BitmapFont();

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
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        texture = new Texture("CardTemplate.png");
        cardTemplateSprite = new Sprite(texture);
        font = new BitmapFont();
        name = card.getName();
        attack = card.getAttack();
        health = card.getHealth();
    }

    public void cardRender(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        cardTemplateSprite.setSize(width,height);
        cardTemplateSprite.setPosition(x,y);
        batch.begin();
        cardTemplateSprite.draw(batch);
        batch.end();
        fontBatch.begin();
        font.draw(fontBatch, getName(), x + 15,y + 195);
        font.draw(fontBatch, getHealth() + "", x + 18,y + 73);
        font.draw(fontBatch, getAttack() + "", x + 85,y + 73);
        fontBatch.end();
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

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString()
    {
        return "Name: " + name + ", Attack: " + attack + ", Health: " + health;
    }
}
