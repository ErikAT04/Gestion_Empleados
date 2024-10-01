package com.erikat.gestion_emples.DAO;

import com.erikat.gestion_emples.Obj.Depart;
import com.erikat.gestion_emples.Obj.Emple;
import com.erikat.gestion_emples.Utils.DatabaseManager;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmpleDAO {
    private Connection con;
    public EmpleDAO(){
        con = DatabaseManager.conectar();
    }
    public int addEmple(String dni, Depart dpt, String name, String surname, double salary, LocalDate date_join){
        String sql = "INSERT INTO EMPLE (dni, working_dept, emp_name, emp_surname, emp_salary, emp_date_join) VALUES (?, ?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d')";

        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, dni);
            sentencia.setInt(2, dpt.getId());
            sentencia.setString(3, name);
            sentencia.setString(4, surname);
            sentencia.setDouble(5, salary);
            sentencia.setString(6, date_join.toString());
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public int modEmple(Emple emple){
        String sql = "UPDATE EMPLE SET working_dept = ?, emp_name = ?, emp_surname = ?, emp_salary = ?, emp_date_join = STR_TO_DATE(?, '%Y-%m-%d') where dni = ?";

        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(6, emple.getDNI());
            sentencia.setInt(1, emple.getDept().getId());
            sentencia.setString(2, emple.getName());
            sentencia.setString(3, emple.getSurname());
            sentencia.setDouble(4, emple.getSalary());
            sentencia.setString(5, emple.getDate_join().toString());
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public int deleteEmple(String dni){
        String sql = "DELETE FROM EMPLE WHERE DNI = ?";
        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, dni);
            return sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return -1;
    }

    public ArrayList<Emple> listEmple(){
        ArrayList<Emple> list = new ArrayList<>();
        String sql = "SELECT * FROM EMPLE";
        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                DepartDAO dptFinder = new DepartDAO();
                String dni = rs.getString("dni");
                String name = rs.getString("emp_name");
                String surname = rs.getString("emp_surname");
                double salary = rs.getDouble("emp_salary");
                String date_join = rs.getDate("emp_date_join").toString();
                LocalDate emp_date_join = LocalDate.parse(date_join);
                Depart dpt = dptFinder.searchDept(rs.getInt("working_dept"));
                list.add(new Emple(dni, name, surname, salary, emp_date_join, dpt));
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return list;
    }

    public Emple searchEmple(String dni){
        Emple emple = null;
        String sql = "SELECT * FROM EMPLE WHERE DNI = ?";
        try {

            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, dni);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                DepartDAO dptFinder = new DepartDAO();
                String name = rs.getString("emp_name");
                String surname = rs.getString("emp_surname");
                double salary = rs.getDouble("emp_salary");
                String date_join = rs.getDate("emp_date_join").toString();
                LocalDate emp_date_join = LocalDate.parse(date_join);
                Depart dpt = dptFinder.searchDept(rs.getInt("working_dept"));
                emple = new Emple(dni, name, surname, salary, emp_date_join, dpt);
            }

        }catch (SQLException e){
            System.out.println("Error de db: " + e.getSQLState());
        }
        return emple;
    }
}
