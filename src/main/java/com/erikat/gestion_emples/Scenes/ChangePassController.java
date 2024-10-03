package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.DatabaseManager;
import com.erikat.gestion_emples.Utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassController extends Controller implements Initializable {
    EnterpriseDAO enterDAO;
    @FXML
    private Button changeBtt;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;

    @FXML
    void onChangeClic(ActionEvent event) {
        String newPass = DigestUtils.sha256Hex(newPassField.getText());
        String oldPass = DigestUtils.sha256Hex(oldPassField.getText());
        if (newPassField.getText().isEmpty() || oldPassField.getText().isEmpty()) {
            AlertUtil.showAlert("Error de introducción", "Todos los campos deben estar rellenados", Alert.AlertType.ERROR);
        } else if (!Validator.validatePassword(newPassField.getText(), DatabaseManager.enterp.getEnter_name())) {
            AlertUtil.showAlert("Error de contraseña", "La contraseña nueva no cumple los requisitos necesarios", Alert.AlertType.ERROR);
        } else {
            if (oldPass.equals(DatabaseManager.enterp.getEnter_passwd())) {
                if (newPassField.getText().equals(oldPassField.getText())) {
                    AlertUtil.showAlert("Error de contraseñas", "La nueva contraseña no puede ser la misma que la anterior", Alert.AlertType.ERROR);
                } else {
                    Enterprise enterp = new Enterprise(DatabaseManager.enterp.getId(), DatabaseManager.enterp.getEnter_name(), newPass);
                    if (enterDAO.updateEnterprise(enterp) == 1) {
                        AlertUtil.showAlert("Cambio de contraseña", "Contraseña cambiada correctamente", Alert.AlertType.INFORMATION);
                        DatabaseManager.enterp = enterp;
                        ((Stage) this.changeBtt.getScene().getWindow()).close();
                    } else {
                        AlertUtil.showAlert("Error de cambio de contraseña", "Ha ocurrido un error inesperado", Alert.AlertType.ERROR);
                    }
                }
            } else {
                AlertUtil.showAlert("Error de contraseña", "La antigua contraseña es incorrecta\nInténtelo de nuevo.", Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterDAO = new EnterpriseDAO();
    }
}

