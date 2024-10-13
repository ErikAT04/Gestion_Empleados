package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.SceneUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'departsMainView.fxml'

public class DepartsMainController extends Controller implements Initializable {

    @FXML
    private TableView<Depart> deptTView;

    @FXML
    private TableColumn<Depart, String> locTColumn;

    @FXML
    private TableColumn<Depart, String> nameTColumn;

    @FXML
    void onAddClick() {
        DepartsEditController controller = (DepartsEditController) SceneUtils.changeSceneNewStage("departsEditMenu.fxml", "Intoducción de departamentos");
        if (controller != null) {
            controller.getPrevController(this);
        }
    }

    @FXML
    void onDeleteClick() {
        Depart dpt = this.deptTView.getSelectionModel().getSelectedItem();//Guarda la selección de la tabla en un objeto de tipo Depart
        if (dpt != null) { //Si la selección no es nula (Se ha seleccionado algo:
            DepartDAO.dropDept(dpt.getId());
            AlertUtil.showAlert("Borrado de departamentos", "Departamento borrado correctamente", Alert.AlertType.INFORMATION);
        } else {
            AlertUtil.showAlert("Borrado de departamentos", "No has seleccionado ningún departamento", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEditClick() {
        Depart dpt = this.deptTView.getSelectionModel().getSelectedItem();//Guarda la selección de la tabla en un objeto de tipo Depart
        if (dpt != null) {
            DepartsEditController controller = (DepartsEditController) SceneUtils.changeSceneNewStage("departsEditMenu.fxml", "Edición de departamentos");
            if (controller != null) {
                controller.getPrevController(this);
                controller.load(dpt);
            }
        } else {
            AlertUtil.showAlert("Borrado de departamentos", "No has seleccionado ningún departamento", Alert.AlertType.ERROR);
        }

    }

    public void tableRefresh(){ //Se encarga de cargar y recargar la tabla
        ObservableList<Depart> list = FXCollections.observableArrayList(DepartDAO.listDepts());
        deptTView.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        En las siguientes líneas, se realiza lo siguiente:
            1-Se crea un objeto de solo lectura que guardará los valores de cada celda. Se crea uno por columna de la tabla
            2-Se le da el valor que interesa que guarde (en la columna de Nombre el nombre y en la columna de Localización la localización)
        Para que salga como nos interesa, hay que dar valores en la declaración de la tabla.
        La tabla será de tipo TableView<Depart> (para que data.getValue() sea de tipo Depart) y las columntas de tipo TableColumn<Depart, String>
         */
        nameTColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDept_name()));
        locTColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDept_location()));
        tableRefresh(); //Carga la tabla
    }
}
