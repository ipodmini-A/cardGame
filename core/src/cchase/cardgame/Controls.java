package cchase.cardgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Controls implements InputProcessor
{
    private Board board;
    private Hand hand;
    private float mouseX;
    private float mouseY;

    public Controls(Board b)
    {
        board = b;
    }

    public Controls (Hand h)
    {
        hand = h;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            board.playerTurn = !board.playerTurn;
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
     * Checks to see if the card was clicked on, and will change the cards color to indicate that it was selected.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
                && board.playerTurn)
        {
            mouseX = Gdx.input.getX();
            mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.

            //Deck draw
            if ((mouseX > board.deckZone.zoneX && mouseX < board.cardWidth + board.deckZone.zoneX) && (mouseY > board.deckZone.zoneY && mouseY < board.cardHeight + board.deckZone.zoneY)
                    & !board.playerDrew)
            {
                board.player1.draw();
                board.playerDrew = true;
                return true;
            }

            //Draw check
            if (!board.playerDrew)
            {
                System.out.println("You must draw");
                return true;
            }

            //Hand selection
            for (int i = 0; i < hand.currentHand.size(); i++)
            {
                if ((mouseX > hand.currentHand.get(i).cardLocationX &&
                        mouseX < hand.cardWidth + hand.currentHand.get(i).cardLocationX)
                        && (mouseY > hand.currentHand.get(i).cardLocationY
                        && mouseY < hand.cardHeight + hand.currentHand.get(i).cardLocationY)
                        && !hand.isCardAlreadySelected())
                {
                    hand.currentHand.get(i).cardSelected = true;
                    hand.setCardAlreadySelected(true);
                    return true;
                } else if ((mouseX > hand.currentHand.get(i).cardLocationX &&
                            mouseX < hand.cardWidth + hand.currentHand.get(i).cardLocationX)
                            && (mouseY > hand.currentHand.get(i).cardLocationY
                            && mouseY < hand.cardHeight + hand.currentHand.get(i).cardLocationY))
                {
                    if (hand.currentHand.get(i).cardSelected)
                    {
                        hand.currentHand.get(i).cardSelected = false;
                        hand.setCardAlreadySelected(false);
                    }
                }
                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
                {
                    int mouseX = Gdx.input.getX();
                    int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.
                    if ((mouseX > hand.currentHand.get(i).cardLocationX &&
                            mouseX < hand.cardWidth + hand.currentHand.get(i).cardLocationX)
                            && (mouseY > hand.currentHand.get(i).cardLocationY
                            && mouseY < hand.cardHeight + hand.currentHand.get(i).cardLocationY))
                    {
                        if (hand.currentHand.get(i).cardSelected)
                        {
                            hand.currentHand.get(i).cardSelected = false;
                            hand.setCardAlreadySelected(false);
                        }
                    }
                }
            }

            //Board selection
            for (int j = 0; j < board.zone.length; j++)
            {
                if ((!board.zone[j].cardPlaced) && (board.zone[j].player == 0) && (board.playerTurn))
                {
                    for (int i = 0; i < board.player1.hand.currentHand.size(); i++)
                    {
                        if (board.player1.hand.currentHand.get(i).cardSelected &&
                                (mouseX > board.zone[j].zoneX && mouseX < board.cardWidth + board.zone[j].zoneX) &&
                                (mouseY > board.zone[j].zoneY && mouseY < board.cardHeight + board.zone[j].zoneY))
                        {
                            board.zone[j].activeCard = board.player1.placeCardFromHand(i);
                            board.zone[j].cardPlaced = true;
                            board.player1.hand.setCardAlreadySelected(false);
                            return true;
                        }
                    }
                }

                if (board.zone[j].cardPlaced && board.playerTurn && !board.zone[j].cardAttacked && !board.zone[j].cardSelected && board.zone[j].player == 0)
                {
                    for (int i = 0; i < board.zone.length; i++)
                    {
                        if ((mouseX > board.zone[j].zoneX && mouseX < board.cardWidth + board.zone[j].zoneX) &&
                                (mouseY > board.zone[j].zoneY && mouseY < board.cardHeight + board.zone[j].zoneY))
                        {
                            board.zone[j].cardSelected = true;
                            return true;
                        }
                    }
                } else if (board.zone[j].cardSelected && board.playerTurn && !board.zone[j].cardAttacked && board.zone[j].player == 0)
                {
                    for (int i = 0; i < board.zone.length + 1; i++)
                    {
                        if ((mouseX > board.zone[j].zoneX && mouseX < board.cardWidth + board.zone[j].zoneX) &&
                                (mouseY > board.zone[j].zoneY && mouseY < board.cardHeight + board.zone[j].zoneY))
                        {
                            board.zone[j].cardSelected = false;
                            return true;
                        }
                    }
                }

                if (!board.zone[j].cardSelected && board.playerTurn && !board.zone[j].cardAttacked && board.zone[j].player == 1)
                {
                    for (int i = 0; i < board.zone.length - 1; i++)
                    {
                        if ((mouseX > board.zone[j].zoneX && mouseX < board.cardWidth + board.zone[j].zoneX) &&
                                (mouseY > board.zone[j].zoneY && mouseY < board.cardHeight + board.zone[j].zoneY) && board.zone[i].cardSelected)
                        {
                            board.zone[j].cardSelected = true;
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
        {
            int mouseX = Gdx.input.getX();
            int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()); //lol y is inverted so this is to un-invert it.

            //Hand de-selection
            for (int i = 0; i < hand.currentHand.size(); i++)
            {
                if ((mouseX > hand.currentHand.get(i).cardLocationX &&
                        mouseX < board.cardWidth + hand.currentHand.get(i).cardLocationX)
                        && (mouseY > hand.currentHand.get(i).cardLocationY
                        && mouseY < board.cardHeight + hand.currentHand.get(i).cardLocationY))
                {
                    if (hand.currentHand.get(i).cardSelected)
                    {
                        hand.currentHand.get(i).cardSelected = false;
                        hand.setCardAlreadySelected(false);
                    }
                }
            }

            //Board de-selection
            for (int j = 0; j < board.zone.length; j++)
            {
                if ((mouseX > board.zone[j].zoneX && mouseX < board.cardWidth + board.zone[j].zoneX) &&
                        (mouseY > board.zone[j].zoneY && mouseY < board.cardHeight + board.zone[j].zoneY)
                        && board.zone[j].cardSelected)
                {
                    board.zone[j].cardSelected = false;
                    return true;
                }
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

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Hand getHand()
    {
        return hand;
    }

    public void setHand(Hand hand)
    {
        this.hand = hand;
    }
}
