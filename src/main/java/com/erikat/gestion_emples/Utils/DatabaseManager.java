package com.erikat.gestion_emples.Utils;

import com.erikat.gestion_emples.Obj.Enterprise;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//Clase que guarda el método estático de la conexión de base de datos, el cual es utilizado por todas las clases DAO

public class DatabaseManager {
    public static Connection con;
    /*
    Para evitar redundancia de código y abrir muchas veces objetos de tipo Connection, inicio en la clase DataBaseManager un atributo estático de tipo Connection que usarán todas las DAO
     */
    public static void conectar(){ //Inicia el atributo estático Connection, el cual usarán las clases DAO a continuación
        Properties dbConfig; //Crea un objeto que guarda las propiedades
        con = null; //Crea la conexión y la inicia como nula
        try{
            dbConfig = new Properties(); //Inicia el objeto de propiedades
            dbConfig.load(R.getProperties("database.properties")); //Carga en las propiedades el archivo Properties por medio de la clase R
            //Acto seguido, crea las variables del archivo Properties
            String host = dbConfig.getProperty("host");
            String port = dbConfig.getProperty("port");
            String dbname = dbConfig.getProperty("dbname");
            String user = dbConfig.getProperty("uname");
            String passwd = dbConfig.getProperty("passwd");
            //Inicia la conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"?serverTimezone=UTC", user, passwd); //Guarda la conexión a la base
        }catch (SQLException e){ //Error de la base de datos (No la ha encontrado)
            System.out.println("Error en la base de datos: " + e.getMessage());
        }catch (ClassNotFoundException | IOException e){ //Error en la obtención de archivos (Ya sea de la clase del driver de MySQL o del archivo Properties)
            System.out.println(e.getMessage());
        }
    }
    public static Enterprise enterp; //Se utilizará para guardar y utilizar datos de la empresa seleccionada en el login.
}
