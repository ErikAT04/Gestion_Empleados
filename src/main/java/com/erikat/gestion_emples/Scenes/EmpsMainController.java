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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'empsMainView.fxml'

public class EmpsMainController extends Controller implements Initializable {

    EmpleDAO empDAO; //Clase DAO de empleados
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
    void onAddClic(ActionEvent event) { //Botón de añadir:
        try {
            EmpsEditController controller = (EmpsEditController) SceneUtils.changeSceneNewStage("empsEditMenu.fxml"); //Crea una nueva ventana con un stage y guarda su controlador
            controller.getPrevController(this); //Con el controller creado, le pasa el controlador actual para manipularlo desde la próxima ventana
        } catch (
                Exception e) { //Puede ocurrir que no pase correctamente el controller, porque de un error al buscar el archivo fxml. El error está controlado en la función, pero aquí falla por la llamada a un objeto nulo
            AlertUtil.showAlert("Error de nueva ventana", "Ha habido un error inesperado", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onDeleteClick(ActionEvent event) { //Botón de borrar
        Emple emp = empTView.getSelectionModel().getSelectedItem(); //Guarda la selección de la tabla
        if (emp == null) { //Si el empleado es nulo (no se ha seleccionado a nadie):
            AlertUtil.showAlert("Error de selección", "No se ha seleccionado a ningún empleado", Alert.AlertType.ERROR);
        } else {
            if (empDAO.deleteEmple(emp.getDNI()) == 1) { //Si se ha eliminado correctamente (Solo una líea)
                AlertUtil.showAlert("Eliminación de empleado", "Usuario borrado correctamente", Alert.AlertType.INFORMATION);
                tableRefresh();//Refresca la tabla
            } else {
                AlertUtil.showAlert("Eliminación de empleado", "Ha habido un error de eliminación", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onEditClick(ActionEvent event) { //Botón de borrar
        Emple emp = empTView.getSelectionModel().getSelectedItem(); //Guarda la selección de la tabla
        if (emp == null) { //Si no se ha seleccionado a nadie
            AlertUtil.showAlert("Error de selección", "No se ha seleccionado a ningún usuario", Alert.AlertType.ERROR);
        } else {
            try {
                EmpsEditController controller = (EmpsEditController) SceneUtils.changeSceneNewStage("empsEditMenu.fxml"); //Carga el FXML en un nuevo stage y guarda su controlador
                controller.load(emp); //Al estar editando, carga el objeto de empleado que ha pasado previamente
                controller.getPrevController(this); //Pasa el controller actual para editarlo más tarde
            }catch (Exception e) { //Igual que antes, puede ocurrir un error porque haya una llamada a un objeto vacío.
                AlertUtil.showAlert("Error de nueva ventana", "Ha habido un error inesperado", Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //Inicializa la tabla y la clase DAO
        empDAO = new EmpleDAO(); //Inicializa
        /*
        En las siguientes líneas, se realiza lo siguiente:
            1-Se crea un objeto de solo lectura que guardará los valores de cada celda. Se crea uno por columna de la tabla
            2-Se le da el valor que interesa que guarde (en la columna de DNI el DNI, en la de Nombre el nombre...)
        Para que salga como nos interesa, hay que dar valores en la declaración de la tabla.
        La tabla tiene que ser de tipo TableView<Objeto> (en este caso TableView<Emple>
        Sus columnas tienen que ser tipo TableColumn<Emple, TipoDato> para que, accediendo al objeto empleado, muestre los distintos valores
         */
        dniCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDNI()));
        nameCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        surnameCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSurname()));
        salaryCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSalary()).asString());
        joinCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDate_join()));
        dptCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDept().getDept_name() + "(" + data.getValue().getDept().getDept_location() + ")"));
        tableRefresh(); //Para terminar, se refresca la tabla
    }

    public void tableRefresh() { //Función que se encarga de cargar y refrescar (recargar) la tabla
        ObservableList<Emple> empleList = FXCollections.observableArrayList(empDAO.listEmple()); //Crea un objeto de tipo ObservableArrayList (Lista observable de FXCollections)
        empTView.setItems(empleList); //Se importa la lista observable a la tabla para que muestre su contenido.
    }
}
