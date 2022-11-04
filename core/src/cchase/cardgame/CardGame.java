package cchase.cardgame;

/**
 * Title: CardGame.java
 *
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CardGame extends Game
{
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;


	@Override
	public void create()
	{
		// create() makes a new SpriteBatch, ShapeRenderer and BitmapFont. Once these objects are created,
		// the screen is then passed to the TitleScreen.
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
