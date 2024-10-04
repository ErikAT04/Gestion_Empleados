package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.DAO.EmpleDAO;
import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.DatabaseManager;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'changeName.fxml'

public class OthersController extends Controller implements Initializable {

    DepartDAO dptDAO;
    EmpleDAO empDAO;
    EnterpriseDAO enterDAO;

    @FXML
    private Button changeEnterpName;

    @FXML
    private Button saveBtt;

    Stage prevStage;

    @FXML
    void onChangeNameClic(ActionEvent event) {

    }

    @FXML
    void onChangePasswdClick(ActionEvent event) {

    }

    @FXML
    void onEraseAccClic(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de borrado de cuenta");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de querer borrar tu cuenta? No la podrás recuperar");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if(enterDAO.deleteEnterprise(DatabaseManager.enterp.getId())==1){
                AlertUtil.showAlert("Información de cuenta", "Su cuenta ha sido borrada con éxito\nEsperamos volver a verle pronto", Alert.AlertType.INFORMATION);
                closeAll();
            } else {
                AlertUtil.showAlert("Error de eliminación", "Ha ocurrido un error, inténtelo de nuevo más tarde", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onLogOutClic(ActionEvent event) {
        AlertUtil.showAlert("Cierre de sesión", "¡Vuelva pronto!", Alert.AlertType.INFORMATION);
        closeAll();
    }

    @FXML
    void onSaveDataClic(ActionEvent event) {
        DirectoryChooser dChooser = new DirectoryChooser();
        dChooser.setTitle("Elige la ubicación para guardar el archivo");
        File file = dChooser.showDialog((Stage)this.saveBtt.getScene().getWindow());
        if (file != null) {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file + "/dbAt"+LocalDate.now()+".txt", false));
                bw.write("Lista completa a fecha de " + LocalDate.now());
                bw.newLine();
                bw.write("Empresa: " + DatabaseManager.enterp.getEnter_name());
                bw.newLine();
                bw.newLine();
                bw.write("Departamentos:");
                bw.newLine();
                for (Depart depart: dptDAO.listDepts()){
                    bw.write(depart.toString());
                    bw.newLine();
                }
                bw.newLine();
                bw.write("Empleados:");
                bw.newLine();
                for (Emple e: empDAO.listEmple()){
                    bw.write(e.toString());
                    bw.newLine();
                }
                bw.close();
                AlertUtil.showAlert("Archivo guardado", "Archivo guardado correctamente.\nCompruébelo en el directorio elegido", Alert.AlertType.INFORMATION);
            }catch (IOException e){
                AlertUtil.showAlert("Error de Creación de Archivos", "Algo fue mal, inténtelo de nuevo más tarde", Alert.AlertType.ERROR);
            }
        }
    }
    public void load(Stage prevStage){
        this.prevStage = prevStage;
    }
    public void closeAll(){
        DatabaseManager.enterp = null;
        prevStage.close();
        ((Stage)this.changeEnterpName.getScene().getWindow()).close();
        SceneUtils.changeSceneNewStage("loginView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empDAO = new EmpleDAO();
        dptDAO = new DepartDAO();
        enterDAO = new EnterpriseDAO();
    }
}
