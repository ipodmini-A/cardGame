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

public class Board
{
    public class Zone
    {
        int zoneLocation;
        Card activeCard;
        public Zone(int z)
        {
            if (zonesCount < zones)
            {
                zoneLocation = zonesCount;
                zonesCount++;
            }else
            {
                RuntimeException zonesFull = new RuntimeException("Zones Full");
                //test
                //test2
            }
        }


        public void ZoneRender()
        {
            shapeRenderer.rect(50.0f, 50.0f, cardWidth, cardHeight);
        }
    }

    Card card1player1;
    boolean card1Player1Active = false;
    Card card2player1;
    Card card3player1;
    Card card4player1;
    Card card1player2;
    Card card2player2;
    Card card3player2;
    Card card4player2;
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

    public Board()
    {
        //Default constructor.
        //batch is created to allow shapes to be drawn.
        //fontBatch is created to allow for fonts. If shapes and fonts are passed through the same batch,
        //visual errors occur with the font. I also can't find a better way to solve this online, so I
        //am stuck with this solution.
        //
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        player1 = new Player();
    }

    public Board(Player p1)
    {
        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(1,1,1,0);
        shapeRenderer = new ShapeRenderer();
        player1 = p1;
    }



    public void player1Zone1()
    {
        if (card1Player1Active == true)
        {
            batch.begin();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1,0,0,0);
            shapeRenderer.rect(50.0f, 50.0f, cardWidth, cardHeight);
            shapeRenderer.end();
            batch.end();
            fontBatch.begin();
            font.draw(fontBatch, card1player1.getName(), 75,75);
            fontBatch.end();
            if (card1player1.getHealth() == 0 && card1Player1Active == true)
            {
                card1Player1Active = false;
                card1player1 = null;
            }
        }
    }

    public void boardPlace()
    {

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && card1Player1Active == false)
        {
            mouseX = Gdx.input.getX();
            mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
            if ((mouseX > 50.0f && mouseX < cardWidth + 50.0f) && (mouseY > 50.0f && mouseY < cardHeight + 50.0f))
            {
                player1.draw();
                placeCard(player1.placeCardFromHand());
            }
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && card1Player1Active == true)
        {
            mouseX = Gdx.input.getX();
            mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
            if ((mouseX > 50.0f && mouseX < cardWidth + 50.0f) && (mouseY > 50.0f && mouseY < cardHeight + 50.0f))
            {
                card1Player1Active = false;
                card1player1 = null;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            player1.draw();
            placeCard(player1.placeCardFromHand());
        }

        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0);
        shapeRenderer.rect(50.0f, 50.0f, cardWidth, cardHeight);
        shapeRenderer.rect(200.0f, 50.0f, cardWidth, cardHeight);
        shapeRenderer.rect(350.0f, 50.0f, cardWidth, cardHeight);
        shapeRenderer.rect(500.0f, 50.0f, cardWidth, cardHeight);
        shapeRenderer.end();
        batch.end();
        player1Zone1();
        fontBatch.begin();
        font.draw(fontBatch, mouseX + "", 100,400);
        font.draw(fontBatch, mouseY + "", 100,300);
        fontBatch.end();
    }

    public void placeCard(Card c)
    {
        //When a card is passed through, it should be displayed on the board. This function should also be overloaded.
        card1player1 = c;
        card1Player1Active = true;
    }

    public void removeBoard()
    {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
