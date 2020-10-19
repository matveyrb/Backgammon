package sample.model;

import java.util.ArrayList;
import java.util.List;

public interface BackGammon {
    Slot getSlot(int num);
    boolean step(boolean isWhite, int initialPosition, int endPosition);
    boolean canThrowAway(boolean isWhite);
    Color isWinGame();
    List<Integer> rollDice();
}
