package cchase.cardgame;
/**
 * Title: Board.java
 * This class makes me question if Computer Science was the right field for me.
 * Class will change as better solutions come up. Currently the class has 8 cards that are allowed on it.
 * The goal is to pull from your hand, and place the cards on the field.
 */

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    public class Zone implements InputProcessor
    {
        int zoneLocation;
        Card activeCard;
        boolean cardPlaced = false;
        float zoneX = 50.0f;
        float zoneY = 50.0f;

        /**
         * Constructor. If more than 4 zones are called, the zone will not be created
         * and a runtime exception will be thrown.
         */
        public Zone()
        {
            if (zonesCount < zones)
            {
                zoneX = zoneX + (150 * zonesCount);
                zoneLocation = zonesCount;
                zonesCount++;
            }else
            {
                RuntimeException zonesFull = new RuntimeException("Zones Full");
            }
        }

        /**
         * zoneRender will render the current zones and what is placed in the zones on the screen.
         */
        public void zoneRender()
        {
            if (!cardPlaced)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,1,1,0);
                shapeRenderer.rect(zoneX, zoneY, cardWidth, cardHeight);
                shapeRenderer.end();
            }
            if (cardPlaced)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,0,0,0);
                shapeRenderer.rect(zoneX, zoneY, cardWidth, cardHeight);
                shapeRenderer.end();
                fontBatch.begin();
                font.draw(fontBatch, activeCard.getName(), zoneX + 25,zoneY + 25);
                font.draw(fontBatch, "Health: " + activeCard.getHealth(), zoneX + 15,zoneY + 125);
                font.draw(fontBatch, "Attack: " + activeCard.getAttack(), zoneX + 15,zoneY + 105);
                fontBatch.end();
                if (activeCard.getHealth() == 0 && cardPlaced)
                {
                    cardPlaced = false;
                    player1.discardPile.push(activeCard);
                }
            }
        }

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
         * Checks and updates mouseX and mouseY with the current mouse location when called.
         * In its current state, when the left mouse button is clicked, it will place down a card in the
         * zone. If the right mouse button is pressed, the card is removed from the zone.
         * This uses the touchDown method from InputProcessor. If this grows, this will
         * have to be split into a different class eventually.
         *
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !cardPlaced)
            {
                mouseX = Gdx.input.getX();
                mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                if ((mouseX > zoneX && mouseX < cardWidth + zoneX) && (mouseY > zoneY && mouseY < cardHeight + zoneY))
                {
                    activeCard = player1.placeCardFromHand();
                    cardPlaced = true;
                    return true;
                }
                return false;
            }

            if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && cardPlaced)
            {
                mouseX = Gdx.input.getX();
                mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                if ((mouseX > zoneX && mouseX < cardWidth + zoneX) && (mouseY > zoneY && mouseY < cardHeight + zoneY))
                {
                    cardPlaced = false;
                    activeCard = null;
                    return true;
                }
                return false;
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

    public class DeckZone extends Zone
    {
        Deck activeDeck;
        float zoneX = 650.0f;
        float zoneY = 50.0f;

        public DeckZone(Player p)
        {
            zoneLocation = 5;
            activeDeck = p.deck;
        }

        public void zoneRender()
        {
            //mouseCheck();
            if (!activeDeck.isEmpty())
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,1,1,0);
                shapeRenderer.rect(zoneX, zoneY, cardWidth, cardHeight);
                shapeRenderer.end();
                fontBatch.begin();
                font.draw(fontBatch, activeDeck.getSize() + "", zoneX + 25,zoneY + 25);
                fontBatch.end();
            }
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            if (button == Input.Buttons.LEFT)
            {
                mouseX = Gdx.input.getX();
                mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                if ((mouseX > zoneX && mouseX < cardWidth + zoneX) && (mouseY > zoneY && mouseY < cardHeight + zoneY))
                {
                    player1.draw();
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    public class handZone extends Zone
    {
        List<Hand> activeHand;
        float zoneX = 50.0f;
        float zoneY = 50.0f;

        public handZone()
        {
            activeHand = new ArrayList<Hand>();
            activeHand = player1.hand;

        }
    }

    Player player1;
    Player player2;
    float cardSize = 40f;
    float cardWidth = 2.5f * cardSize;
    float cardHeight = 3.5f * cardSize;
    SpriteBatch batch;
    SpriteBatch fontBatch;
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    float mouseX = Gdx.input.getX();
    float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY());
    int zones = 4;
    int zonesCount = 0;
    Zone zone0;
    Zone zone1;
    Zone zone2;
    Zone zone3;
    DeckZone deckZone;
    InputMultiplexer inputMultiplexer;

    /**
     * Default constructor.
     * batch is created to allow shapes to be drawn.
     * fontBatch is created to allow for fonts. If shapes and fonts are passed through the same batch,
     * visual errors occur with the font. I also can't find a better way to solve this online, so I
     * am stuck with this solution.
     */
    public Board()
    {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        player1 = new Player();
        zone0 = new Zone();
        zone1 = new Zone();
        zone2 = new Zone();
        zone3 = new Zone();
        deckZone = new DeckZone(player1);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(deckZone);
        inputMultiplexer.addProcessor(zone0);
        inputMultiplexer.addProcessor(zone1);
        inputMultiplexer.addProcessor(zone2);
        inputMultiplexer.addProcessor(zone3);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public Board(Player p1)
    {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(1,1,1,0);
        shapeRenderer = new ShapeRenderer();
        player1 = p1;
        zone0 = new Zone();
        zone1 = new Zone();
        zone2 = new Zone();
        zone3 = new Zone();
        deckZone = new DeckZone(p1);
    }

    public void boardPlace()
    {
        zone0.zoneRender();
        zone1.zoneRender();
        zone2.zoneRender();
        zone3.zoneRender();
        deckZone.zoneRender();
        fontBatch.begin();
        font.draw(fontBatch, mouseX + "", 100,400);
        font.draw(fontBatch, mouseY + "", 100,300);
        fontBatch.end();
    }

    public void removeBoard()
    {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
