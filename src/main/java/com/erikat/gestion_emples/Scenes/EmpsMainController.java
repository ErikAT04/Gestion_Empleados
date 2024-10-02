package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.EmpleDAO;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @FXML
    void onAddClic(ActionEvent event) {

    }

    @FXML
    void onDeleteClick(ActionEvent event) {

    }

    @FXML
    void onEditClick(ActionEvent event) {

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

    private void tableRefresh() {
        ObservableList<Emple> empleList = FXCollections.observableArrayList(empDAO.listEmple());
        empTView.setItems(empleList);
    }
}
