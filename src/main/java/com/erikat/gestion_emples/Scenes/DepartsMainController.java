package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
        SceneUtils.changeSceneNewStage("departsEditMenu.fxml");
    }

    @FXML
    void onDeleteClick(ActionEvent event) {

    }

    @FXML
    void onEditClick(ActionEvent event) {
        DepartsEditController controller = (DepartsEditController) SceneUtils.changeSceneNewStage("departsEditMenu.fxml");
        tableRefresh();
    }

    private void tableRefresh(){
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
