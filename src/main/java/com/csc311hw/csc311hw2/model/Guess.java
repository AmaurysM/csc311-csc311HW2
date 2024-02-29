package com.csc311hw.csc311hw2.model;

import com.google.gson.annotations.SerializedName;
/**
 * This class contains methods to store grade information.
 * @author Amaurys De Los Santos Mendez
 */
public class Guess {
    private static int guesses = 0;
    private static int correctGuesses = 0;
    private boolean rightGuess;

    @SerializedName("GuessNumber")
    private int guessNumber;
    @SerializedName("CorrectShape")
    private String correctShape;
    @SerializedName("GuessedShape")
    private String guessedShape;

    /**
     * Creates a guess containing the shape and the shape guessed.
     * @param correctShape Correct shape of the guess.
     * @param guessedShape Guessed shape of the guess.
     */
    public Guess(String correctShape, String guessedShape) {
        this.correctShape = correctShape;
        this.guessedShape = guessedShape;
        guesses++;
        guessNumber = guesses;

        if(correctShape.equals(guessedShape)){
            this.rightGuess = true;
            correctGuesses++;
        }
    }
    /**
     * Gives the guessed shape.
     * @return String guessed shape.
     */
    public String getGuessedShape() {
        return guessedShape;
    }

    /**
     * Sets the guessed shape.
     * @param guessedShape The new shape of the assignment as a String.
     */
    public void setGuessedShape(String guessedShape) {
        this.guessedShape = guessedShape;
    }

    /**
     * Gives the turn of the guess.
     * @return int turn of the guess.
     */
    public int getGuessNumber() {
        return guessNumber;
    }

    /**
     * Gives the correct shape of the guess.
     * @return String correct shape of the guess.
     */
    public String getCorrectShape() {
        return correctShape;
    }

    /**
     * Gives the number of guesses.
     * @return static int the number of guesses.
     */
    public static int getGuesses() {
        return guesses;
    }

    /**
     * Sets the number of guesses.
     * @param guesses The number of guesses .
     */
    public static void setGuesses(int guesses) {
        Guess.guesses = guesses;
    }

    /**
     * Gives the number of correct guesses.
     * @return int the number of correct guesses.
     */
    public static int getCorrectGuesses() {
        return correctGuesses;
    }

    /**
     * Sets the number of correct guesses.
     * @param correctGuesses The new number of correct guesses for this game.
     */
    public static void setCorrectGuesses(int correctGuesses) {
        Guess.correctGuesses = correctGuesses;
    }

    /**
     * Gives the true if the guess is right
     * false if the guess is wrong.
     * @return boolean If the guessed correctly or falsely.
     */
    public boolean isRightGuess() {
        return rightGuess;
    }

    /**
     * Sets the value that determines
     * if they guessed correctly or falsely.
     * @param rightGuess The new correct or false value.
     */
    public void setRightGuess(boolean rightGuess) {
        this.rightGuess = rightGuess;
    }

    /**
     * Resets the value of guesses and correct guesses.
     */
    public static void reset(){
        guesses = 0;
        correctGuesses = 0;
    }

    /**
     * This will show when the object is printed out.
     * @return String The correct guesses and the total guesses.
     */
    @Override
    public String toString() {
        return guessNumber +
                ", correct: " + correctShape.toUpperCase() +
                ", guessed: " + guessedShape.toUpperCase();
    }
}
