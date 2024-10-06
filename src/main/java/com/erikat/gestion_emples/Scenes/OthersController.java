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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'others.fxml'

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
    void onChangeNameClic(ActionEvent event) { //Al dar al botón de cambiar nombre de usuario, se abre la ventana de cambio de nombre
        SceneUtils.changeSceneNewStage("changeName.fxml", "Cambio de Nombre de Empresa"); //Abre una nueva ventana e introduce la escena del fxml de cambio de nombre
    }

    @FXML
    void onChangePasswdClick(ActionEvent event) { //Al dar al botón de cambiar contraseña, se abre su respectiva ventana
        SceneUtils.changeSceneNewStage("changePasswd.fxml", "Cambio de Contraseña"); //Abre una nueva ventana e introduce la escena del fxml de cambio de contraseña
    }

    @FXML
    void onEraseAccClic(ActionEvent event) { //Al dar a la opción de borrar cuenta, se hace lo siguiente
        //Se crea una nueva alerta (Sin el AlertUtils ya que esta además pide la acción de un botón en la confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de borrado de cuenta");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de querer borrar tu cuenta? No la podrás recuperar");
        Optional<ButtonType> result = alert.showAndWait(); //Creamos una variable de botón que guarde la elección del usuario
        if (result.get() == ButtonType.OK) { //Si su elección ha sido borrar la cuenta:
            if(enterDAO.deleteEnterprise(DatabaseManager.enterp.getId())==1){ //Si la cuenta se ha borrado bien (Solo se ha borrado una línea):
                AlertUtil.showAlert("Información de cuenta", "Su cuenta ha sido borrada con éxito\nEsperamos volver a verle pronto", Alert.AlertType.INFORMATION);
                closeAll(); //Cierrra todas las ventanas y vuelve al inicio de sesión
            } else {
                AlertUtil.showAlert("Error de eliminación", "Ha ocurrido un error, inténtelo de nuevo más tarde", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onLogOutClic(ActionEvent event) { //Si se elige la opción de cerrar sesión:
        AlertUtil.showAlert("Cierre de sesión", "¡Vuelva pronto!", Alert.AlertType.INFORMATION);
        closeAll(); //Cierra las venanas y vuelve al inicio de sesión
    }

    @FXML
    void onSaveDataClic(ActionEvent event) { //Si se elige la opción de guardar los datos de la empresa:
        DirectoryChooser dChooser = new DirectoryChooser(); //Crea un objeto de tipo DirectoryChooser (un objeto que abre una ventana para poder elegir una ruta)
        dChooser.setTitle("Elige la ubicación para guardar el archivo");
        File file = dChooser.showDialog((Stage)this.saveBtt.getScene().getWindow()); //Guardo en un objeto de Archivo la ruta especificada por el DirectoryChooser
        if (file != null) { //Si el archivo no es nulo (Si se ha seleccionado un directorio)
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file + "/dbAt"+LocalDate.now()+".txt", false)); //Crea un BufferedWriter para escribir la información. Append = false para que, si el archivo ya existe, no se sobrescriba
                bw.write("Lista completa a fecha de " + LocalDate.now()); //Añade la fecha actual
                bw.newLine();
                bw.write("Empresa: " + DatabaseManager.enterp.getEnter_name()); //Nombre de la empresa
                bw.newLine();
                bw.newLine();
                bw.write("Departamentos:"); //Departamentos
                bw.newLine();
                for (Depart depart: dptDAO.listDepts()){ //ForEach de los departamentos
                    bw.write(depart.toString());
                    bw.newLine();
                    //Introduce uno y salta de línea
                }
                bw.newLine(); //Otro salto de línea (mera estética)
                bw.write("Empleados:");
                bw.newLine();
                for (Emple e: empDAO.listEmple()){ //ForEach de los empleados
                    bw.write(e.toString());
                    bw.newLine();
                    //Introduce uno y salta de línea
                }
                bw.close(); //Se cierra el writer
                AlertUtil.showAlert("Archivo guardado", "Archivo guardado correctamente.\nCompruébelo en el directorio elegido", Alert.AlertType.INFORMATION); //Salta una alerta informando de que todo ha ido bien
            }catch (IOException e){
                AlertUtil.showAlert("Error de Creación de Archivos", "Algo fue mal, inténtelo de nuevo más tarde", Alert.AlertType.ERROR); //Si hay una excepción por el archivo, salta este error
            }
        }
    }
    public void load(Stage prevStage){ //Carga los datos del anterior stage si este se lo pasa
        this.prevStage = prevStage;
    }
    public void closeAll(){ //Función llamada desde el cierre de sesión y el borrado de cuenta
        DatabaseManager.enterp = null; //Pone en nulo la empresa
        prevStage.close(); //Cierra el stage de las tablas
        ((Stage)this.changeEnterpName.getScene().getWindow()).close(); //Cierra este stage
        SceneUtils.changeSceneNewStage("loginView.fxml", "Inicio de Sesión"); //Abre en un nuevo stage el inicio de sesión
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empDAO = new EmpleDAO();
        dptDAO = new DepartDAO();
        enterDAO = new EnterpriseDAO();
    }
}
