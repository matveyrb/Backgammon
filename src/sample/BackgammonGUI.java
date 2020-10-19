package sample;

import sample.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class BackgammonGUI {
    private HashMap<Integer, Image> picturesBlack;
    private HashMap<Integer, Image> picturesWhite;
    private HashMap<Integer, Image> dices;
    private BackGammon logic;
    private Integer triangleNumberFirst;
    private Integer triangleNumberLast;
    private List<Integer> step;
    private int count = 0;
    private boolean isWhite = true;
    private int tempStep = 0;
    private String turn = "White turn";
    private boolean isEquals;
    private boolean isTurnFromHead = false;
    private ImageView[] arrayImageView;

    @FXML
    private ImageView tr12;

    @FXML
    private ImageView tr11;

    @FXML
    private ImageView tr10;

    @FXML
    private ImageView tr9;

    @FXML
    private ImageView tr8;

    @FXML
    private ImageView tr7;

    @FXML
    private ImageView tr6;

    @FXML
    private ImageView tr5;

    @FXML
    private ImageView tr4;

    @FXML
    private ImageView tr3;

    @FXML
    private ImageView tr2;

    @FXML
    private ImageView tr1;

    @FXML
    private ImageView tr13;

    @FXML
    private ImageView tr14;

    @FXML
    private ImageView tr15;

    @FXML
    private ImageView tr16;

    @FXML
    private ImageView tr17;

    @FXML
    private ImageView tr18;

    @FXML
    private ImageView tr19;

    @FXML
    private ImageView tr20;

    @FXML
    private ImageView tr21;

    @FXML
    private ImageView tr22;

    @FXML
    private ImageView tr23;

    @FXML
    private ImageView tr24;

    @FXML
    private Button diceRoller;

    @FXML
    private ImageView diceOne;

    @FXML
    private ImageView diceTwo;

    @FXML
    private Label diceValue;

    @FXML
    public void rollDice() {
        step = logic.rollDice();
        diceValue.setText("Your dices are: \n" + step.get(0) + ", " + step.get(1) + "\n" + turn);
        diceOne.setImage(dices.get(step.get(0)));
        diceTwo.setImage(dices.get(step.get(1)));
        diceRoller.setDisable(true);
        tempStep = step.size();
        isEquals = step.get(0).equals(step.get(1)) && (step.get(0).equals(3) || step.get(0).equals(4) || step.get(0).equals(6));
    }

    public BackgammonGUI() throws FileNotFoundException {
        setMapDice();
        setMapImages();
    }

    public void draw() {
        if (arrayImageView == null) {
            arrayImageView = new ImageView[]{
                    tr1, tr2, tr3, tr4, tr5, tr6, tr7, tr8, tr9, tr10, tr11, tr12, tr13, tr14, tr15, tr16, tr17, tr18, tr19, tr20, tr21, tr22, tr23, tr24
            };
        }
        for (int i = 0; i < 24; i++) {
            arrayImageView[i].setImage(setTheImage(logic.getSlot(i)));
        }
        for (int i = 12; i < 24; i++) {
            arrayImageView[i].setRotate(180);
        }
    }

    public Image setTheImage(Slot tr) {
        if (tr.isBlack()) {
            return picturesBlack.get(tr.getCount());
        } else {
            return picturesWhite.get(tr.getCount());
        }
    }

    private void setMapDice() throws FileNotFoundException {
        dices = new HashMap<>();
        for (int i = 1; i <= 6; i++)
        {
            dices.put(i, new Image(new FileInputStream(String.format("src/sample/resources/diceSide%d.jpg",i))));
        }
    }

    private void setMapImages() throws FileNotFoundException {
        picturesBlack = new HashMap<>();
        picturesWhite = new HashMap<>();
        for (int i = 0; i <= 15; i++)
        {
            picturesBlack.put(i, new Image(new FileInputStream(String.format("src/sample/resources/checkSetBlack%d.png",i))));
            picturesWhite.put(i, new Image(new FileInputStream(String.format("src/sample/resources/checkSetWhite%d.png",i))));
        }

    }

    private void setValue(int num) {
        if (count < tempStep && canMove()) {
            boolean isOk = false;
            if (triangleNumberFirst == null) {
                triangleNumberFirst = num;
                //подсвечивать ход
            } else {
                triangleNumberLast = num;
                for (int i = 0; i < step.size(); i++) {
                    if (triangleNumberLast == 1000 && logic.canThrowAway(isWhite)) {
                        if (24 - triangleNumberFirst <= step.get(i) && isWhite || !isWhite && triangleNumberFirst > 5 && triangleNumberFirst < 12) {
                            isOk = true;
                            step.remove(i);
                            break;
                        }
                    }
                    if (triangleNumberLast - triangleNumberFirst == step.get(i) || !isWhite &&
                            (24 - triangleNumberFirst + triangleNumberLast == step.get(i))) {
                        isOk = true;
                        if (isTurnFromHead && (triangleNumberFirst == 0 && isWhite || triangleNumberFirst == 12 && !isWhite) && !isEquals) {
                            isOk = false;
                            break;
                        }
                        if (logic.getSlot(triangleNumberLast).getColor() == logic.getSlot(triangleNumberFirst).getColor() ||
                                logic.getSlot(triangleNumberLast).getColor() == Color.NULL) {
                            step.remove(i);
                            break;
                        } else isOk = false;
                    }
                }

                if (isOk && logic.step(isWhite, triangleNumberFirst, triangleNumberLast)) {
                    count++;
                    if (triangleNumberFirst == 0 && isWhite || triangleNumberFirst == 12 && !isWhite)
                        isTurnFromHead = true;
                }

                triangleNumberLast = null;
                triangleNumberFirst = null;

                if (count == tempStep) {
                    isTurnFromHead = false;
                    count = 0;
                    isWhite = !isWhite;
                    if (isWhite) {
                        turn = "White turn";
                    } else {
                        turn = "Black turn";
                    }
                    switch (logic.isWinGame()) {
                        case NULL:
                            diceRoller.setDisable(false);
                            step.clear();
                            break;
                        case BLACK:
                            diceRoller.setText("Black wins");
                            break;
                        case WHITE:
                            diceRoller.setText("White wins");
                            break;
                    }
                }
            }
        }
        draw();
    }

    private boolean canMove() {
        for (int element : step) {
            for (int i = 0; i < 24 - element; i++) {
                if (isWhite) {
                    if (i > 17 && i + element > 23) return true; // out
                    if (!logic.getSlot(i + element).isBlack()) {
                        return true;
                    }
                } else {
                    if (i + element <= 23 && i > 11) { // lower row
                        if (!logic.getSlot(i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i < 11 - element) { // upper row
                        if (!logic.getSlot(i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i > 17 && 24 - i + element < 6) { // from lower to upper row (border)
                        if (!logic.getSlot(24 - i + element).isWhite()) {
                            return true;
                        }
                    }
                    if (i > 5 && i + element > 11) return true; // out
                }
            }
        }
        return false;
    }

    @FXML
    void setValue1() {
        setValue(0);
    }

    @FXML
    void setValue2() {
        setValue(1);
    }

    @FXML
    void setValue3() {
        setValue(2);
    }

    @FXML
    void setValue4() {
        setValue(3);
    }

    @FXML
    void setValue5() {
        setValue(4);
    }

    @FXML
    void setValue6() {
        setValue(5);
    }

    @FXML
    void setValue7() {
        setValue(6);
    }

    @FXML
    void setValue8() {
        setValue(7);
    }

    @FXML
    void setValue9() {
        setValue(8);
    }

    @FXML
    void setValue10() {
        setValue(9);
    }

    @FXML
    void setValue11() {
        setValue(10);
    }

    @FXML
    void setValue12() {
        setValue(11);
    }

    @FXML
    void setValue13() {
        setValue(12);
    }

    @FXML
    void setValue14() {
        setValue(13);
    }

    @FXML
    void setValue15() {
        setValue(14);
    }

    @FXML
    void setValue16() {
        setValue(15);
    }

    @FXML
    void setValue17() {
        setValue(16);
    }

    @FXML
    void setValue18() {
        setValue(17);
    }

    @FXML
    void setValue19() {
        setValue(18);
    }

    @FXML
    void setValue20() {
        setValue(19);
    }

    @FXML
    void setValue21() {
        setValue(20);
    }

    @FXML
    void setValue22() {
        setValue(21);
    }

    @FXML
    void setValue23() {
        setValue(22);
    }

    @FXML
    void setValue24() {
        setValue(23);
    }

    @FXML
    private void outThrow() {
        setValue(1000);
    }

    public void setLogic(BackGammon logic) {
        this.logic = logic;
    }
}
