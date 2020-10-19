package sample.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameBoard implements BackGammon {
    static final int COUNT_SLOTS = 24;
    static final int START_WHITE_SLOT = 0;
    static final int START_BLACK_SLOT = 12;
    private Random random = new Random();
    private final Slot[] board = new Slot[COUNT_SLOTS];
    private int outCounterBlack = 0;
    private int outCounterWhite = 0;

    public void setOutCounterBlack (int num) {this.outCounterBlack = num;}

    public void setOutCounterWhite (int num) {this.outCounterWhite = num;}

    public GameBoard() {
        for (int i = 0; i < COUNT_SLOTS; i++) {
            board[i] = Slot.createEmptySlot();
        }
        board[START_WHITE_SLOT] = Slot.createFullWhiteSlot();
        board[START_BLACK_SLOT] = Slot.createFullBlackSlot();
    }

    public Slot getSlot(int num) {return board[num];}

    public boolean step(boolean isWhite, int initialPosition, int endPosition) {
        Color current;
        if (isWhite){
            current = Color.WHITE;
        } else current = Color.BLACK;
        if (endPosition == 1000) {
            board[initialPosition].removeChecker();
            if (current == Color.BLACK){
                outCounterBlack++;
            } else {
                outCounterWhite++;
            }
            return true;
        }
        if (!current.equals(board[initialPosition].getColor())) return false;
        if (!current.equals(board[endPosition].getColor()) && board[endPosition].getColor() != Color.NULL) return false;
        board[initialPosition].removeChecker();

            if (isWhite) {
                board[endPosition].addChecker(Color.WHITE);
            } else {
                board[endPosition].addChecker(Color.BLACK);
            }
        return true;
    }

    public boolean canThrowAway(boolean isWhite){
        int count = 0;
        int start = 18;
        int finish = 24;
        Color color = Color.WHITE;
        if (!isWhite) {
            start = 6;
            finish = 12;
            color = Color.BLACK;
        }
        for (int i = start; i < finish; i++){
            if (board[i].getColor().equals(color)){
                count += board[i].getCount();
            }
        }
        return count + outCounterBlack == 15;
    }

    public Color isWinGame() {
        if (outCounterBlack == 15) {
            System.out.println("Black wins");
            return Color.BLACK;
        } else if (outCounterWhite == 15) {
            System.out.println("White wins");
            return Color.WHITE;
        }
        return Color.NULL;
    }

    public List<Integer> rollDice() {
        List<Integer> steps = new LinkedList<>();
        int firstDice = random.nextInt(6) + 1;
        int secondDice = random.nextInt(6) + 1;
        if (firstDice == secondDice) {
            for (int i = 0; i < 4; i++) {
                steps.add(firstDice);
            }
        } else {
            steps.add(firstDice);
            steps.add(secondDice);
        }
        return steps;
    }
}