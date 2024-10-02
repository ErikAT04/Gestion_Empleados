package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.DAO.EmpleDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.w3c.dom.events.Event;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EmpsEditController extends Controller implements Initializable {
    EmpleDAO empDAO;
    DepartDAO dptDAO;
    @FXML
    private Button changeBtt;

    @FXML
    private ComboBox<String> deptCB;

    @FXML
    private DatePicker joinDPicker;

    @FXML
    private TextField nameTField;

    @FXML
    private TextField salaryTField;

    @FXML
    private TextField surnameTField;

    @FXML
    private TextField dniTField;

    @FXML
    private Label contextLabel;

    boolean isEditing = false;

    ArrayList<Depart> listDpt; //Se crea un ArrayList que guardará la información de los departamentos

    HashMap<String, Depart> deptMap; //Creo un Hashmap para controlar los departamentos

    @FXML
    void onDatePicked(ActionEvent event) {
        try {
            LocalDate datePicked = joinDPicker.getValue();
            if (datePicked.isAfter(LocalDate.now())){
                AlertUtil.showAlert("Error de Fecha", "Como mínimo, la fecha de alta debería ser la actual", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            AlertUtil.showAlert("Error de Fecha", "La fecha no puede tener ese tipo de valor", Alert.AlertType.ERROR);
            joinDPicker.setValue(LocalDate.now());
        }
    }

    @FXML
    void onChangeClick(ActionEvent event){
        try {
            if (dniTField.getText().isEmpty() || deptCB.getValue() == null || joinDPicker.getValue() == null || nameTField.getText().isEmpty() || salaryTField.getText().isEmpty() || surnameTField.getText().isEmpty()) {
                AlertUtil.showAlert("Error de datos", "Deben estar todos los campos rellenados", Alert.AlertType.ERROR);
            } else {
                String dni = dniTField.getText();
                String name = nameTField.getText();
                String surname = surnameTField.getText();
                double salary = Double.parseDouble(salaryTField.getText());
                LocalDate date_join = joinDPicker.getValue();
                Depart dpt = deptMap.get(deptCB.getValue());
                if (Validator.validateDNI(dni)){
                    Emple e = empDAO.searchEmple(dni);
                    if (e==null){ //Aquí solo va a llegar si se está añadiendo, ya que el DNI no es editable en el otro caso.
                        if (empDAO.addEmple(dni, dpt, name, surname, salary, date_join) == 1){
                            AlertUtil.showAlert("Gestión de empleados", "Empleado añadido correctamente", Alert.AlertType.INFORMATION);
                        } else {
                            AlertUtil.showAlert("Error de creación", "No se ha podido crear el usuario", Alert.AlertType.ERROR);
                        }
                    } else {
                        if (isEditing){
                            if (empDAO.modEmple(e) == 1){
                                AlertUtil.showAlert("Gestión de empleados", "Empleado modificado correctamente", Alert.AlertType.INFORMATION);
                            } else {
                                AlertUtil.showAlert("Error de edición", "Ha habido un error al editar el usuario", Alert.AlertType.ERROR);
                            }
                        } else {
                            AlertUtil.showAlert("Error de creación", "El dni del empleado ya se encuentra registrado en la base de datos", Alert.AlertType.ERROR);
                        }
                    }
                }else {
                    AlertUtil.showAlert("DNI no válido", "El DNI no cumple los requisitos de verificación", Alert.AlertType.ERROR);
                }
            }
        }catch (NumberFormatException e){
            AlertUtil.showAlert("Error de salario", "El salario debe ser un número", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dptDAO = new DepartDAO();
        listDpt = dptDAO.listDepts();
        for (Depart dpt : listDpt){
            String dptInfo = dpt.getDept_name() + " (" + dpt.getDept_location() + ")";
            deptMap.put(dptInfo, dpt);
            deptCB.getId()
        }
    }
}
