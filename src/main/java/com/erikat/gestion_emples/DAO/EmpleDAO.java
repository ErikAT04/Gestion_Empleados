package com.erikat.gestion_emples.DAO;

import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmpleDAO {
    public static int addEmple(String dni, Depart dpt, String name, String surname, double salary, LocalDate date_join){
        String sql = "INSERT INTO EMPLE(dni, working_dept, emp_name, emp_surname, emp_salary, emp_date_join) VALUES(?, ?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d'))";
        //El comando STR_TO_DATE(fecha, formato) en MySQL permite cambiar un atributo de tipo String a una fecha de sql

        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, dni);
            sentencia.setInt(2, dpt.getId());
            sentencia.setString(3, name);
            sentencia.setString(4, surname);
            sentencia.setDouble(5, salary);
            sentencia.setString(6, date_join.toString()); //La fecha se introduce en tipo String para el comando str_to_date
            return sentencia.executeUpdate(); //Devuelve el número de líneas introducidas

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getMessage());
        }
        return -1; //De haber un error, se devuelve -1 (una posibilidad que nunca se va a dar)
    }

    public static int modEmple(Emple emple){ //Recibe un empleado entero
        String sql = "UPDATE EMPLE SET working_dept = ?, emp_name = ?, emp_surname = ?, emp_salary = ?, emp_date_join = STR_TO_DATE(?, '%Y-%m-%d') where dni = ?";
        //Se trabaja con el DNI ya que es la clave primaria del empleado

        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(6, emple.getDNI());
            sentencia.setInt(1, emple.getDept().getId());
            sentencia.setString(2, emple.getName());
            sentencia.setString(3, emple.getSurname());
            sentencia.setDouble(4, emple.getSalary());
            sentencia.setString(5, emple.getDate_join().toString());
            return sentencia.executeUpdate(); //Devuelve el número de líneas de la edición (Debería ser 1)

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getMessage());
        }
        return -1; //Si hay un error, devuelve -1 (una número que, de otra forma, nunca se va a dar)
    }

    public static int deleteEmple(String dni){
        String sql = "DELETE FROM EMPLE WHERE DNI = ?";
        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, dni);
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getMessage());
        }
        return -1;
    }

    public static ArrayList<Emple> listEmple(){
        ArrayList<Emple> list = new ArrayList<>(); //Crea la lista
        String sql = "SELECT * FROM EMPLE WHERE working_dept = ANY(SELECT ID FROM DEPT WHERE ENTERPRISE=?)";
        //La subconsulta es debido a que la información de las empresas se guardan todas en la misma base de datos, por lo que hay que conseguir que solo lo vea la empresa que nos interesa
        //Para ello, hacemos una subconsulta que muestre solo los empleados que pertenezcan a un departamento que, a su vez, pertenezca a la empresa que está haciendo la consulta
        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setInt(1, DatabaseManager.enterp.getId());
            ResultSet rs = sentencia.executeQuery(); //Guarda toda la información en un ResultSet
            while (rs.next()){ //Mientras el resultSet tenga datos:
                String dni = rs.getString("dni");
                String name = rs.getString("emp_name");
                String surname = rs.getString("emp_surname");
                double salary = rs.getDouble("emp_salary");
                String date_join = rs.getDate("emp_date_join").toString(); //Primero se guarda la fecha como un String
                LocalDate emp_date_join = LocalDate.parse(date_join); //Luego se parsea el String al formato LocalDate (lo bueno que no hay que hacer ningún LocalDateTimeFormatter ya que la LocalDate de Java y la Date de MySQL tienen por defecto el mismo formato
                Depart dpt = DepartDAO.searchDept(rs.getInt("working_dept"));
                list.add(new Emple(dni, name, surname, salary, emp_date_join, dpt)); //Añade a la lista el empleado de la búsqueda
            }

        }catch (SQLException e){ //Error de base de datos
            System.out.println("Error de db: " + e.getMessage());
        }
        return list; //Devuelve la lista como esté, ya sea que no tiene datos o que tiene muchos
    }

    public static Emple searchEmple(String dni){ //Busca un empleado por su DNI
        Emple emple = null; //Crea un objeto de tipo empleado sin iniciarlo
        String sql = "SELECT * FROM EMPLE WHERE DNI = ?";
        try {
            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, dni);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){ //Si encuentra un empleado:
                String name = rs.getString("emp_name");
                String surname = rs.getString("emp_surname");
                double salary = rs.getDouble("emp_salary");
                String date_join = rs.getDate("emp_date_join").toString();
                LocalDate emp_date_join = LocalDate.parse(date_join); //Parsea la fecha de MySQL
                Depart dpt = DepartDAO.searchDept(rs.getInt("working_dept")); //Busca el departamento por medio del código que tiene guardado en su tabla de MySQL
                emple = new Emple(dni, name, surname, salary, emp_date_join, dpt); //Guarda la información en el objeto de empleado
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getMessage());
        }
        return emple; //Devuelve el empleado (Si hay un error, lo devolverá nulo)
    }
}
