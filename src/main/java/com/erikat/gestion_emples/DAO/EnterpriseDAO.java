package com.erikat.gestion_emples.DAO;

import com.erikat.gestion_emples.Obj.Enterprise;
import com.erikat.gestion_emples.Utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterpriseDAO {
    private Connection con;
    public EnterpriseDAO(){
        con = DatabaseManager.conectar();
    }
    public int registerEnterprise(String name, String passwd){
        String sql = "INSERT INTO ENTERPRISE (enter_name, enter_passwd) VALUES (?, ?)";
        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, passwd);
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public int updateEnterprise(Enterprise enterp){
        String sql = "UPDATE ENTERPRISE SET enter_name = ?, enter_passwd = ? WHERE id = ?";
        try{

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, enterp.getEnter_name());
            sentencia.setString(2, enterp.getEnter_passwd());
            sentencia.setInt(3, enterp.getId());
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public int deleteEnterprise(int id){
        String sql = "DELETE FROM ENTERPRISE WHERE id = ?";
        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public Enterprise searchEnterp(String name){
        String sql = "SELECT * FROM ENTERPRISE WHERE enter_name = ?";
        try{

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, name);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String passwd = rs.getString("enter_passwd");
                return new Enterprise(id, name, passwd);
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return null;
    }
}
