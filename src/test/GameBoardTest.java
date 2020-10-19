package test;

import org.junit.jupiter.api.Test;
import sample.model.Color;
import sample.model.GameBoard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    @Test
    void step() {
        GameBoard actualWhite = new GameBoard();
        assertTrue(actualWhite.step(true, 0, 1));
        GameBoard expectedWhite = new GameBoard();
        expectedWhite.getSlot(0).removeChecker();
        expectedWhite.getSlot(1).addChecker(Color.WHITE);
        assertEquals(expectedWhite.getSlot(0), actualWhite.getSlot(0));
        assertEquals(expectedWhite.getSlot(1), actualWhite.getSlot(1));

        GameBoard actualBlack = new GameBoard();
        assertTrue(actualBlack.step(false, 12, 13));
        GameBoard expectedBlack = new GameBoard();
        expectedBlack.getSlot(12).removeChecker();
        expectedBlack.getSlot(13).addChecker(Color.BLACK);
        assertEquals(expectedBlack.getSlot(12), actualBlack.getSlot(12));
        assertEquals(expectedBlack.getSlot(13), actualBlack.getSlot(13));

        GameBoard actualBlack2 = new GameBoard();
        assertFalse(actualBlack2.step(false, 0, 1));

        GameBoard actualWhite2 = new GameBoard();
        assertFalse(actualWhite2.step(true, 12, 13));

        GameBoard actualWhite3 = new GameBoard();
        actualWhite3.getSlot(0).setSlot(0,Color.NULL);
        actualWhite3.getSlot(23).setSlot(1, Color.WHITE);
        actualWhite3.step(true, 23, 1000);
        GameBoard expectedWhite3 = new GameBoard();
        expectedWhite3.getSlot(23).setSlot(0, Color.NULL);
        assertEquals(actualWhite3.getSlot(23), expectedWhite3.getSlot(23));

        GameBoard actualBlack3 = new GameBoard();
        actualBlack3.getSlot(12).setSlot(0,Color.NULL);
        actualBlack3.getSlot(11).setSlot(1, Color.BLACK);
        actualBlack3.step(true, 11, 1000);
        GameBoard expectedBlack3 = new GameBoard();
        expectedWhite3.getSlot(11).setSlot(0, Color.NULL);
        assertEquals(actualBlack3.getSlot(11), expectedBlack3.getSlot(11));
    }

    @Test
    void isWinGame() {
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
    void rollDice() {
        GameBoard test = new GameBoard();
        List<Integer> steps = test.rollDice();
        if (steps.get(0).equals(steps.get(1))) {
            assertEquals(steps.get(2), steps.get(3));
            assertEquals(steps.get(1), steps.get(2));
        } else {
            assertEquals(2, steps.size());
        }
    }

    @Test
    void canThrowAway() {
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

}