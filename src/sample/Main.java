package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.BackGammon;
import sample.model.Color;
import sample.model.GameBoard;

public class Main extends Application {

    Scene scene ;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        BackgammonGUI gameInterface = loader.getController();
        BackGammon logic = new GameBoard();

        gameInterface.setLogic(logic);

        gameInterface.draw();
        primaryStage.setTitle("Backgammon");
        primaryStage.setResizable(false);
        scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
