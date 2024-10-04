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

//CONTROLADOR QUE AFECTA AL FICHERO 'changeName.fxml'

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
    void onDepartsClick(ActionEvent event) {
        SceneUtils.changeScene("departsMainView.fxml", (Stage) this.dptBtt.getScene().getWindow());
    }

    @FXML
    void onEmployeeClick(ActionEvent event) {
        SceneUtils.changeScene("empsMainView.fxml", (Stage) this.empBtt.getScene().getWindow());
    }

    @FXML
    void onOthersClick(ActionEvent event) {
        OthersController controller = (OthersController) SceneUtils.changeSceneNewStage("others.fxml");
        controller.load((Stage) this.othersBtt.getScene().getWindow()); //Paso este Stage, ya que puede que el próximo controlador se encargue de cerrar todas las ventanas
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterpLbl.setText(enterpLbl.getText() + DatabaseManager.enterp.getEnter_name());
    }
}
