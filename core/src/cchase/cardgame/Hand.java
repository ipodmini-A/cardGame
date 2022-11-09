package cchase.cardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class Hand implements InputProcessor
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
        SpriteBatch cardBatch;
        ShapeRenderer shapeRenderer;
        BitmapFont font;
        boolean cardSelected = false;

        /**
         * Constructor. Converts a Card to a CardVisable
         * @param c accepts a Card
         */
        public CardVisible(Card c)
        {
            super(c);
            cardBatch = new SpriteBatch();
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
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0,1,0,0);
                shapeRenderer.rect(cardLocationX, cardLocationY, cardWidth, cardHeight);
                shapeRenderer.end();
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

    /*
    Below is code for user input. Only touchDown is in use at the moment.
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Checks to see if the card was clicked on, and will change the cards color to indicate that it was selected.
     * TODO: Add input for the right click. it should deselect the card.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (int i = 0; i < currentHand.size(); i++)
        {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !currentHand.get(i).cardSelected
                    && board.playerTurn == true)
            {
                int mouseX = Gdx.input.getX();
                int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                if ((mouseX > currentHand.get(i).cardLocationX &&
                        mouseX < cardWidth + currentHand.get(i).cardLocationX)
                        && (mouseY > currentHand.get(i).cardLocationY
                        && mouseY < cardHeight + currentHand.get(i).cardLocationY))
                {
                    currentHand.get(i).cardSelected = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
