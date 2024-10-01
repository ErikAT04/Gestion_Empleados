package com.erikat.gestion_emples.Obj;

public class Enterprise {
    private int id;
    private String enter_name;
    private String enter_passwd;

    public Enterprise(int id, String enter_name, String enter_passwd) {
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
