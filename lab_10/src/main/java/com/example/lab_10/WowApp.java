package com.example.lab_10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class WowApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WowApp.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1165, 720);
        stage.setTitle("WOWS!");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("images/owen.bmp")).toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}