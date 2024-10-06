package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.DatabaseManager;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'mainView.fxml'

public class MainController extends Controller implements Initializable {

    @FXML
    private Button dptBtt;

    @FXML
    private Button empBtt;

    @FXML
    private Button othersBtt;

    @FXML
    private Label enterpLbl;


    @FXML
    void onDepartsClick(ActionEvent event) { //Al dar a este botón, se cambia a la escena de departamentos
        SceneUtils.changeScene("departsMainView.fxml", (Stage) this.dptBtt.getScene().getWindow(), "Departamentos"); //Pasa como parámetros el nombre del archivo, la ventana actual (casteada a Stage) y el nombre que debe tener el Stage
    }

    @FXML
    void onEmployeeClick(ActionEvent event) { //Al dar a este botón, se cambia a la escena de empleados
        SceneUtils.changeScene("empsMainView.fxml", (Stage) this.empBtt.getScene().getWindow(), "Empleados"); //Pasa como parámetros el nombre del fxml, la ventana actual (casteada a Stage) y el nombre que ha de tener
    }

    @FXML
    void onOthersClick(ActionEvent event) { //Al dar a este botón, se abre una nueva ventana con la escena de Otras Opciones
        OthersController controller = (OthersController) SceneUtils.changeSceneNewStage("others.fxml", "Otras opciones"); //Pasa como parámetro el nombre del fxml
        controller.load((Stage) this.othersBtt.getScene().getWindow()); //Paso este Stage, ya que puede que el próximo controlador se encargue de cerrar todas las ventanas
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterpLbl.setText(enterpLbl.getText() + DatabaseManager.enterp.getEnter_name()); //Añade al texto de "Nombre de empresa:" el nombre de la empresa introducido
    }
}
