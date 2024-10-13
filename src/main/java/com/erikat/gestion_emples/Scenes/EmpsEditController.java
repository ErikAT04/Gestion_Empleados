package com.erikat.gestion_emples.Scenes;

import com.erikat.gestion_emples.DAO.DepartDAO;
import com.erikat.gestion_emples.DAO.EmpleDAO;
import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.AlertUtil;
import com.erikat.gestion_emples.Utils.Controller;
import com.erikat.gestion_emples.Utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

//CONTROLADOR QUE AFECTA AL FICHERO 'empsEditMenu.fxml'

public class EmpsEditController extends Controller implements Initializable {
    private EmpsMainController prevController;
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

    boolean isEditing = false; //Booleana que muestra si se ha accedido a la ventana por el botón de añadir o editar

    ArrayList<Depart> listDpt; //Se crea un ArrayList que guardará la información de los departamentos

    HashMap<String, Depart> deptMap; //Creo un Hashmap para controlar los departamentos

    @FXML
    void onDatePicked() { //Esta función controla el datePicker de la fecha de alta
        try {
            LocalDate datePicked = joinDPicker.getValue();
            if (datePicked.isAfter(LocalDate.now())){ //Si la fecha dada por el objeto es posterior a la fecha actual:
                AlertUtil.showAlert("Error de Fecha", "Como mínimo, la fecha de alta debería ser la actual", Alert.AlertType.ERROR);
                joinDPicker.setValue(LocalDate.now()); //Se cambia a la actual
            }
        }catch (Exception e){ //El usuario intenta meter una fecha por teclado y, a la hora de parsearlo, da un error de formato
            AlertUtil.showAlert("Error de Fecha", "La fecha no puede tener ese tipo de valor", Alert.AlertType.ERROR);
            joinDPicker.setValue(LocalDate.now()); //Por defecto, pone la fecha actual

        }
    }

    @FXML
    void onChangeClick(){ //Botón de cambio:
        /*
        Requisitos para que funcione correctamente:
            1-Todos los campos están rellenados
            2-El DNI cumple los requisitos de validación
            3-El salario es un número con decimales
        La función lleva a cabo tanto las opciones de edición
         */
        try {
            if (dniTField.getText().isEmpty() || deptCB.getValue() == null || joinDPicker.getValue() == null || nameTField.getText().isEmpty() || salaryTField.getText().isEmpty() || surnameTField.getText().isEmpty()) { //Si alguno de los campos está vacío_
                AlertUtil.showAlert("Error de datos", "Deben estar todos los campos rellenados", Alert.AlertType.ERROR);
            } else {
                //Guarda los valores
                String dni = dniTField.getText();
                String name = nameTField.getText();
                String surname = surnameTField.getText();
                double salary = Double.parseDouble(salaryTField.getText());
                LocalDate date_join = joinDPicker.getValue();
                Depart dpt = deptMap.get(deptCB.getValue());
                if (Validator.validateDNI(dni)){ //Si el DNI sigue un formato válido:
                    Emple e = EmpleDAO.searchEmple(dni);
                    if (e==null){ //Aquí solo va a llegar si se está añadiendo, ya que el DNI no es editable en el otro caso.
                        if (EmpleDAO.addEmple(dni, dpt, name, surname, salary, date_join) == 1){ //Si se añade correctamente el usuario (Se añade una fila)
                            AlertUtil.showAlert("Gestión de empleados", "Empleado añadido correctamente", Alert.AlertType.INFORMATION);
                            prevController.tableRefresh(); //Se refresca la tabla
                            ((Stage) contextLabel.getScene().getWindow()).close(); //Se cierra la ventana
                        } else { //Si no se ha podido crear correctamente
                            AlertUtil.showAlert("Error de creación", "No se ha podido crear el usuario", Alert.AlertType.ERROR);
                        }
                    } else {//Si no es nulo (Si ha detectado que hay un empleado con ese DNI)
                        if (isEditing){ //Si ha entrado desde la opción de editar:
                            if (EmpleDAO.modEmple(new Emple(e.getDNI(), name, surname, salary, date_join, dpt)) == 1){ //Si se ha editado correctamente (Se ha editado una fila)
                                AlertUtil.showAlert("Gestión de empleados", "Empleado modificado correctamente", Alert.AlertType.INFORMATION);
                                prevController.tableRefresh(); //Se refresca la tabla
                                ((Stage) contextLabel.getScene().getWindow()).close(); //Se cierra esta ventana
                            } else { //Si ha habido un error:
                                AlertUtil.showAlert("Error de edición", "Ha habido un error al editar el usuario", Alert.AlertType.ERROR);
                            }
                        } else { //Si no se está editando y el dni del empleado no es nulo
                            AlertUtil.showAlert("Error de creación", "El dni del empleado ya se encuentra registrado en la base de datos", Alert.AlertType.ERROR);
                        }
                    }
                }else { //Si el DNI no cumple los requisitos del Validator
                    AlertUtil.showAlert("DNI no válido", "El DNI no cumple los requisitos de verificación", Alert.AlertType.ERROR);
                }
            }
        }catch (NumberFormatException e){ //Salta la excepción si el salario contiene valores no numéricos
            AlertUtil.showAlert("Error de salario", "El salario debe ser un número", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //Inicia todos los campos
        deptMap = new HashMap<>(); //Declara el hashMap encargado de guardar los valores del ComboBox
        listDpt = DepartDAO.listDepts(); //Introduce en la lista todos los departamentos de la base de datos
        for (Depart dpt : listDpt){ //ForEach de la lista:
            String dptInfo = dpt.getDept_name() + " (" + dpt.getDept_location() + ")"; //Crea un String que guarda la información a modo de "Nombre (Localización)
            deptMap.put(dptInfo, dpt); //En el hashMap se añade el string como clave y el departamento como valor.
            deptCB.getItems().add(dptInfo); //En el comboBox se añade a su vez el String como opción
        }
    }

    public void load(Emple emp) { //Clase que carga el botón de editar los usuarios:
        isEditing = true; //Cambia a True la booleana de edición
        //A continuación, se añaden todos los datos:
        this.changeBtt.setText("Editar");
        this.contextLabel.setText("Editar empleado: ");
        this.dniTField.setText(emp.getDNI());
        this.dniTField.setEditable(false); //Se impide la edición del DNI (Es la clave primaria)
        this.nameTField.setText(emp.getName());
        this.surnameTField.setText(emp.getSurname());
        this.salaryTField.setText(String.valueOf(emp.getSalary()));
        this.joinDPicker.setValue(emp.getDate_join());
        this.deptCB.setValue(emp.getDept().getDept_name() + " (" + emp.getDept().getDept_location() + ")"); //Se introduce el valor del comboBox de la misma forma que se introduce el valor en la inicialización: "Nombre (Localización)"
    }
    public void getPrevController(EmpsMainController controller) { //Recibe el controller de la clase anterior para manipular la tabla.
        this.prevController = controller;
    }
}
