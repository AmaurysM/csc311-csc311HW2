package com.csc311hw.csc311hw2;

import com.csc311hw.csc311hw2.model.Guess;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
/**
 * This class contains methods to handle interactions between
 * json files and the database, and the Database and AppController.
 * @author Amaurys De Los Santos Mendez
 */
public class DBController {

    /**
     * Creates a database file and a table.
     */
    public static void createDBAndTable(){
        String dbFilePath = ".//Guesses.accdb";
        String databaseURL = "jdbc:ucanaccess://" + dbFilePath;
        File dbFile = new File(dbFilePath);

        if (!dbFile.exists()) {
            try (Database db =
                         DatabaseBuilder.create(Database.FileFormat.V2010, new File(dbFilePath))) {
                System.out.println("The database file has been created.");
            } catch (IOException ioe) {
                ioe.printStackTrace(System.err);
            }
        }
        //
        // Create Table
        //
        try {

            Connection conn = DriverManager.getConnection(databaseURL);
            String sql;
            sql = "CREATE TABLE Guesses (GuessNumber INT, CorrectShape nvarchar(255), GuessedShape nvarchar(255))";
            Statement createTableStatement = conn.createStatement();
            createTableStatement.execute(sql);
            conn.commit();
            System.out.println("done");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Inserts a {@link Guess} into the database.
     * @param conn the database connection.
     * @param guess the grade to be inserted.
     */
    public static void insertDataToDB(Connection conn, Guess guess){
        String sql = "INSERT INTO Guesses (GuessNumber, CorrectShape, GuessedShape) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, guess.getGuessNumber());
            preparedStatement.setString(2, guess.getCorrectShape());
            preparedStatement.setString(3, guess.getGuessedShape());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Fills the database with a list of items.
     * @param conn Connection to the database.
     * @param list List of grades to be added to the database.
     */
    public static void insertListOfData(Connection conn, List<Guess> list){
        if(list == null){
            return;
        }

        clearAllDBData(conn);
        list.forEach(e->{
            insertDataToDB(conn,e);
        });
    }

    /**
     * Retrieves the information from the database and returns it as a linked-list.
     * @param conn Connection to the database.
     * @return LinkedList<Grade> A list of grades from the database.
     */
    public static LinkedList<Guess> getData(Connection conn){
        ResultSet result = null;
        LinkedList<Guess> listOfGrades = new LinkedList<>();
        try {
            String tableName = "Guesses";
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName);
        } catch (SQLException except) {
            except.printStackTrace();
        }

        while (true) {
            try {
                if (!result.next()) break;

                int guessNumber = result.getInt("GuessNumber");
                String correctShape = result.getString("CorrectShape");
                String guessedShape = result.getString("GuessedShape");

                Guess temp = new Guess(correctShape, guessedShape);
                listOfGrades.add(temp);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return listOfGrades;

    }


    /**
     * Clears all data from the database without effecting the json file.
     * @param conn Connection to the database.
     */
    public static void clearAllDBData(Connection conn){

        String sql = "DELETE FROM Guesses";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            int rowsDeleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
