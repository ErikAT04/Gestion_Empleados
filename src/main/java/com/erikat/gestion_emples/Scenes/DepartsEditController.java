package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepartsEditController extends Controller {

    @FXML
    private Button actionBtt;

    @FXML
    private Label contextLbl;

    @FXML
    private TextField locTField;

    @FXML
    private TextField nameTField;

    private Depart dpt;

    private boolean isEditing = false;

    DepartDAO dptDAO;

    DepartsMainController prevController;

    @FXML
    void onActionClick(ActionEvent event) {
        if(locTField.getText().isEmpty() || nameTField.getText().isEmpty() ) {
            AlertUtil.showAlert("Error de datos", "Todos los campos deben estar rellenados", Alert.AlertType.ERROR);
        } else {
            String location = locTField.getText();
            String name = nameTField.getText();
            if (dptDAO.lookIfDepartExists(name, location)){
                AlertUtil.showAlert("Error de opción", "Ya existe un departamento con esos datos", Alert.AlertType.ERROR);
            } else {
                if (isEditing){
                    dpt.setDept_location(location);
                    dpt.setDept_name(name);
                    if (dptDAO.modDept(dpt) == 1){
                        AlertUtil.showAlert("Actualización de departamento", "Acción realizada correctamente", Alert.AlertType.INFORMATION);
                        prevController.tableRefresh();
                        ((Stage)this.actionBtt.getScene().getWindow()).close();
                    } else {
                        AlertUtil.showAlert("Error de actualización", "Ha habido un error en la base de datos", Alert.AlertType.ERROR);
                    }
                } else {
                    if (dptDAO.addDept(name, location) == 1){
                        AlertUtil.showAlert("Introducción de departamento", "Departamento añadido correctamente", Alert.AlertType.INFORMATION);
                        prevController.tableRefresh();
                        ((Stage)this.actionBtt.getScene().getWindow()).close();
                    } else {
                        AlertUtil.showAlert("Error de actualización", "Ha habido un error en la base de datos", Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }
    void load(Depart dpt){
        this.dpt = dpt;
        isEditing = true;
        this.actionBtt.setText("Editar");
        this.contextLbl.setText("Edición de departamento");
        this.locTField.setText(dpt.getDept_location());
        this.nameTField.setText(dpt.getDept_name());
    }
    void getPrevController(DepartsMainController controller){
        this.prevController = controller; //El objetivo principal de la función es pasar el controlador de la tabla para poder refrescar la tabla
        dptDAO = new DepartDAO(); //Como todos los métodos que abren el fxml usan esta función, se puede crear aquí el objeto
    }
}
