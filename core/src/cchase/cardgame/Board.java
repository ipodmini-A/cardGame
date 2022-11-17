package cchase.cardgame;
/**
 * Title: Board.java
 * This class makes me question if Computer Science was the right field for me.
 * Class will change as better solutions come up. Currently the class has 8 cards that are allowed on it.
 * The goal is to pull from your hand, and place the cards on the field.
 *
 * TODO: Think about how the board is going to be laid out
 * TODO: This class has grown too big, and its starting to become difficult to keep track of it. Refactor if possible.
 * TODO: Time to think about rules.
 */

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Board
{
    public class Zone
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
    }

    public class DeckZone extends Zone
    {
        Deck activeDeck;
        float  zoneX = Gdx.graphics.getWidth() - 200;
        float zoneY = 100;

        public DeckZone(Player p)
        {
            zoneLocation = -1;
            activeDeck = p.getDeck();
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
    }

    Player player1;
    AIPlayer player2;
    float cardScale = 50f;
    float cardWidth = 2.5f * cardScale;
    float cardHeight = 3.5f * cardScale;
    SpriteBatch batch;
    SpriteBatch fontBatch;
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    int player1ZonesCount = 0;
    int player2ZonesCount = 0;
    private int turn = 0;

    Zone[] zone;
    DeckZone deckZone;
    Controls controls;
    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    boolean firstTurnDraw = false;
    boolean playerDrew = false;
    boolean playerTurn = true;
    boolean playerAttackForGame = false;
    boolean aiAttackForGame = false;

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
        controls = new Controls(player1.hand);
        controls.setBoard(this);
        inputMultiplexer.addProcessor(controls);
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
        controls = new Controls(player1.hand);
        inputMultiplexer.addProcessor(controls);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void playerTurn()
    {

        for (int i = 0; i < 4; i++)
        {
            zone[i].cardAttacked = false;
        }
        playerDrew = false;
    }

    public void attack(Card attackingCard, Card defendingCard)
    {
        try
        {
            defendingCard.setHealth(defendingCard.getHealth() - attackingCard.getAttack());
        }catch (Exception e)
        {
            for (int i = 0; i < zone.length; i++)
            {
                boolean ActiveCards = false;
                if (zone[i].player == 1)
                {
                    ActiveCards = zone[i].cardPlaced;
                }

                if (!ActiveCards && turn != 0)
                {
                    playerAttackForGame = true;
                }else
                {
                    System.out.println("Card not found");
                }
            }
        }
    }

    /**
     * zoneRenderAndAttackCheck currently renders the zones on screen, and checks to see if an attack was made.
     * Note, zoneRender and AttackCheck will be split in the future.
     */
    public void zoneRenderAndAttackCheck()
    {
        for (int i = 0; i < zone.length; i++)
        {
            zone[i].zoneRender();
            if (zone[i].cardSelected)
            {
                for (int j = 0; j < i; j++)
                {
                    if (zone[j].cardSelected)
                    {
                        attack(zone[i].activeCard, zone[j].activeCard);
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
                        attack(zone[i].activeCard, zone[k].activeCard);
                        zone[i].cardSelected = false;
                        zone[i].cardAttacked = true;
                        zone[k].cardSelected = false;
                        break;
                    }
                }
            }
        }
    }

    /**
     * boardPlace is rendering the zones and deck on screen. Now that I think about it, why is this
     * method called boardPlace?
     * TODO: Fix attacking issue, where player cannot attack AI
     */
    public void boardPlace()
    {
        //There is a bad bug, where if a card attacks the card lower than it in the array list
        //This is most likely happening because of the j for loop.
        //Bug won't show up unless there is a card that attacks the card next to it.
        while (!firstTurnDraw)
        {
            for (int i = 0; i < 1; i++)
            {
                player1.hand.add(player1.getDeck().draw());
            }
            firstTurnDraw = true;
        }

        player1.hand.handCardRender(this);
        player2.AIUpdate(this);
        if (!playerTurn)
        {
            turn++;
            player2.aiPlay();
            turn++;
            playerTurn();
        }
        zoneRenderAndAttackCheck();
        deckZone.zoneRender();

        if (playerAttackForGame && turn != 0)
        {
            System.out.println("Player has won");
        }
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

    public boolean isPlayerAttackForGame()
    {
        return playerAttackForGame;
    }

    public void setPlayerAttackForGame(boolean playerAttackForGame)
    {
        this.playerAttackForGame = playerAttackForGame;
    }

    public int getTurn()
    {
        return turn;
    }

    public void setTurn(int turn)
    {
        this.turn = turn;
    }
}
