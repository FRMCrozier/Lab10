package com.example.lab_10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

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
        try {
            con = getConnection(Constants.URL, DBClass.getProperties());
            Statement statement = con.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from wows;");
            data = FXCollections.observableArrayList();
            Load();
            while (resultSet.next()) {
                data.add(new Wow(resultSet.getInt("WowID"), resultSet.getString("Movie"), resultSet.getInt("movieYear"), resultSet.getDate("ReleaseDate").toLocalDate(), resultSet.getString("Director"), resultSet.getString("MovieCharacter"), resultSet.getTime("MovieDuration").toLocalTime(), resultSet.getTime("TimestampM").toLocalTime(), resultSet.getString("FullLine"),
                        resultSet.getInt("CurrentWow"), resultSet.getInt("TotalWow"), resultSet.getString("Poster"), resultSet.getString("Video"), resultSet.getString("Audio")));
            }
            tableWows.setItems(data);
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений по айди
     */
    public void FoundID(ActionEvent actionEvent) {
        try {
            int idWow = Integer.parseInt(fieldID.getText());
            con = getConnection(Constants.URL, DBClass.getProperties());

            String query = "SELECT * FROM wows WHERE WowID = ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setInt(1, idWow);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
            while (rs.next()) {
                data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                        rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
            }
            tableWows.setItems(data);
        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        } catch (SQLException se) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Удаление значений по айди
     */
    public void DeleteID(ActionEvent actionEvent) {

        try {
            int idWow = Integer.parseInt(fieldID.getText());
            con = getConnection(Constants.URL, DBClass.getProperties());
            Statement st = con.createStatement();

            String sql = "SELECT * FROM wows WHERE WowID = " + idWow + ";";
            String sql1 = "DELETE FROM wows WHERE WowID = " + idWow + ";";
            ResultSet rs = st.executeQuery(sql);

            int c = 0;
            while (rs.next()) {
                c++;
            }
            if (c > 0) {
                st.executeUpdate(sql1);
                MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Deleted successfully");
                LoadDT(actionEvent);
            } else {
                MBox.MessageBox(Alert.AlertType.WARNING, null, "This wow does not exist!");
            }

            rs.close();
            st.close();

        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        } catch (SQLException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений по названию фильма
     */
    public void FoundTitle(ActionEvent actionEvent) {
        try {
            String movieTitle = fieldMovie.getText();
            con = getConnection(Constants.URL, DBClass.getProperties());

            String query = "SELECT * FROM wows WHERE Movie = ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setString(1, movieTitle);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
            while (rs.next()) {
                data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                        rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
            }
            tableWows.setItems(data);
        } catch (SQLException se) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений, где общее количество Wow больше, чем n
     */
    public void FoundWow(ActionEvent actionEvent) {
        try {
            int tWow = Integer.parseInt(fieldWow.getText());
            con = getConnection(Constants.URL, DBClass.getProperties());

            String query = "SELECT * FROM wows WHERE TotalWow > ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setInt(1, tWow);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
            while (rs.next()) {
                data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                        rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
            }
            tableWows.setItems(data);
        } catch (NumberFormatException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        } catch (SQLException se) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Очистка таблицы
     */
    public void ClearTable(ActionEvent actionEvent) {
        try {
            con = getConnection(Constants.URL, DBClass.getProperties());
            String query = "TRUNCATE TABLE wows;";
            Statement stm = con.createStatement();
            stm.execute(query);
            LoadDT(actionEvent);
            stm.close();
            MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Table is clear!");

        } catch (SQLException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
    }

    public void initialize() {
        con = null;
    }
}