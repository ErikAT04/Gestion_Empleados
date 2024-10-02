package com.erikat.gestion_emples.Utils;

import com.erikat.gestion_emples.Scenes.MainController;
import com.sun.tools.javac.Main;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static InputStream getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config" + File.separator + name);
    }
    public static URL getUI(String name){
        return Thread.currentThread().getContextClassLoader().getResource("ui/" + name);
    }

}
