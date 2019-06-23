module newsline {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires retrofit;
    requires gson;
    requires converter.gson;
    requires java.sql;


    opens newsline.ui;
    opens newsline.model to gson;
    opens newsline to javafx.fxml;

    exports newsline;
}

