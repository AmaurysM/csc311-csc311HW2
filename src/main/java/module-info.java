module com.csc311hw.csc311hw2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.healthmarketscience.jackcess;
    requires com.google.gson;
    requires MaterialFX;



    opens com.csc311hw.csc311hw2 to com.google.gson, javafx.fxml;
    exports com.csc311hw.csc311hw2;
    exports com.csc311hw.csc311hw2.model;
    opens com.csc311hw.csc311hw2.model to com.google.gson, javafx.fxml;
    exports com.csc311hw.csc311hw2.controllers;
    opens com.csc311hw.csc311hw2.controllers to com.google.gson, javafx.fxml;
}