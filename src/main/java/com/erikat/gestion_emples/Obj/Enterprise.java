package com.erikat.gestion_emples.Obj;

public class Enterprise {
    private int id; //Id de empresa, valor de la base de datos que no sale reflejada en ningún otro sitio
    private String enter_name; //Nombre de la empresa
    private String enter_passwd; //Contraseña de la cuenta de la base de datos (encriptada en sha256)

    public Enterprise(int id, String enter_name, String enter_passwd) { //Constructor
        this.id = id;
        this.enter_name = enter_name;
        this.enter_passwd = enter_passwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnter_name() {
        return enter_name;
    }

    public void setEnter_name(String enter_name) {
        this.enter_name = enter_name;
    }

    public String getEnter_passwd() {
        return enter_passwd;
    }

    public void setEnter_passwd(String enter_passwd) {
        this.enter_passwd = enter_passwd;
    }
}
