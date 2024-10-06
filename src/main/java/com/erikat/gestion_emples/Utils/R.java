package com.erikat.gestion_emples.Utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

//CLASE R: Gestiona distintos recursos del programa por medio de clases estáticas

public class R {

    public static InputStream getProperties(String name) { //Por medio de un nombre, consigue la dirección al archivo de propiedades con dicho nombre
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config" + File.separator + name);
    }
    public static URL getUI(String name){ //Por medio de un nombre, devuelve la dirección del archivo FXML de ese nombre
        return Thread.currentThread().getContextClassLoader().getResource("ui/" + name);
    }

}
