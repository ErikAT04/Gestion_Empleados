package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EnterpriseDAO;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

//CONTROLADOR QUE AFECTA AL FICHERO 'loginView.fxml'

public class LoginController extends Controller {

    @FXML
    private Label enterNameLbl;

    @FXML
    private TextField enterNameTField;

    @FXML
    private PasswordField enterPassField;

    @FXML
    private Label enterPasswdLbl;


    @FXML
    void onInfoClick() { //Al dar al icono de información aparece la siguiente alerta:
        String info = """
                Información de la contraseña:
                    1. Debe tener carácteres alfanuméricos, como mínimo con una mayúscula
                    2. Debe tener al menos 8 caracteres y, como mucho, 64
                    3. No debe ser igual que el nombre de la empresa.""";
        AlertUtil.showAlert("Información de contraseñas", info, Alert.AlertType.INFORMATION);
    }

    @FXML
    void onLoginClick() { //Al dar al botón de inicio de sesión salta esta función
        String enterpName = enterNameTField.getText();
        String enterpPass = enterPassField.getText();
        String passEncrypted;
        if (enterpName.isEmpty() || enterpPass.isEmpty()) { //Si alguno de los campos está vacío
            AlertUtil.showAlert("Error de inicio de sesión", "Los campos deben estar rellenados correctamente", Alert.AlertType.ERROR);
        } else{
            Enterprise enter = EnterpriseDAO.searchEnterp(enterpName);
            passEncrypted = DigestUtils.sha256Hex(enterpPass); //Se encripta la contraseña introducida
            if (enter==null){
                AlertUtil.showAlert("Error de usuario", "No se encuentra ningún usuario con ese nombre en la base de datos", Alert.AlertType.ERROR);
            } else if (passEncrypted.equals(enter.getEnter_passwd())){
                AlertUtil.showAlert("Usuario conectado", "Bienvenido de vuelta, " + enter.getEnter_name(), Alert.AlertType.INFORMATION);
                DatabaseManager.enterp = enter; //Guardo la empresa para usarla más tarde
                SceneUtils.changeSceneNewStage("mainView.fxml", "Ventana Principal");
                ((Stage) this.enterNameLbl.getScene().getWindow()).close(); //Cierro el Stage actual
            }
        }
    }

    @FXML
    void onNameType() { //Cuando se pulsa una tecla en el campo de nombre, se activa esta función
        int txtSize = enterNameTField.getText().length(); //Guarda el tamaño de texto en una variable
        if (txtSize > 64){
            enterNameTField.setText(enterNameTField.getText().substring(0, txtSize)); //Si el texto es más grande de lo señalado, se quita la última letra.
        } else if(txtSize < 64){ //Si es menor a 64, el texto aparecerá de color blanco
            enterNameLbl.setTextFill(Color.WHITE);
        } else { //Si es igual a 64, aparecerá en rojo
            enterNameLbl.setTextFill(Color.RED);
        }
        enterNameLbl.setText(enterNameTField.getText().length()+"/64"); //Edita el label que hay al lado del campo del nombre
    }

    @FXML
    void onPassType() { //Se activa la función cuando se pulsa una tecla en el campo de las contraseñas
        int txtSize = enterPassField.getText().length(); //Se guarda el tamaño de la contraseña en una variable de enteros
        if (txtSize > 64){ //Si el texto es máas grande que lo señalado, se quita una letra
            enterPassField.setText(enterPassField.getText().substring(0, txtSize));
        } else if(txtSize < 64){ //Si es menor a 64, el texto se verá de color blanco
            enterPasswdLbl.setTextFill(Color.WHITE);
        } else { //Si es igual a 64, se pondrá rojo
            enterPasswdLbl.setTextFill(Color.RED);
        }
        enterPasswdLbl.setText(enterPassField.getText().length() + "/64"); //Al final, se edita el label que tiene al lado el campo de la contraseña
    }

    @FXML
    void onRegisterClick() { //Acción de darle al botón de registrarse
        String enterpName = enterNameTField.getText();
        String enterpPass = enterPassField.getText();
        String passEncrypted;
        if (enterpName.isEmpty() || enterpPass.isEmpty()) { //Si alguno de los campos está vacío
            AlertUtil.showAlert("Error de creación", "Los campos deben estar rellenados correctamente", Alert.AlertType.ERROR);
        } else if (EnterpriseDAO.searchEnterp(enterpName)!=null){ //Si encuentra una cuenta con el nombre dado
            AlertUtil.showAlert("Error de creación de cuenta", "Ya existe una cuenta con ese nombre", Alert.AlertType.ERROR);
        } else if (!Validator.validatePassword(enterpPass, enterpName)){ //Comprueba todos los requisitos de la contraseña
            AlertUtil.showAlert("Error de contraseña", "La contraseña escrita no cumple los requisitos marcados en el icono '\uD83D\uDEC8'", Alert.AlertType.ERROR);
        } else {
            passEncrypted = DigestUtils.sha256Hex(enterpPass); //Encripto la contraseña
            if (EnterpriseDAO.registerEnterprise(enterpName, passEncrypted)==1){ //Si se actualiza una fila (es decir, si ha salido bien el insert)
                AlertUtil.showAlert("Creación de cuenta", "Cuenta creada correctamente", Alert.AlertType.INFORMATION);
                DatabaseManager.enterp = EnterpriseDAO.searchEnterp(enterpName); //Se guarda la empresa seleccionada de forma estática, ya que va a ser utilizada más adelante.
                SceneUtils.changeSceneNewStage("mainView.fxml", "Ventana Principal");
                ((Stage) this.enterNameLbl.getScene().getWindow()).close(); //Cierro el Stage actual
            } else {
                AlertUtil.showAlert("Error de creación", "Ha habido un error a la hora de crear su base de datos", Alert.AlertType.ERROR);
            }
        }
    }
}
