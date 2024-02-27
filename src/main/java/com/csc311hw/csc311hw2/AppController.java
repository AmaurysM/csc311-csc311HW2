package com.csc311hw.csc311hw2;


import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.sql.*;
public class AppController implements Initializable {

    private Connection conn = null;
    private String dbFilePath = ".//Guesses.accdb";
    private String databaseURL = "jdbc:ucanaccess://" + dbFilePath;
    private LinkedList<Guess> guesses = null;

    private ToggleGroup toggleGroup;

    @FXML
    private Circle circle;

    @FXML
    private Rectangle rectangle;

    @FXML
    private VBox shapeVBox;

    @FXML
    private RadioButton circleRadioButton;

    @FXML
    private RadioButton rectangleRadioButton;

    @FXML
    private ListView<Guess> listViewFromDB;

    @FXML
    private TextField totalGuessCount;

    @FXML
    private TextField correctGuessCount;

    @FXML
    private Button guessButton;


    @FXML
    void exitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void newGameMenuItem(ActionEvent event) {
        DBController.clearAllDBData(conn);
        listViewFromDB.getItems().clear();
        Guess.reset();
        updateCounters();
    }


    @FXML
    void guessButton(ActionEvent event) {
        Random rand = new Random();
        int num = rand.nextInt(0,2);
        Guess guess;
        if(toggleGroup.getSelectedToggle().equals(circleRadioButton)){
            guess = new Guess( num == 1 ? "Circle":"Rectangle","Circle");
            DBController.insertDataToDB(conn,guess);
            shapeAnimation(circle,guess.isRightGuess());
        } else if (toggleGroup.getSelectedToggle().equals(rectangleRadioButton)) {
            guess = new Guess( num == 1 ? "Circle":"Rectangle","Rectangle");
            DBController.insertDataToDB(conn,guess);
            shapeAnimation(rectangle,guess.isRightGuess());
        }
        updateCounters();

    }

    @FXML
    void showGuessButton(ActionEvent event) {
        Guess.reset();
        guesses = DBController.getData(conn);
        listViewFromDB.getItems().clear();
        listViewFromDB.getItems().addAll(guesses);
    }

    public void fillAppWithData(){
        Guess.reset();
        guesses = DBController.getData(conn);
        updateCounters();
    }

    public void updateCounters(){
        totalGuessCount.setText(String.valueOf(Guess.getGuesses()));
        correctGuessCount.setText(String.valueOf(Guess.getCorrectGuesses()));
    }
    public void shapeAnimation(Shape shape, boolean same){
        guessButton.setDisable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),shape);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);

        FillTransition fillTransition =
                new FillTransition(Duration.seconds(2), shape);
        fillTransition.setToValue(same ? Color.GREEN:Color.RED);


        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), shape);
        translateTransition.setByX(100);

        ParallelTransition parallelTransition = new ParallelTransition( fillTransition, translateTransition,fadeTransition);
        parallelTransition.setCycleCount(2);
        parallelTransition.setAutoReverse(true);
        parallelTransition.setOnFinished((e)->{
            guessButton.setDisable(false);
        });
        parallelTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        circleRadioButton.setToggleGroup(toggleGroup);
        rectangleRadioButton.setToggleGroup(toggleGroup);

        // DBController.createDBAndTable();
        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fillAppWithData();


    }
}