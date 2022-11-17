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
    }
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        board.boardPlace();
        if (board.playerAttackForGame)
        {
            game.setScreen(new EndScreen(game));
        }
    }
@Override
    public void hide()
    {
        board.removeBoard();
    }
}
