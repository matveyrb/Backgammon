package sample.model;


import java.util.*;

public class GameBoard implements BackGammon {
    static final int COUNT_SLOTS = 24;
    static final int START_WHITE_SLOT = 0;
    static final int START_BLACK_SLOT = 12;
    private Random random = new Random();
    private final Slot[] board = new Slot[COUNT_SLOTS];
    private int outCounterBlack = 0;
    private int outCounterWhite = 0;
    private int countFromHead = 1;
    private boolean firstWhite = true;
    private boolean firstBlack = true;
    private  Integer diceOne;
    private  Integer diceTwo;

    private Map<Integer, Integer> outPositionWhite = new HashMap<>();
    private Map<Integer, Integer> outPositionBlack = new HashMap<>();


    private List<Integer> step = new ArrayList<>();
    private TurnColor color = TurnColor.WHITE;

    public void setOutCounterBlack (int num) {this.outCounterBlack = num;}

    public void setOutCounterWhite (int num) {this.outCounterWhite = num;}

    public GameBoard() {
        for (int i = 0; i < COUNT_SLOTS; i++) {
            board[i] = Slot.createEmptySlot();
        }
        outPositionWhite.put(23, 1);
        outPositionWhite.put(22, 2);
        outPositionWhite.put(21, 3);
        outPositionWhite.put(20, 4);
        outPositionWhite.put(19, 5);
        outPositionWhite.put(18, 6);

        outPositionBlack.put(11, 1);
        outPositionBlack.put(10, 2);
        outPositionBlack.put(9, 3);
        outPositionBlack.put(8, 4);
        outPositionBlack.put(7, 5);
        outPositionBlack.put(6, 6);

        board[START_WHITE_SLOT] = Slot.createFullWhiteSlot();
        board[START_BLACK_SLOT] = Slot.createFullBlackSlot();
    }

    public Slot getSlot(int num) {return board[num];}

    public boolean isWhite()
    {
        return color==TurnColor.WHITE;
    }

    @Override
    public boolean move(Integer triangleNumberFirst, Integer triangleNumberLast) {
        if(step.isEmpty())
            return false;
        if(triangleNumberFirst==null || triangleNumberLast==null)
            return false;

        if (triangleNumberLast == 1000)
        {
            if(!canThrowAway(isWhite()))
                return false;
            Map<Integer,Integer> dicePos  = isWhite() ? outPositionWhite: outPositionBlack;
            int diceVal = dicePos.get(triangleNumberFirst);
            System.out.println(diceVal + " " +isWhite());
            if(step.contains(diceVal))
            {
                step.remove((Object)diceVal);
                return step(triangleNumberFirst, triangleNumberLast);
            }
            else
            {
                for(int i = diceVal + 1, pos = 1; i <=6; i++, pos ++ )
                {
                    if(step.contains(i) && getSlot(triangleNumberFirst-pos).getColor()==Color.NULL)
                    {
                        step.remove((Object)i);
                        return step(triangleNumberFirst, triangleNumberLast);
                    }
                }
            }
            return false;
        }

        for (int i = 0; i < step.size(); i++) {
            if (triangleNumberLast - triangleNumberFirst == step.get(i) || !isWhite() &&
                    (24 - triangleNumberFirst + triangleNumberLast == step.get(i))) {
                if (countFromHead <=0 && (triangleNumberFirst == 0 && isWhite() || triangleNumberFirst == 12 && !isWhite()))
                {
                    return false;
                }
                if (getSlot(triangleNumberLast).getColor() == getSlot(triangleNumberFirst).getColor() ||
                        getSlot(triangleNumberLast).getColor() == Color.NULL) {
                    step.remove(i);
                    if (triangleNumberFirst == 0 && isWhite() || triangleNumberFirst == 12 && !isWhite())
                        countFromHead--;
                    return step(triangleNumberFirst, triangleNumberLast);
                } else
                    return false;
            }
        }

        return false;
    }

    @Override
    public boolean turnIsOver() {
        return step.isEmpty();
    }

    public void nextPlayer() {
        countFromHead = 1;
        color = (color == TurnColor.WHITE) ? TurnColor.BLACK :  TurnColor.WHITE;
        step.clear();
        diceOne = null;
        diceTwo = null;
    }

    public boolean emptyCell(Integer num) {
        if(num!=1000)
            return board[num].getCount()==0;
        return false;
    }

    public boolean step(int initialPosition, int endPosition) {
        Color current;
        if (isWhite()){
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

            if (isWhite()) {
                board[endPosition].addChecker(Color.WHITE);
            } else {
                board[endPosition].addChecker(Color.BLACK);
            }
        return true;
    }

    public boolean canThrowAway(boolean isWhite){
        int start = 18;
        int finish = 23;
        Color color = Color.WHITE;
        if (!isWhite) {
            start = 6;
            finish = 11;
            color = Color.BLACK;
        }
        for (int i = 0; i < 24; i++){
            if (board[i].getColor().equals(color) && !(i>=start && i<=finish)){
                return false;
            }
        }
        return true;
    }

    public Color isWinGame() {
        if (outCounterBlack == 15) {
            return Color.BLACK;
        } else if (outCounterWhite == 15) {
            return Color.WHITE;
        }
        return Color.NULL;
    }

    public void rollDice() {
        step = new ArrayList<>();
        int firstDice = random.nextInt(6) + 1;
        int secondDice = random.nextInt(6) + 1;
        diceOne = firstDice;
        diceTwo = secondDice;
        if (firstDice == secondDice) {
            for (int i = 0; i < 4; i++) {
                step.add(firstDice);
            }
        } else {
            step.add(firstDice);
            step.add(secondDice);
        }
        if((isWhite() && firstWhite || !isWhite() && firstBlack )&& step.size()>2)
        {
            countFromHead = 2;
        }
        if(isWhite())
            firstWhite = false;
        if(!isWhite())
            firstBlack = false;
    }

    public boolean haveMove()
    {
        return !step.isEmpty();
    }

    public boolean canMove()
    {
        for (int element : step) {
            for (int i = 0; i < 24 - element; i++) {
                if (color == TurnColor.WHITE)
                {
                    if (i > 17 && i + element > 23) return true; // out
                    if (getSlot(i).isWhite() && !getSlot(i + element).isBlack()) {
                        return true;
                    }
                } else {
                    if (i + element <= 23 && i > 11) { // lower row
                        if (getSlot(i).isBlack() && !getSlot(i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i < 11 - element) { // upper row
                        if (!getSlot(i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i > 17 && 24 - i + element < 6) { // from lower to upper row (border)
                        if (!getSlot(24 - i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i > 5 && i + element > 11) return true; // out
                }
            }
        }
        return false;
    }

    @Override
    public Integer getDiceOne() {
        return diceOne;
    }

    @Override
    public Integer getDiceTwo() {
        return diceTwo;
    }

    @Override
    public TurnColor getCurrentTurnColor() {
        return color;
    }
}