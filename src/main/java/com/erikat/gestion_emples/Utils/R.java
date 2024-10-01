package com.erikat.gestion_emples.Utils;

import java.io.File;
import java.io.InputStream;

public class R {

    public static InputStream getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config" + File.separator + name);
    }
}
