package cchase.cardgame;
/**
 * Title: Board.java
 * This class makes me question if Computer Science was the right field for me.
 * Class will change as better solutions come up. Currently the class has 8 cards that are allowed on it.
 * The goal is to pull from your hand, and place the cards on the field.
 *
 * TODO: Code in zones for second player (AI) and also think about how the board is going to be laid out
 * TODO: Refactor zones. It should be easier to spawn Zones. Modify constructors?
 */

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
            if (player1ZonesCount < player1Zones)
            {
                zoneX = zoneX + (150 * player1ZonesCount);
                zoneLocation = player1ZonesCount;
                player1ZonesCount++;
            }
        }

        /**
         * Parameter constructor. Accepts two floats, one representing X, and one representing Y
         * Warning: Be careful, LibGDX is annoying with how it handles its X and Y axis. Don't say I
         * didn't warn you future self.
         * @param x X location of the rectangle that is currently representing the zone
         * @param y Y location of the rectangle that is currently representing the zone
         */
        public Zone(float x, float y)
        {
            zoneX = x;
            zoneY = y;
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
         * Checks and updates mouseX and mouseY with the current mouse location when called.
         * In its current state, when the left mouse button is clicked, it will place down a card in the
         * zone. If the right mouse button is pressed, the card is removed from the zone.
         * This uses the touchDown method from InputProcessor. If this grows, this will
         * have to be split into a different class eventually.
         *
         * TODO: Cry... Oh and only allow for one card to be selected.
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !cardPlaced)
            {
                mouseX = Gdx.input.getX();
                mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                for (int i = 0; i < player1.hand.currentHand.size(); i++)
                {
                    if (player1.hand.currentHand.get(i).cardSelected == true &&
                            (mouseX > zoneX && mouseX < cardWidth + zoneX) &&
                            (mouseY > zoneY && mouseY < cardHeight + zoneY))
                    {
                        activeCard = player1.placeCardFromHand(i);
                        cardPlaced = true;
                        return true;
                    }
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
    int player1Zones = 4;
    int player1ZonesCount = 0;
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
        player2 = new Player();
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
        inputMultiplexer.addProcessor(player1.hand);
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

    /**
     * boardPlace is rendering the zones and deck on screen. Now that I think about it, why is this
     * method called boardPlace?
     */
    public void boardPlace()
    {
        player1.hand.handCardRender();
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

    /**
     * "Deconstructor" for Board. disposes of batch, font, and shapeRenderer.
     * TODO: Add more dispose methods as they arise
     */
    public void removeBoard()
    {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
