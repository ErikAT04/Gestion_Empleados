package com.erikat.gestion_emples.Utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    public static InputStream getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config" + File.separator + name);
    }
    public static URL getFXML(String name){
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }

}
