package com.erikat.gestion_emples.DAO;

import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartDAO {
    public static int addDept(String dept_name, String dept_location){
        String sql = "INSERT INTO DEPT(dept_name, dept_location, enterprise) VALUES (?, ?, ?);";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, dept_name);
            sentencia.setString(2, dept_location);
            sentencia.setInt(3, DatabaseManager.enterp.getId());
            return sentencia.executeUpdate(); //Devuelve el número de líneas introducidas

        }catch (SQLException e){
            System.out.println("Error de db: "+e.getMessage());
        }
        return -1; //Valor que solo puede dar si hay error en el try-catch
    }
    public static int modDept(Depart dept){
        String sql = "UPDATE DEPT SET dept_name = ?, dept_location = ? WHERE id = ?";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, dept.getDept_name());
            sentencia.setString(2, dept.getDept_location());
            sentencia.setInt(3, dept.getId());
            //El id de la empresa no se puede modificar, por lo que se utiliza para actualizar los demás datos.
            return sentencia.executeUpdate(); //Devuelve el número de líneas actualizadas (Funciona bien si solo sale una)

        }catch (SQLException e){
            System.out.println("Error de db: "+e.getMessage());
        }
        return -1;
    }
    public static int dropDept(int id){
        ArrayList<Emple> listEmple = EmpleDAO.listEmple();
        for (Emple e : listEmple){
            if (e.getDept().getId() == id){
                EmpleDAO.deleteEmple(e.getDNI()); //Primero borra a todos los empleados del departamento
            }
        }
        String sql = "DELETE FROM DEPT WHERE ID = ?";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setInt(1, id);
            return sentencia.executeUpdate(); //Devuelve el número de líneas actualizadas (El programa funciona bien si solo sale una)

        }catch (SQLException e){
            System.out.println("Error de db: "+e.getMessage());
        }
        return -1; //Caso donde hay un error, nunca va a haber -1 actualizaciones
    }
    public static ArrayList<Depart> listDepts(){
        ArrayList<Depart> list = new ArrayList<>();
        String sql = "SELECT * FROM DEPT WHERE ENTERPRISE = ?";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setInt(1, DatabaseManager.enterp.getId());
            ResultSet rs = sentencia.executeQuery(); //ResultSet, lista que guarda los datos
            while (rs.next()){ //Cada iteración del bucle baja una fila en la lista hasta que ya no hay más filas
                int id = rs.getInt("id");
                String dept_name = rs.getString("dept_name");
                String dept_location = rs.getString("dept_location");
                list.add(new Depart(id, dept_name, dept_location, DatabaseManager.enterp)); //Añade el objeto a la lista y se repite el bucle
            }

        }catch (SQLException e){
            System.out.println("Error de db: "+e.getMessage());
        }
        return list; //Devuelve la lista, independientemente de si tiene o no filas
    }

    public static Depart searchDept(int id){
        Depart dpt = null;
        String sql = "SELECT * FROM DEPT WHERE ID = ?";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setInt(1, id);

            ResultSet rs = sentencia.executeQuery();
            if (rs.next()){ //Solo entra si ha encontrado un resultado.
                String dept_name = rs.getString("dept_name");
                String dept_location = rs.getString("dept_location");
                dpt = new Depart(id, dept_name, dept_location, DatabaseManager.enterp);
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getMessage());
        }
        return dpt;
    }
    public static boolean lookIfDepartExists(String name, String location){
        String sql = "SELECT * FROM DEPT WHERE dept_name = ? AND dept_location = ? AND enterprise = ?";
        try{
            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, location);
            sentencia.setInt(3, DatabaseManager.enterp.getId());

            ResultSet rs = sentencia.executeQuery();
            return rs.next(); //Devolverá true si encuentra un valor
        }catch (SQLException e){
            System.out.println("Error de db: "+e.getMessage());
        }
        return true; //Si salta excepción, dará true para que la función no siga su curso
    }
}