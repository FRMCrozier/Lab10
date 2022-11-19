package com.example.lab_10;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;

public class HelloController {
    @FXML
    public TableView<Wow> tableWows;
    @FXML
    TableColumn<Wow, Integer> WowID; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Movie; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> movieYear;
    @FXML
    TableColumn<Wow, String> ReleaseDate; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Director; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> MovieCharacter; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> MovieDuration; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> TimestampM; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> FullLine; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> CurrentWow; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> TotalWow; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Poster; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Video; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Audio; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TextField fieldID;
    @FXML
    TextField fieldMovie;
    @FXML
    TextField fieldWow;
    private ObservableList<Wow> data;
    private Connection con;


    /**
     * Подключение к БД
     */


    public void ConnectDB() {
        DBClass.ConnectDB();
    }

    /**
     * Загрузка апи в БД
     */
    public void DownloadDB() throws IOException {
        DBClass.DownloadDB();
    }

    /**
     * Разрыв соединения
     */
    public void Exit() {
        DBClass.Exit();
    }

    /**
     * Вспомогательный метод для загрузки значений из БД в таблицу
     */
    public void Load() {
        WowID.setCellValueFactory(new PropertyValueFactory<>("WowID"));
        Movie.setCellValueFactory(new PropertyValueFactory<>("Movie"));
        movieYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<>("ReleaseDate"));
        Director.setCellValueFactory(new PropertyValueFactory<>("Director")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        MovieCharacter.setCellValueFactory(new PropertyValueFactory<>("Character")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        MovieDuration.setCellValueFactory(new PropertyValueFactory<>("MovieDuration"));
        TimestampM.setCellValueFactory(new PropertyValueFactory<>("Timestamp")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        FullLine.setCellValueFactory(new PropertyValueFactory<>("FullLine")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        CurrentWow.setCellValueFactory(new PropertyValueFactory<>("currentWowInMovie"));
        TotalWow.setCellValueFactory(new PropertyValueFactory<>("totalWowsInMovie")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        Poster.setCellValueFactory(new PropertyValueFactory<>("Poster"));
        Video.setCellValueFactory(new PropertyValueFactory<>("Video")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        Audio.setCellValueFactory(new PropertyValueFactory<>("Audio"));
    }
    //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression

    /**
     * //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
     * Загрузка из БД в таблицу
     */
    public void LoadDT(ActionEvent actionEvent) {
        DBClass.LoadDT(data, tableWows);
        Load();
    }

    /**
     * Поиск значений по айди
     */
    public void FoundID(ActionEvent actionEvent) {
        try {
            int idWow = Integer.parseInt(fieldID.getText());
            Load();
            DBClass.FoundID(idWow, data, tableWows);

        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
    }

    /**
     * Удаление значений по айди
     */
    public void DeleteID(ActionEvent actionEvent) {

        try {
            int idWow = Integer.parseInt(fieldID.getText());
            DBClass.DeleteID(idWow, data, tableWows);
        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
    }

    /**
     * Поиск значений по названию фильма
     */
    public void FoundTitle(ActionEvent actionEvent) {
        String movieTitle = fieldMovie.getText();
        Load();
        DBClass.FoundTitle(movieTitle, data, tableWows);

    }

    /**
     * Поиск значений, где общее количество Wow больше, чем n
     */
    public void FoundWow(ActionEvent actionEvent) {
        try {
            int tWow = Integer.parseInt(fieldWow.getText());
            Load();
            DBClass.FoundWow(tWow, data, tableWows);

        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
    }

    /**
     * Очистка таблицы
     */
    public void ClearTable(ActionEvent actionEvent) {
        DBClass.ClearTable(data, tableWows);
    }

    public void initialize() {
        con = null;
    }
}