package com.csc311hw.csc311hw2;

import com.google.gson.annotations.SerializedName;

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

/*    public Guess(String correctShape) {
        this.correctShape = correctShape;
        guessNumber = guesses;
        guesses++;
    }*/

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

    public String getGuessedShape() {
        return guessedShape;
    }

    public void setGuessedShape(String guessedShape) {
        this.guessedShape = guessedShape;
    }

    public int getGuessNumber() {
        return guessNumber;
    }

    public String getCorrectShape() {
        return correctShape;
    }

    public static int getGuesses() {
        return guesses;
    }

    public static void setGuesses(int guesses) {
        Guess.guesses = guesses;
    }

    public static int getCorrectGuesses() {
        return correctGuesses;
    }

    public static void setCorrectGuesses(int correctGuesses) {
        Guess.correctGuesses = correctGuesses;
    }

    public boolean isRightGuess() {
        return rightGuess;
    }

    public void setRightGuess(boolean rightGuess) {
        this.rightGuess = rightGuess;
    }

    public static void reset(){
        guesses = 0;
        correctGuesses = 0;
    }

    @Override
    public String toString() {
        return guessNumber +
                ", correct: " + correctShape.toUpperCase() +
                ", guessed: " + guessedShape.toUpperCase();
    }
}
