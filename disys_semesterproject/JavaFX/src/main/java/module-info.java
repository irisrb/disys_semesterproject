module at.technikum.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires javafx.graphics;

    opens at.technikum.javafx to javafx.fxml;
    exports at.technikum.javafx;
}