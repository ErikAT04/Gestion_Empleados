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

//CONTROLADOR QUE AFECTA AL FICHERO 'changeName.fxml'

public class ChangeNameController extends Controller implements Initializable {
    EnterpriseDAO enterDAO; //Necesita acceder a la clase DAO de Empresas
    @FXML
    private Button changeBtt;

    @FXML
    private TextField nameTField;

    @FXML
    private PasswordField passField;

    @FXML
    void onChangeClick(ActionEvent event) {
        /*
        Tiene que cumplir ciertas condiciones:
            1-Los campos no pueden estar vacíos
            2-El nombre de usuario no puede ser el mismo que el que intentamos poner
            3-La contraseña tiene que ser correcta para hacer el cambio
            4-El nombre de usuario no puede repetirse en la base de datos (Unique)
         */
        if(nameTField.getText().isEmpty() || passField.getText().isEmpty()) { //Campos vacíos
            AlertUtil.showAlert("Error de introducción", "Alguno de los campos está vacío", Alert.AlertType.ERROR);
        } else {
            if (nameTField.getText().equals(DatabaseManager.enterp.getEnter_name())){ //El nombre coincide con el actual
                AlertUtil.showAlert("Error de nombre", "Ya estás usando este nombre", Alert.AlertType.ERROR);
            } else {
                String pass = DigestUtils.sha256Hex(passField.getText()); //Encripta la contraseña pasada
                if (!pass.equals(DatabaseManager.enterp.getEnter_passwd())){ //Las contraseñas encriptadas no coinciden
                    AlertUtil.showAlert("Error de contraseña", "Las contraseñas no coinciden en la base de datos", Alert.AlertType.ERROR);
                } else {
                    Enterprise enter = new Enterprise(DatabaseManager.enterp.getId(), DatabaseManager.enterp.getEnter_name(), DatabaseManager.enterp.getEnter_passwd());
                    //Antes de cambiar datos en la aplicación, uso una copia del objeto. Es necesario crear un nuevo objeto porque, de lo contrario, modificaría la dirección de memoria
                    enter.setEnter_name(nameTField.getText()); //Se añade el nombre nuevo a la copia
                    if (enterDAO.updateEnterprise(enter)==1){ //Si hay cambios en la base de datos (si se ha cambiado correctamente):
                        AlertUtil.showAlert("Cambio de nombre", "Nombre de cuenta cambiada correctamente", Alert.AlertType.INFORMATION);
                        DatabaseManager.enterp = enter; //Ahora sí, se iguala al nuevo usuario, ya que se ha cambiado sin problema
                        ((Stage)this.changeBtt.getScene().getWindow()).close();
                    } else { //La única forma de llegar a este else es si ya está ese nombre de usuario en la cuenta
                        AlertUtil.showAlert("Error de cuenta", "Ya hay un usuario con ese nombre de cuenta", Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //Inicializa el controlador para iniciar el objeto DAO
        enterDAO = new EnterpriseDAO();
    }
}
