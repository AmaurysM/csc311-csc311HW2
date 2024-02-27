module com.csc311hw.csc311hw2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.csc311hw.csc311hw2 to javafx.fxml;
    exports com.csc311hw.csc311hw2;
}