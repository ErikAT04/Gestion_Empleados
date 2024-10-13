package com.erikat.gestion_emples;

import com.erikat.gestion_emples.Utils.DatabaseManager;
import com.erikat.gestion_emples.Utils.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        DatabaseManager.conectar(); //Inicio la base de datos antes de nada
        FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio de sesión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}