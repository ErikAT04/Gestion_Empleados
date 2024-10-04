package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;


import java.net.URL;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'changeName.fxml'

public class LoginController extends Controller implements Initializable {

    @FXML
    private Label enterNameLbl;

    @FXML
    private TextField enterNameTField;

    @FXML
    private PasswordField enterPassField;

    @FXML
    private Label enterPasswdLbl;

    private EnterpriseDAO enterDAO;

    @FXML
    void onInfoClick(MouseEvent event) {
        String info = """
                Información de la contraseña:
                    1. Debe tener carácteres alfanuméricos, como mínimo con una mayúscula
                    2. Debe tener al menos 8 caracteres y, como mucho, 64
                    3. No debe ser igual que el nombre de la empresa.""";
        AlertUtil.showAlert("Información de contraseñas", info, Alert.AlertType.INFORMATION);
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        String enterpName = enterNameTField.getText();
        String enterpPass = enterPassField.getText();
        String passEncrypted;
        if (enterpName.isEmpty() || enterpPass.isEmpty()) { //Si alguno de los campos está vacío
            AlertUtil.showAlert("Error de inicio de sesión", "Los campos deben estar rellenados correctamente", Alert.AlertType.ERROR);
        } else{
            Enterprise enter = enterDAO.searchEnterp(enterpName);
            passEncrypted = DigestUtils.sha256Hex(enterpPass); //Se encripta la contraseña introducida
            if (enter==null){
                AlertUtil.showAlert("Error de usuario", "No se encuentra ningún usuario con ese nombre en la base de datos", Alert.AlertType.ERROR);
            } else if (passEncrypted.equals(enter.getEnter_passwd())){
                AlertUtil.showAlert("Usuario conectado", "Bienvenido de vuelta, " + enter.getEnter_name(), Alert.AlertType.INFORMATION);
                DatabaseManager.enterp = enter; //Guardo la empresa para usarla más tarde
                SceneUtils.changeSceneNewStage("mainView.fxml");
                ((Stage) this.enterNameLbl.getScene().getWindow()).close(); //Cierro el Stage actual
            }
        }
    }

    @FXML
    void onNameType(KeyEvent event) {
        int txtSize = enterNameTField.getText().length();
        if (txtSize > 255){
            enterNameTField.setText(enterNameTField.getText().substring(0, txtSize));
        } else if(txtSize < 255){
            enterNameLbl.setTextFill(Color.WHITE);
        } else {
            enterNameLbl.setTextFill(Color.RED);
        }
        enterNameLbl.setText(enterNameTField.getText().length()+"/255");
    }

    @FXML
    void onPassType(KeyEvent event) {
        int txtSize = enterPassField.getText().length();
        if (txtSize > 255){
            enterPassField.setText(enterPassField.getText().substring(0, txtSize));
        } else if(txtSize < 255){
            enterPasswdLbl.setTextFill(Color.WHITE);
        } else {
            enterPasswdLbl.setTextFill(Color.RED);
        }
        enterPasswdLbl.setText(enterPassField.getText().length() + "/255");
    }

    @FXML
    void onRegisterClick(ActionEvent event) {
        String enterpName = enterNameTField.getText();
        String enterpPass = enterPassField.getText();
        String passEncrypted;
        if (enterpName.isEmpty() || enterpPass.isEmpty()) { //Si alguno de los campos está vacío
            AlertUtil.showAlert("Error de creación", "Los campos deben estar rellenados correctamente", Alert.AlertType.ERROR);
        } else if (enterDAO.searchEnterp(enterpName)!=null){ //Si encuentra una cuenta con el nombre dado
            AlertUtil.showAlert("Error de creación de cuenta", "Ya existe una cuenta con ese nombre", Alert.AlertType.ERROR);
        } else if (!Validator.validatePassword(enterpPass, enterpName)){ //Comprueba todos los requisitos de la contraseña
            AlertUtil.showAlert("Error de contraseña", "La contraseña escrita no cumple los requisitos marcados en el icono '\uD83D\uDEC8'", Alert.AlertType.ERROR);
        } else {
            passEncrypted = DigestUtils.sha256Hex(enterpPass); //Encripto la contraseña
            if (enterDAO.registerEnterprise(enterpName, passEncrypted)==1){ //Si se actualiza una fila (es decir, si ha salido bien el insert)
                AlertUtil.showAlert("Creación de cuenta", "Cuenta creada correctamente", Alert.AlertType.INFORMATION);
                DatabaseManager.enterp = enterDAO.searchEnterp(enterpName); //Se guarda la empresa seleccionada de forma estática, ya que va a ser utilizada más adelante.
                SceneUtils.changeSceneNewStage("mainView.fxml");
                ((Stage) this.enterNameLbl.getScene().getWindow()).close(); //Cierro el Stage actual
            } else {
                AlertUtil.showAlert("Error de creación", "Ha habido un error a la hora de crear su base de datos", Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         enterDAO = new EnterpriseDAO();
    }
}
