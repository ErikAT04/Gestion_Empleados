package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.DatabaseManager;
import com.erikat.gestion_emples.Utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

//CONTROLADOR QUE AFECTA AL FICHERO 'changePasswd.fxml'

public class ChangePassController extends Controller {
    @FXML
    private Button changeBtt;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;

    @FXML
    void onChangeClic() {
        /*
        Condiciones a cumplir:
            1-Los campos están rellenos
            2-Las contraseñas no deben ser las mismas
            3-La contraseña nueva debe cumplir los requisitos de validación (La anterior ya lo cumple)
            4-La contraseña en el campo "Antigua Contraseña" (oldPasTField) es igual a la contraseña antes de cambiarla
         */
        String newPass = DigestUtils.sha256Hex(newPassField.getText());
        String oldPass = DigestUtils.sha256Hex(oldPassField.getText());
        if (newPassField.getText().isEmpty() || oldPassField.getText().isEmpty()) { //Campos vacíos
            AlertUtil.showAlert("Error de introducción", "Todos los campos deben estar rellenados", Alert.AlertType.ERROR);
        } else if (!Validator.validatePassword(newPassField.getText(), DatabaseManager.enterp.getEnter_name())) {
            AlertUtil.showAlert("Error de contraseña", "La contraseña nueva no cumple los requisitos necesarios", Alert.AlertType.ERROR);
        } else {
            if (oldPass.equals(DatabaseManager.enterp.getEnter_passwd())) { //La antigua contraseña es correcta
                if (newPassField.getText().equals(oldPassField.getText())) { //Si se ha escrito la misma contraseña en ambos campos
                    AlertUtil.showAlert("Error de contraseñas", "La nueva contraseña no puede ser la misma que la anterior", Alert.AlertType.ERROR);
                } else {
                    Enterprise enterp = new Enterprise(DatabaseManager.enterp.getId(), DatabaseManager.enterp.getEnter_name(), newPass); //Creo un nuevo objeto de tipo Enterprise (no sirve igualar porque copiaría la dirección de memoria
                    if (EnterpriseDAO.updateEnterprise(enterp) == 1) { //Si se ha realizado el cambio correctamente (se ha actualizado una linea)
                        AlertUtil.showAlert("Cambio de contraseña", "Contraseña cambiada correctamente", Alert.AlertType.INFORMATION);
                        DatabaseManager.enterp = enterp; //Ahora sí, se guarda el cambio en el atributo estçatico
                        ((Stage) this.changeBtt.getScene().getWindow()).close(); //Se cierra la ventana actual
                    } else { //Si no se ha hecho bien el cambio
                        AlertUtil.showAlert("Error de cambio de contraseña", "Ha ocurrido un error inesperado", Alert.AlertType.ERROR);
                    }
                }
            } else { //Si la antigua contraseña no es la misma que la de la empresa
                AlertUtil.showAlert("Error de contraseña", "La antigua contraseña es incorrecta\nInténtelo de nuevo.", Alert.AlertType.ERROR);
            }
        }
    }
}

