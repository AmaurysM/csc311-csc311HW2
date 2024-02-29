package com.csc311hw.csc311hw2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Runs the Guessing Game Application
 * @author Amaurys De Los Santos Mendez
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GuessingGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CSC311 HW2 Amaurys");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}