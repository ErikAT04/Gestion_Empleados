package com.erikat.gestion_emples.Utils;

public class Validator {
    public static boolean validatePassword(String password, String enterName) {
        if (password.length() < 8) { //Si tiene menos de 8 caracteres
            return false;
        } else if (!(password.matches(".*[0-9].*") && password.matches(".*[A-Z].*")) ) { //Si no incluye números y letras tanto en minúsculas como mayúsculas
            return false;
        } else return !enterName.equals(password); //Devuelve si el nombre NO es igual que la contraseña
    }

    public static boolean validateDNI(String dni) {
        String dni_chars = "TRWAGMYFPDBNJZSQVHLCKE"; //Cadena de caracteres ordenada en función del resto del dni%23
        if(dni.matches("[0-9]{8}[A-Z]")){
            int dni_numb = Integer.parseInt(dni.substring(0, dni.length() - 1)); //Saca el número del DNI completo (caracteres 0-8)
            return(dni.charAt(dni.length()-1) == dni_chars.charAt(dni_numb%23)); //Coge la letra escrita del DNI y la compara con la cadena en la posición del resto del DNI
        } else return false; //Si no pasa el matches, devuelve falso
    }
}
