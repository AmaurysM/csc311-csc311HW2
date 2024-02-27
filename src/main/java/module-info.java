module com.csc311hw.csc311hw2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.healthmarketscience.jackcess;
    requires com.google.gson;


    opens com.csc311hw.csc311hw2 to com.google.gson, javafx.fxml;
    exports com.csc311hw.csc311hw2;
}