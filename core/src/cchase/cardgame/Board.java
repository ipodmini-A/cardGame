package cchase.cardgame;
/**
 * Title: Board.java
 * This class makes me question if Computer Science was the right field for me.
 * Class will change as better solutions come up. Currently the class has 8 cards that are allowed on it.
 * The goal is to pull from your hand, and place the cards on the field.
 *
 * TODO: Code in zones for second player (AI) and also think about how the board is going to be laid out
 * TODO: This class has grown too big, and its starting to become difficult to keep track of it. Refactor if possible.
 * TODO: Begin to think about how the screen looks.
 */

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Board
{
    public class Zone implements InputProcessor
    {
        int zoneLocation;
        Card activeCard;
        boolean cardSelected = false;
        boolean cardPlaced = false;
        boolean cardAttacked = false;
        float zoneX = Gdx.graphics.getWidth() / 3.5f;
        float zoneY = Gdx.graphics.getHeight() / 4;
        int player;

        /**
         * Constructor. Currently, just sets the player to -1.
         */
        public Zone()
        {
            player = -1;
        }

        /**
         *
         * @param location represents where the cards will be rendered. 0 for the player side. 1 for the ai(player 2)
         */
        public Zone(int location)
        {
            inputMultiplexer.addProcessor(this);
            if (location == 0)
            {
                zoneX = zoneX + (150 * player1ZonesCount);
                zoneLocation = player1ZonesCount;
                player1ZonesCount++;
                player = 0;
            }

            if (location == 1)
            {
                zoneY = 400f;
                zoneX = zoneX + (150 * player2ZonesCount);
                zoneLocation = player2ZonesCount;
                player2ZonesCount++;
                player = 1;
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
            inputMultiplexer.addProcessor(this);
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
                if (activeCard.getHealth() <= 0 && cardPlaced)
                {
                    cardPlaced = false;
                    player1.discardPile.push(activeCard);
                }
            }
            if (cardSelected && cardPlaced)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1,1,0,0);
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
        public boolean keyDown(int keycode)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            {
                playerTurn = !playerTurn;
            }
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
         * TODO: Fix bug where player can select ai cards.
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) // && player == 0
            {

                mouseX = Gdx.input.getX();
                mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                if ((!cardPlaced) && (player == 0) && (playerTurn))
                {
                    for (int i = 0; i < player1.hand.currentHand.size(); i++)
                    {
                        if (player1.hand.currentHand.get(i).cardSelected &&
                                (mouseX > zoneX && mouseX < cardWidth + zoneX) &&
                                (mouseY > zoneY && mouseY < cardHeight + zoneY))
                        {
                            activeCard = player1.placeCardFromHand(i);
                            cardPlaced = true;
                            return true;
                        }
                    }
                }

                if (cardPlaced && playerTurn && !cardAttacked)
                {
                    for (int i = 0; i < player1.hand.currentHand.size() + 1; i++)
                    {
                        if ((mouseX > zoneX && mouseX < cardWidth + zoneX) &&
                                (mouseY > zoneY && mouseY < cardHeight + zoneY))
                        {
                            cardSelected = true;
                            return true;
                        }
                    }
                }
            }

            if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && cardPlaced && player == 0)
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
        float  zoneX = Gdx.graphics.getWidth() - 200;
        float zoneY = 100;

        public DeckZone(Player p)
        {
            inputMultiplexer.addProcessor(this);
            zoneLocation = -1;
            activeDeck = p.deck;
            player = 1;
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
    AIPlayer player2;
    float cardScale = 40f;
    float cardWidth = 2.5f * cardScale;
    float cardHeight = 3.5f * cardScale;
    SpriteBatch batch;
    SpriteBatch fontBatch;
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    float mouseX = Gdx.input.getX();
    float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY());
    int player1Zones = 4;
    int player1ZonesCount = 0;

    int player2Zones = 4;
    int player2ZonesCount = 0;
    Zone[] zone;
    DeckZone deckZone;
    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    boolean firstTurnDraw = false;
    boolean playerTurn = true;
    Random rand = new Random();


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
        player2 = new AIPlayer();
        zone = new Zone[8];
        for (int i = 0; i < 4; i++)
        {
            zone[i] = new Zone(0);
        }
        for (int i = 4; i < 8; i++)
        {
            zone[i] = new Zone(1);
        }
        deckZone = new DeckZone(player1);
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
        zone = new Zone[8];
        for (int i = 0; i < 4; i++)
        {
            zone[i] = new Zone(0);
        }
        for (int i = 4; i < 8; i++)
        {
            zone[i] = new Zone(1);
        }
        deckZone = new DeckZone(p1);
        inputMultiplexer.addProcessor(player1.hand);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void playerTurn()
    {
        for (int i = 0; i < 4; i++)
        {
            zone[i].cardAttacked = false;
        }
    }

    /**
     * boardPlace is rendering the zones and deck on screen. Now that I think about it, why is this
     * method called boardPlace?
     */
    public void boardPlace()
    {
        //There is a bad bug, where if a card attacks the card lower than it in the array list
        //This is most likely happening because of the j for loop.
        //Bug won't show up unless there is a card that attacks the card next to it.
        while (!firstTurnDraw)
        {
            for (int i = 0; i < 5; i++)
            {
                player1.hand.add(player1.deck.draw());
            }
            firstTurnDraw = true;
        }

        player1.hand.handCardRender(this);
        player2.AIUpdate(this);
        if (!playerTurn)
        {
            player2.aiPlay();
            playerTurn();
        }

        for (int i = 0; i < zone.length; i++)
        {
            zone[i].zoneRender();
            if (zone[i].cardSelected)
            {
                for (int j = 0; j < i; j++)
                {
                    if (zone[j].cardSelected)
                    {
                        zone[j].activeCard.setHealth(zone[j].activeCard.getHealth() - zone[i].activeCard.getAttack());
                        zone[i].cardSelected = false;
                        zone[i].cardAttacked = true;
                        zone[j].cardSelected = false;
                        break;
                    }
                }
                for (int k = i + 1; k < zone.length; k++)
                {
                    if (zone[k].cardSelected)
                    {
                        zone[k].activeCard.setHealth(zone[k].activeCard.getHealth() - zone[i].activeCard.getAttack());
                        zone[i].cardSelected = false;
                        zone[i].cardAttacked = true;
                        zone[k].cardSelected = false;
                        break;
                    }
                }
            }
        }
        deckZone.zoneRender();
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
        fontBatch.dispose();
    }
}
