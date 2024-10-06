package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeNameController extends Controller implements Initializable {
    EnterpriseDAO enterDAO;
    @FXML
    private Button changeBtt;

    @FXML
    private TextField nameTField;

    @FXML
    private PasswordField passField;

    @FXML
    void onChangeClick(ActionEvent event) {
        if(nameTField.getText().isEmpty() || passField.getText().isEmpty()) {
            AlertUtil.showAlert("Error de introducción", "Alguno de los campos está vacío", Alert.AlertType.ERROR);
        } else {
            String pass = DigestUtils.sha256Hex(passField.getText());
            if (nameTField.getText().equals(DatabaseManager.enterp.getEnter_name())){
                AlertUtil.showAlert("Error de nombre", "Ya estás usando este nombre", Alert.AlertType.ERROR);
            } else {
                if (!pass.equals(DatabaseManager.enterp.getEnter_passwd())){
                    AlertUtil.showAlert("Error de contraseña", "Las contraseñas no coinciden en la base de datos", Alert.AlertType.ERROR);
                } else {
                    Enterprise enter = new Enterprise(DatabaseManager.enterp.getId(), nameTField.getText(), DatabaseManager.enterp.getEnter_passwd());
                    //Antes de cambiar datos en la aplicación, uso una copia del objeto. Es necesario crear un nuevo objeto porque, de lo contrario, modificaría la dirección de memoria
                    //Además, en el atributo del nombre pongo el nombre nuevo
                    if (enterDAO.updateEnterprise(enter)==1){
                        AlertUtil.showAlert("Cambio de nombre", "Nombre de cuenta cambiada correctamente", Alert.AlertType.INFORMATION);
                        DatabaseManager.enterp = enter; //Ahora sí, se iguala al nuevo usuario, ya que se ha cambiado sin problema
                        ((Stage)this.changeBtt.getScene().getWindow()).close();
                    } else {
                        AlertUtil.showAlert("Error de cuenta", "Ya hay un usuario con ese nombre de cuenta", Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterDAO = new EnterpriseDAO();
    }
}
