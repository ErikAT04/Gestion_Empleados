package com.erikat.gestion_emples.DAO;

import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnterpriseDAO {
    public static int registerEnterprise(String name, String passwd){ //Coge el nombre de la empresa y la contraseña encriptada
        String sql = "INSERT INTO ENTERPRISE (enter_name, enter_passwd) VALUES (?, ?)";
        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, passwd);
            return sentencia.executeUpdate(); //Devuelve las filas actualizadas (debería ser 1)

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1; //Si da error, devuelve -1 (no hay otra forma de llegar a esta solución)
    }

    public static int updateEnterprise(Enterprise enterp){ //Coge la empresa y la edita
        String sql = "UPDATE ENTERPRISE SET enter_name = ?, enter_passwd = ? WHERE id = ?";
        //Trabaja con el ID, un dato que no se ve a simple vista
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, enterp.getEnter_name());
            sentencia.setString(2, enterp.getEnter_passwd());
            sentencia.setInt(3, enterp.getId());
            return sentencia.executeUpdate(); //Devuelve las filas actualizadas (Debería ser 1)

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1; //Devuelve -1 si hay un error en la base de datos
    }

    public static int deleteEnterprise(int id){ //Borra el departamento por medio de un ID
        ArrayList<Depart> depts = DepartDAO.listDepts();
        for (Depart dep: depts){
            if(dep.getEnterprise().getId() == id){
                DepartDAO.dropDept(dep.getId()); //Borra todos los departamentos pertenecientes a la empresa.
            }
        }
        String sql = "DELETE FROM ENTERPRISE WHERE id = ?";
        try {

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setInt(1, id);
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public static Enterprise searchEnterp(String name){ //Busca una empresa por su nombre
        String sql = "SELECT * FROM ENTERPRISE WHERE enter_name = ?";
        try{

            PreparedStatement sentencia = DatabaseManager.con.prepareStatement(sql);
            sentencia.setString(1, name);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()){ //Si en el resultSet encuentra una empresa:
                int id = rs.getInt("id");
                String passwd = rs.getString("enter_passwd");
                return new Enterprise(id, name, passwd); //Devuelve dicha empresa.
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return null; //Si no encuentra nada o hay un error, devuelve null
    }
}
