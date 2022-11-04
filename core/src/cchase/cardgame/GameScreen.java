package cchase.cardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends ScreenAdapter
{
    CardGame game;
    Board board;

    public GameScreen(CardGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        board = new Board();
        game.dispose();
    }
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        board.boardPlace();
    }
@Override
    public void hide()
    {
        board.removeBoard();
    }
}
