package sample.model;


public interface BackGammon {
    Slot getSlot(int num);
    boolean canThrowAway(boolean isWhite);
    Color isWinGame();
    void rollDice();
    Integer getDiceOne();
    Integer getDiceTwo();
    TurnColor getCurrentTurnColor();
    boolean isWhite();
    boolean move(Integer triangleNumberFirst, Integer triangleNumberLast);
    boolean turnIsOver();
    void nextPlayer();

    boolean haveMove();
    boolean canMove();
    boolean emptyCell(Integer num);
}
