package com.erikat.gestion_emples.Obj;

import java.time.LocalDate;

public class Emple {
    private String dni;
    private String name;
    private String surname;
    private double salary;
    private LocalDate date_join;
    private Depart dept;

    public Emple(String dni, String name, String surname, double salary, LocalDate date_join, Depart dept) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.date_join = date_join;
        this.dept = dept;
    }

    public Depart getDept() {
        return dept;
    }

    public void setDept(Depart dept) {
        this.dept = dept;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getDate_join() {
        return date_join;
    }

    public void setDate_join(LocalDate date_join) {
        this.date_join = date_join;
    }
}
