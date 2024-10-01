package com.erikat.gestion_emples.Utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showAlert(String title, String content, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
