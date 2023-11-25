module com.example.cmpt305milestone2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.jetbrains.annotations;
    requires java.net.http;
    requires atlantafx.base;
    requires java.sql;
    requires com.esri.arcgisruntime;

    opens com.Github.cmpt305milestone2 to javafx.fxml;
    exports com.Github.cmpt305milestone2;
    exports com.Github.cmpt305milestone2.DAO;
    opens com.Github.cmpt305milestone2.DAO to javafx.fxml;
    exports com.Github.cmpt305milestone2.Data;
    opens com.Github.cmpt305milestone2.Data to javafx.fxml;
}