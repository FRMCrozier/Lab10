package com.example.lab_10;

import javafx.scene.control.Alert;

public class MBox {
    public static void MessageBox(Alert.AlertType type, String info, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(alert.getAlertType().toString());
        alert.setHeaderText(info);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
