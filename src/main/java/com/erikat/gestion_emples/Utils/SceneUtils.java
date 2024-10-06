package com.erikat.gestion_emples.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

//SCENE UTILS: Contiene métodos estáticos relacionados a la creación y edición de escenas y escenarios
public class SceneUtils {
    public static void changeScene(String fileName, Stage stage, String title){ //Cambia la escena del escenario actual
        FXMLLoader loader = new FXMLLoader(R.getUI(fileName)); //Guarda la dirección del fxml el un loader
        try {
            stage.setScene(new Scene(loader.load())); //Carga el loader y lo introduce en la escena del Stage mandado por parámetro
            stage.setTitle(title);
        }catch (IOException e){
            System.out.println(e.getCause());
        }
    }
    public static Controller changeSceneNewStage(String fileName, String title){ //Devuelve el controlador del objeto si así lo pide la función
        FXMLLoader loader = new FXMLLoader(R.getUI(fileName)); //Guarda la dirección del fxml el un loader
        try {
            Scene scene = new Scene(loader.load()); //Carga el loader y lo introduce en una nueva escena
            Stage stage = new Stage(); //Crea el stage
            stage.setScene(scene); //Le introduce la escena
            stage.setTitle(title); //Le añade un título
            stage.initModality(Modality.APPLICATION_MODAL); //Le agrega la APPLICATION_MODAL, una propiedad que hace que no se pueda editar otra ventanas que no sean estas
            stage.show();
            return loader.getController(); //Devuelve el controlador de la nueva escena
        }catch (IOException e){
            System.out.println(e.getCause());
        }
        return null;
    }
}
