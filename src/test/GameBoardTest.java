package test;


import org.junit.Test;
import sample.model.BackGammon;
import sample.model.Color;
import sample.model.GameBoard;

import static org.junit.Assert.*;

public class GameBoardTest  {

    @Test
    public void canMove()
    {
        BackGammon game = new GameBoard();
        assertTrue(!game.canMove());
        game.rollDice();
        assertTrue(game.canMove());
        for(int i=1; i<=11; i++)
            game.getSlot(i).setSlot(1, Color.BLACK);
        assertTrue(!game.canMove());
    }

    @Test
    public void haveMove()
    {
        BackGammon game = new GameBoard();
        assertTrue(!game.haveMove());
        game.rollDice();
        assertTrue(game.haveMove());
    }

    @Test
    public void emptyCell()
    {
        BackGammon game = new GameBoard();
        assertTrue(!game.emptyCell(0));
        assertTrue(!game.emptyCell(12));
        assertTrue(game.emptyCell(1));
    }

    @Test
    public void move()
    {
        BackGammon game = new GameBoard();
        game.rollDice();
        int diceOne = game.getDiceOne();
        int diceTow = game.getDiceTwo();
        game.move(0, diceOne);
        assertEquals(Color.WHITE, game.getSlot(diceOne).getColor());
        game.move(diceOne, diceOne + diceTow);
        assertEquals(Color.WHITE, game.getSlot(diceOne+diceTow).getColor());
    }

    @Test
    public void roll()
    {
        BackGammon game = new GameBoard();

        assertNull(game.getDiceOne());
        assertNull(game.getDiceTwo());

        game.rollDice();

        int diceOne = game.getDiceOne();
        int diceTow = game.getDiceTwo();

        assertTrue(diceOne >= 1 && diceOne<=6);
        assertTrue(diceTow >= 1 && diceTow<=6);
    }
    @Test
    public void nextPlayer()
    {
        BackGammon game = new GameBoard();
        boolean startedWhite = game.isWhite();
        game.nextPlayer();
        assertTrue(startedWhite != game.isWhite());
        assertNull(game.getDiceOne());
        assertNull(game.getDiceTwo());
    }

    @Test
    public void isWinGame()
    {
        GameBoard test = new GameBoard();
        test.setOutCounterBlack(15);
        assertEquals(Color.BLACK, test.isWinGame());
        test.setOutCounterBlack(0);
        test.setOutCounterWhite(15);
        assertEquals(Color.WHITE, test.isWinGame());
        test.setOutCounterWhite(0);
        assertEquals(Color.NULL, test.isWinGame());
    }

    @Test
    public void canThrowAway() {
        GameBoard test = new GameBoard();
        test.getSlot(0).setSlot(0, Color.NULL);
        test.getSlot(12).setSlot(0, Color.NULL);
        test.getSlot(23).setSlot(14,Color.WHITE);
        test.getSlot(22).setSlot(1,Color.WHITE);
        assertTrue(test.canThrowAway(true));

        test.getSlot(23).setSlot(0, Color.NULL);
        test.getSlot(11).setSlot(14, Color.BLACK);
        test.getSlot(10).setSlot(1, Color.BLACK);
        assertTrue(test.canThrowAway(false));

        test.getSlot(22).setSlot(0,Color.WHITE);
        test.getSlot(1).setSlot(1,Color.WHITE);
        test.getSlot(10).setSlot(0, Color.BLACK);
        test.getSlot(13).setSlot(1, Color.BLACK);
        assertFalse(test.canThrowAway(true));
        assertFalse(test.canThrowAway(false));
    }

    @Test
    public void step() {
        GameBoard actualWhite = new GameBoard();
        assertTrue(actualWhite.step(0, 1));
        GameBoard expectedWhite = new GameBoard();
        expectedWhite.getSlot(0).removeChecker();
        expectedWhite.getSlot(1).addChecker(Color.WHITE);
        assertEquals(expectedWhite.getSlot(0), actualWhite.getSlot(0));
        assertEquals(expectedWhite.getSlot(1), actualWhite.getSlot(1));

        GameBoard actualBlack = new GameBoard();
        actualBlack.nextPlayer();
        assertTrue(actualBlack.step(12, 13));
        GameBoard expectedBlack = new GameBoard();
        expectedBlack.getSlot(12).removeChecker();
        expectedBlack.getSlot(13).addChecker(Color.BLACK);
        assertEquals(expectedBlack.getSlot(12), actualBlack.getSlot(12));
        assertEquals(expectedBlack.getSlot(13), actualBlack.getSlot(13));

        GameBoard actualBlack2 = new GameBoard();
        actualBlack2.nextPlayer();
        assertFalse(actualBlack2.step( 0, 1));

        GameBoard actualWhite2 = new GameBoard();
        assertFalse(actualWhite2.step(12, 13));

        GameBoard actualWhite3 = new GameBoard();
        actualWhite3.getSlot(0).setSlot(0,Color.NULL);
        actualWhite3.getSlot(23).setSlot(1, Color.WHITE);
        actualWhite3.step( 23, 1000);
        GameBoard expectedWhite3 = new GameBoard();
        expectedWhite3.getSlot(23).setSlot(0, Color.NULL);
        assertEquals(actualWhite3.getSlot(23), expectedWhite3.getSlot(23));

        GameBoard actualBlack3 = new GameBoard();
        actualBlack3.getSlot(12).setSlot(0,Color.NULL);
        actualBlack3.getSlot(11).setSlot(1, Color.BLACK);
        actualBlack3.step( 11, 1000);
        GameBoard expectedBlack3 = new GameBoard();
        expectedWhite3.getSlot(11).setSlot(0, Color.NULL);
        assertEquals(actualBlack3.getSlot(11), expectedBlack3.getSlot(11));
    }
}