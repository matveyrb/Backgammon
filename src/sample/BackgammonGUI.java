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
import java.util.Map;

public class BackgammonGUI {
    private Map<Integer, Image> picturesBlack;
    private Map<Integer, Image> picturesWhite;
    private Map<Integer, Image> dices;
    private BackGammon logic;
    private Integer triangleNumberFirst;
    private Integer triangleNumberLast;
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
        logic.rollDice();
        diceValue.setText("Your dices are: \n" + logic.getDiceOne() + ", " + logic.getDiceTwo() + "\n" + "Turn " + logic.getCurrentTurnColor().name());
        diceOne.setImage(dices.get(logic.getDiceOne()));
        diceTwo.setImage(dices.get(logic.getDiceTwo()));
        diceRoller.setDisable(true);
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
        if (logic.haveMove() && logic.canMove()) {
            if (triangleNumberFirst == null && !logic.emptyCell(num)) {
                triangleNumberFirst = num;
            } else {
                triangleNumberLast = num;
                logic.move(triangleNumberFirst, triangleNumberLast);

                triangleNumberLast = null;
                triangleNumberFirst = null;
                if (logic.turnIsOver()) {
                    switch (logic.isWinGame()) {
                        case NULL:
                            diceRoller.setDisable(false);
                            logic.nextPlayer();
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
        else
        {
            logic.nextPlayer();
        }
        draw();
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
