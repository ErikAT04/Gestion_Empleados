package com.erikat.gestion_emples.Utils;

import com.erikat.gestion_emples.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {
    public static void changeScene(String fileName, Stage stage){
        FXMLLoader loader = new FXMLLoader(R.getUI(fileName));
        try {
            stage.setScene(new Scene(loader.load()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Controller changeSceneNewStage(String fileName){ //Devuelve el controlador del objeto si así lo pide la función
        FXMLLoader loader = new FXMLLoader(R.getUI(fileName));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            return loader.getController();
        }catch (IOException e){
            System.out.println(e.getCause());
        }
        return null;
    }
}
