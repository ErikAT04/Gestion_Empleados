package com.erikat.gestion_emples.Obj;

public class Depart {
    int id;
    String dept_name;
    String dept_location;
    Enterprise enterprise;

    public Depart(int id, String dept_name, String dept_location, Enterprise enterprise) {
        this.id = id;
        this.dept_name = dept_name;
        this.dept_location = dept_location;
        this.enterprise = enterprise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_location() {
        return dept_location;
    }

    public void setDept_location(String dept_location) {
        this.dept_location = dept_location;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
