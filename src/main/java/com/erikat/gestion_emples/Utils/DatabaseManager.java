package com.erikat.gestion_emples.Utils;

import com.erikat.gestion_emples.Obj.Enterprise;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    public static Connection conectar(){
        Properties dbConfig;
        Connection con = null;
        try{
            dbConfig = new Properties();
            dbConfig.load(R.getProperties("database.properties"));
            String host = dbConfig.getProperty("host");
            String port = dbConfig.getProperty("port");
            String dbname = dbConfig.getProperty("dbname");
            String user = dbConfig.getProperty("uname");
            String passwd = dbConfig.getProperty("passwd");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"?serverTimezone=UTC", user, passwd);
        }catch (SQLException e){
            System.out.println("Error en la base de datos: " + e.getMessage());
        }catch (ClassNotFoundException | IOException e){
            System.out.println(e.getMessage());
        }
        return con;
    }
    public static Enterprise enterp; //Se utilizará para guardar y utilizar datos de la empresa seleccionada en el login.
}
