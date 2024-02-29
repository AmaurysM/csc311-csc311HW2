package com.csc311hw.csc311hw2.controllers;

import com.csc311hw.csc311hw2.DBController;
import com.csc311hw.csc311hw2.model.Guess;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This class contains methods to handle incoming requests from clients
 * interacting with the GuessingGame.fxml file.
 * @author Amaurys De Los Santos Mendez
 */
public class AppController {

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

    /**
     * Closes the application.
     */
    @FXML
    void exitMenuItem() {
        System.exit(0);
    }

    /**
     * Calls {@link DBController#clearAllDBData(Connection)} to the clear the database.
     * Clears the listView.
     * Calls {@link Guess#reset()} to clear the total number of guesses
     * and correct guesses in the {@link Guess} class.
     * Calls {@link #updateCounters()} to clear the textFields that contain
     * the total number of guesses and correct guesses.
     */
    @FXML
    void newGameMenuItem() {
        DBController.clearAllDBData(conn);
        listViewFromDB.getItems().clear();
        Guess.reset();
        updateCounters();
    }

    /**
     * Plays the game.
     * Once a user picks a choice, flips a coin (0 or 1).
     * Depending on the flip of that coin, we choose what is the correct shape is.
     * If the shape chosen by the flip of the coin matches the shape chosen by the player,
     * an animation is played depending on the shape chosen by the player.
     * GREEN if they chose correctly.
     * RED if they chose incorrectly.
     * Then calls {@link DBController#insertDataToDB(Connection, Guess)}
     * to add the {@link Guess} to the database.
     * Then calls {@link #updateCounters()} to update the text fields
     * containing the total guess counter and correct guesses.
     */
    @FXML
    void guessButton() {
        if(toggleGroup.getSelectedToggle() == null){
            return;
        }

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

    /**
     * Updates the listView using information from the database.
     * <p>
     * Calls {@link Guess#reset()} to reset the static guess information.
     * Calls {@link DBController#getData(Connection)} to get a list of guesses,
     * then inputs them into a linkedList({@link #guesses}), in this class.
     * This then clears the listView and adds the new linkedList of guesses.
     */
    @FXML
    void showGuessButton() {
        Guess.reset();
        guesses = DBController.getData(conn);
        listViewFromDB.getItems().clear();
        listViewFromDB.getItems().addAll(guesses);
    }

    /**
     * Updates the total guess counter and
     * correct guess counter being displayed on the application.
     */
    public void updateCounters(){
        totalGuessCount.setText(String.valueOf(Guess.getGuesses()));
        correctGuessCount.setText(String.valueOf(Guess.getCorrectGuesses()));
    }

    /**
     * Animates a shape to move, fade and change color.
     *
     * @param shape The type of shape to be animated.
     * @param same If it was correct or not.
     *             If it was correct it will change to the color green
     *             and if it is false it will be red.
     */
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

    /**
     * Called at start.
     * Reads the database and puts them in a linkedList.
     * The call {@link #updateCounters()} to read this linkedList
     * and update the displayed information.
     *
     */
    public void fillAppWithData(){
        Guess.reset();
        guesses = DBController.getData(conn);
        updateCounters();
    }

    /**
     * Creates the initial connection to the database.
     * Creates a toggleGroup.
     * Calls {@link #fillAppWithData()} to fill the listView
     * with information from the database.
     */
    public void initialize() {
        toggleGroup = new ToggleGroup();
        circleRadioButton.setToggleGroup(toggleGroup);
        rectangleRadioButton.setToggleGroup(toggleGroup);

        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fillAppWithData();


    }
}