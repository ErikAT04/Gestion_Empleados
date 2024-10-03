package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartsMainController extends Controller implements Initializable {
    DepartDAO dptDAO;
    @FXML
    private Button addBtt;

    @FXML
    private TableView<Depart> deptTView;

    @FXML
    private Button editBtt;

    @FXML
    private TableColumn<Depart, String> locTColumn;

    @FXML
    private TableColumn<Depart, String> nameTColumn;

    @FXML
    void onAddClick(ActionEvent event) {
        DepartsEditController controller = (DepartsEditController) SceneUtils.changeSceneNewStage("departsEditMenu.fxml");
    }

    @FXML
    void onDeleteClick(ActionEvent event) {
        Depart dpt = this.deptTView.getSelectionModel().getSelectedItem();
        if (dpt != null) {
            dptDAO.dropDept(dpt.getId());
            AlertUtil.showAlert("Borrado de departamentos", "Departamento borrado correctamente", Alert.AlertType.INFORMATION);
        } else {
            AlertUtil.showAlert("Borrado de departamentos", "No has seleccionado ningún departamento", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEditClick(ActionEvent event) {
        Depart dpt = this.deptTView.getSelectionModel().getSelectedItem();
        if (dpt != null) {
            DepartsEditController controller = (DepartsEditController) SceneUtils.changeSceneNewStage("departsEditMenu.fxml");
            controller.getPrevController(this);
            controller.load(dpt);
        } else {
            AlertUtil.showAlert("Borrado de departamentos", "No has seleccionado ningún departamento", Alert.AlertType.ERROR);
        }

    }

    public void tableRefresh(){
        ObservableList<Depart> list = FXCollections.observableArrayList(dptDAO.listDepts());
        deptTView.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dptDAO = new DepartDAO();
        nameTColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDept_name()));
        locTColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDept_location()));
        tableRefresh();
    }
}
