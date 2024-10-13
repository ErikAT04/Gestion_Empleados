package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//CONTROLADOR QUE AFECTA AL FICHERO 'departsEditMenu.fxml'

public class DepartsEditController extends Controller {

    @FXML
    private Button actionBtt;

    @FXML
    private Label contextLbl;

    @FXML
    private TextField locTField;

    @FXML
    private TextField nameTField;

    private Depart dpt; //Objeto de tipo departamento si

    private boolean isEditing = false;
    //Booleana que controla cómo se ha accedido a esta sección: False si se ha entrado por el botón de añadir y True si se ha entrado por el de editar

    DepartsMainController prevController;

    @FXML
    void onActionClick() {
        /*
        Requisitos de funcionamiento:
            1-Todos los campos están rellenos
            2-No existe un departamento con el mismo nombre y la misma ubicación

        Se tiene que tener en cuenta que la misma fución sirve para añadir o editar, por lo que se controla por medio de una booleana
         */
        if(locTField.getText().isEmpty() || nameTField.getText().isEmpty() ) { //Si algún campo está vacío
            AlertUtil.showAlert("Error de datos", "Todos los campos deben estar rellenados", Alert.AlertType.ERROR);
        } else {
            String location = locTField.getText();
            String name = nameTField.getText();
            if (DepartDAO.lookIfDepartExists(name, location)){ //Si ya existe un departamento en esa empresa con esos datos:
                AlertUtil.showAlert("Error de opción", "Ya existe un departamento con esos datos", Alert.AlertType.ERROR);
            } else {
                if (isEditing){ //Si la booleana es True (se entró desde el botón de editar)
                    dpt.setDept_location(location);
                    dpt.setDept_name(name);
                    if (DepartDAO.modDept(dpt) == 1){ //Si la actualización se ha hecho bien (Se ha actualizado una única fila)
                        AlertUtil.showAlert("Actualización de departamento", "Acción realizada correctamente", Alert.AlertType.INFORMATION);
                        prevController.tableRefresh(); //Se refresca la tabla del menú principal
                        ((Stage)this.actionBtt.getScene().getWindow()).close(); //Se cierra esta ventana
                    } else { //Si no se ha actualizado bien (no debería poder llegar aquí)
                        AlertUtil.showAlert("Error de actualización", "Ha habido un error en la base de datos", Alert.AlertType.ERROR);
                    }
                } else { //Si la booleana es False (se entró desde el botón de añadir)
                    if (DepartDAO.addDept(name, location) == 1){ //Si se ha añadido correctamente (Se ha añadido una línea)
                        AlertUtil.showAlert("Introducción de departamento", "Departamento añadido correctamente", Alert.AlertType.INFORMATION);
                        prevController.tableRefresh(); //Se refresca la tabla del menú principal
                        ((Stage)this.actionBtt.getScene().getWindow()).close(); //Se cierra esta ventana
                    } else { //Si no se ha añadido correctamente (No debería poder llegar aquí)
                        AlertUtil.showAlert("Error de actualización", "Ha habido un error en la base de datos", Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }
    void load(Depart dpt){ //Esta función solo es activada por el botón de editar de la ventana anterior
        this.dpt = dpt; //Se pasa el departamento seleccionado previamente y se iguala en este controlador
        isEditing = true; //La booleana de edición = true
        this.actionBtt.setText("Editar"); //Se cambia el texto del botón de editar
        this.contextLbl.setText("Edición de departamento"); //Se cambia el texto del label
        //Se procede a añadir los valores del departamento pasado por parámetro:
        this.locTField.setText(dpt.getDept_location());
        this.nameTField.setText(dpt.getDept_name());
    }
    void getPrevController(DepartsMainController controller){
        this.prevController = controller; //El objetivo principal de la función es pasar el controlador de la tabla para poder refrescar la tabla
    }
}
