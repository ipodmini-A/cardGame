package cchase.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: Hand.java
 *
 * This class serves as the players hand. After refactoring, this class now contains a List that will hold the cards
 * This should be a easier solution to allow the player to add cards, as well as place cards from their hand.
 */

public class Hand
{
    /**
     * Title: CardVisible
     *
     * This class serves as a subclass under Hand, and allows a Card to be rendered on screen.
     */
    public class CardVisible extends Card
    {
        float cardLocationX;
        float cardLocationY;
        Sprite cardTemplateSprite;
        SpriteBatch cardBatch;
        SpriteBatch fontBatch;
        ShapeRenderer shapeRenderer;
        BitmapFont font;
        Texture texture;

        boolean cardSelected = false;

        /**
         * Constructor. Converts a Card to a CardVisable
         * @param c accepts a Card
         */
        public CardVisible(Card c)
        {
            super(c);
            cardBatch = new SpriteBatch();
            fontBatch = new SpriteBatch();
            texture = new Texture("CardTemplate.png");
            cardTemplateSprite = new Sprite(texture);
            shapeRenderer = new ShapeRenderer();
            font = new BitmapFont();
        }

        /**
         * cardRender serves as a way to Render the card on screen. Currently, has functionality for when the card is
         * selected.
         */
        public void cardRender()
        {
            if (cardSelected == true)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0,1,1,0);
                shapeRenderer.rect(cardLocationX, cardLocationY, cardWidth, cardHeight);
                shapeRenderer.end();
            }
            if (cardSelected == false)
            {
                cardTemplateSprite.setSize(cardWidth,cardHeight);
                cardTemplateSprite.setPosition(cardLocationX,cardLocationY);
                cardBatch.begin();
                cardTemplateSprite.draw(cardBatch);
                cardBatch.end();
            }
            cardBatch.begin();
            font.draw(cardBatch, getName(), cardLocationX,cardLocationY + 35);
            font.draw(cardBatch, "Health: " + getHealth(), cardLocationX,cardLocationY + 25);
            font.draw(cardBatch, "Attack: " + getAttack(), cardLocationX,cardLocationY + 15);
            cardBatch.end();
        }
    }
    List<CardVisible> currentHand;
    float cardSize = 30f;
    float cardHeight = 3.5f * cardSize;
    float cardWidth = 2.5f * cardSize;
    float cardPlacementScale = 80f;
    private float startX = 300;
    private boolean cardAlreadySelected = false;
    Board board;

    /**
     * The constructor. Currently, just creates a List that accepts CardVisible
     */
    public Hand()
    {
        currentHand = new ArrayList<CardVisible>();
    }

    /**
     * Pushes the passed through card into the hand stack.
     */
    public void add(Card c)
    {
        currentHand.add(new CardVisible(c));
    }
    public void handCardRender(Board b)
    {
        for (int i = 0; i < currentHand.size(); i++)
        {
            currentHand.get(i).cardLocationX = cardPlacementScale + i * cardPlacementScale;
            currentHand.get(i).cardRender();
        }

        board = b;
    }

    /**
     * drawCards is currently not in use, and has no code to execute.
     */
    public void drawCards()
    {

    }

    public float getStartX()
    {
        return startX;
    }

    public void setStartX(float startX)
    {
        this.startX = startX;
    }

    public boolean isCardAlreadySelected() {
        return cardAlreadySelected;
    }

    public void setCardAlreadySelected(boolean cardAlreadySelected) {
        this.cardAlreadySelected = cardAlreadySelected;
    }
}
