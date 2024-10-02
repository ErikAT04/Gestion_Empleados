package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EmpleDAO;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmpsMainController extends Controller implements Initializable {

    EmpleDAO empDAO;
    @FXML
    private Button addBtt;

    @FXML
    private Button editBtt;

    @FXML
    private TableColumn<Emple, String> dniCol;

    @FXML
    private TableColumn<Emple, String> dptCol;

    @FXML
    private TableView<Emple> empTView;

    @FXML
    private TableColumn<Emple, LocalDate> joinCol;

    @FXML
    private TableColumn<Emple, String> nameCol;

    @FXML
    private TableColumn<Emple, String> salaryCol;

    @FXML
    private TableColumn<Emple, String> surnameCol;

    Stage thisStage;

    @FXML
    void onAddClic(ActionEvent event) {
        EmpsEditController controller = (EmpsEditController) SceneUtils.changeSceneNewStage("empsEditMenu.fxml");
        controller.getPrevController(this);
    }

    @FXML
    void onDeleteClick(ActionEvent event) {
        Emple emp = empTView.getSelectionModel().getSelectedItem();
        if (emp==null) {
            AlertUtil.showAlert("Error de selección", "No se ha seleccionado a ningún usuario", Alert.AlertType.ERROR);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion de borrado");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás totalmente seguro de querer borrar a este usuario? No habrá vuelta atrás");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (empDAO.deleteEmple(emp.getDNI())==1){
                    AlertUtil.showAlert("Eliminación de usuario", "Usuario borrado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        }
    }

    @FXML
    void onEditClick(ActionEvent event) {
        Emple emp = empTView.getSelectionModel().getSelectedItem();
        if (emp==null) {
            AlertUtil.showAlert("Error de selección", "No se ha seleccionado a ningún usuario", Alert.AlertType.ERROR);
        } else {
            EmpsEditController controller = (EmpsEditController) SceneUtils.changeSceneNewStage("empsEditMenu.fxml");
            controller.load(emp);
            controller.getPrevController(this);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empDAO = new EmpleDAO();
        dniCol.setCellValueFactory(data->new ReadOnlyObjectWrapper<>(data.getValue().getDNI()));
        nameCol.setCellValueFactory(data->new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        surnameCol.setCellValueFactory(data->new ReadOnlyObjectWrapper<>(data.getValue().getSurname()));
        salaryCol.setCellValueFactory(data-> new ReadOnlyObjectWrapper<>(data.getValue().getSalary()).asString());
        joinCol.setCellValueFactory(data->new ReadOnlyObjectWrapper<>(data.getValue().getDate_join()));
        dptCol.setCellValueFactory(data->new ReadOnlyObjectWrapper<>(data.getValue().getDept().getDept_name() + "(" + data.getValue().getDept().getDept_location() + ")"));
        tableRefresh();
    }

    public void tableRefresh() {
        ObservableList<Emple> empleList = FXCollections.observableArrayList(empDAO.listEmple());
        empTView.setItems(empleList);
    }
}
