package com.example.lab_10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class HelloController {
    @FXML
    public TableView<Wow> tableWows;
    private ObservableList<Wow> data;
    @FXML
    TableColumn<Wow, Integer> WowID;
    @FXML
    TableColumn<Wow, String> Movie;
    @FXML
    TableColumn<Wow, Integer> movieYear;
    @FXML
    TableColumn<Wow, String> ReleaseDate;
    @FXML
    TableColumn<Wow, String> Director;
    @FXML
    TableColumn<Wow, String> MovieCharacter;
    @FXML
    TableColumn<Wow, String> MovieDuration;
    @FXML
    TableColumn<Wow, String> TimestampM;
    @FXML
    TableColumn<Wow, String> FullLine;
    @FXML
    TableColumn<Wow, Integer> CurrentWow;
    @FXML
    TableColumn<Wow, Integer> TotalWow;
    @FXML
    TableColumn<Wow, String> Poster;
    @FXML
    TableColumn<Wow, String> Video;
    @FXML
    TableColumn<Wow, String> Audio;
    private Connection con = null;
    @FXML
    TextField fieldID;
    @FXML
    TextField fieldMovie;
    @FXML
    TextField fieldWow;

    /**
     * Подключение к БД
     */

    private boolean DBconnect(String conn, String login, String password) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            String url = "jdbc:mysql://" + conn;
            Properties properties = new Properties();
            properties.setProperty("user", login);
            properties.setProperty("password", password);
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");

            con = getConnection(url, properties);
            System.out.println("Connection ID" + con.toString());
            result = true;
            MessageBox(Alert.AlertType.INFORMATION, null, "Connected successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }

        return result;
    }

    private Properties getProperties (){
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        return properties;
    }

    public void ConnectDB(javafx.event.ActionEvent actionEvent) {
        DBconnect("localhost:3306/test", "root", "root");
    }

    /**
     * Загрузка апи в БД
     */
    public void DownloadDB(ActionEvent actionEvent) {

        try
        {
            Wows wows = new Wows();
            wows = JGetter.loadByURL("https://owen-wilson-wow-api.onrender.com/wows/random?results=10");
            ArrayList<Wow> w = wows.getWows();

            con = getConnection(Constants.URL, getProperties());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("use test;");

            for(Wow wow : w){
                PreparedStatement st1 = con.prepareStatement("insert into wows( Movie, MovieYear, ReleaseDate, Director, MovieCharacter, MovieDuration, TimestampM, FullLine, CurrentWow, TotalWow, Poster, Video, Audio) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                st1.setString(1, wow.getMovie());
                st1.setInt(2, wow.getYear());
                st1.setDate(3, Date.valueOf(wow.getReleaseDate()));
                st1.setString(4, wow.getDirector());
                st1.setString(5, wow.getCharacter());
                st1.setTime(6, Time.valueOf(wow.getMovieDuration()));
                st1.setTime(7, Time.valueOf(wow.getTimestamp()));
                st1.setString(8, wow.getFullLine());
                st1.setInt(9, wow.getCurrentWowInMovie());
                st1.setInt(10, wow.getTotalWowsInMovie());
                st1.setString(11, wow.getPoster());
                st1.setString(12, wow.getVideo());
                st1.setString(13, wow.getAudio());
                System.out.println("\nParametrized query" + st1.toString());
                int result = st1.executeUpdate();
                System.out.println("\nInserted " + result + " records");
            }

            rs.close();
            st.close();
            MessageBox(Alert.AlertType.INFORMATION, null, "Wows are loaded to DB");

        }
        catch(SQLException | IOException e )
        {
            e.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
    }

    /**
     * Разрыв соединения
     */
    public void Exit(ActionEvent actionEvent) {
        if (con != null) {
            try {
                con.close();
                MessageBox(Alert.AlertType.INFORMATION, null, "Disconnected successfully");
            } catch (Exception e) {
                e.printStackTrace();
                MessageBox(Alert.AlertType.WARNING, null, "Error!");
            }
        }
    }

    /**
     * Вспомогательный метод для загрузки значений из БД в таблицу
     */
    public void Load(){
        WowID.setCellValueFactory(new PropertyValueFactory<>("WowID"));
        Movie.setCellValueFactory(new PropertyValueFactory<>("Movie"));
        movieYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<>("ReleaseDate"));
        Director.setCellValueFactory(new PropertyValueFactory<>("Director"));
        MovieCharacter.setCellValueFactory(new PropertyValueFactory<>("Character"));
        MovieDuration.setCellValueFactory(new PropertyValueFactory<>("MovieDuration"));
        TimestampM.setCellValueFactory(new PropertyValueFactory<>("Timestamp"));
        FullLine.setCellValueFactory(new PropertyValueFactory<>("FullLine"));
        CurrentWow.setCellValueFactory(new PropertyValueFactory<>("currentWowInMovie"));
        TotalWow.setCellValueFactory(new PropertyValueFactory<>("totalWowsInMovie"));
        Poster.setCellValueFactory(new PropertyValueFactory<>("Poster"));
        Video.setCellValueFactory(new PropertyValueFactory<>("Video"));
        Audio.setCellValueFactory(new PropertyValueFactory<>("Audio"));
    }

    /**
     * Загрузка из БД в таблицу
     */
    public void LoadDT(ActionEvent actionEvent) {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from wows;");
            System.out.println("\nEXECUTING QUERY TO DB: " + resultSet.toString() + "\n");
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
            e.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений по айди
     */
    public void FoundID(ActionEvent actionEvent) {
        try {
            int idWow = Integer.parseInt(fieldID.getText());
            con = getConnection(Constants.URL, getProperties());

            String query = "SELECT * FROM wows WHERE WowID = ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setInt(1, idWow);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
            while(rs.next()) {
                data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                        rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
            }
            tableWows.setItems(data);
        }
        catch (NumberFormatException e){
            MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
        catch (SQLException se) {
            se.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Удаление значений по айди
     */
    public void DeleteID(ActionEvent actionEvent)  {

        try
        {
            int idWow = Integer.parseInt(fieldID.getText());
            con = getConnection(Constants.URL, getProperties());
            Statement st = con.createStatement();

            String sql = "SELECT * FROM wows WHERE WowID = " + idWow + ";";
            String sql1 = "DELETE FROM wows WHERE WowID = " + idWow + ";";
            ResultSet rs = st.executeQuery(sql);

            int c = 0;
            while (rs.next()){
                c++;
            }
            if (c>0){
                int result = st.executeUpdate(sql1);
                System.out.println("\nDeleted " + result + " records");
                MessageBox(Alert.AlertType.INFORMATION, null, "Deleted successfully");
                LoadDT(actionEvent);
            }
            else {
                MessageBox(Alert.AlertType.WARNING, null, "This wow does not exist!");
            }

            rs.close();
            st.close();

        }
        catch (NumberFormatException e){
            MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений по названию фильма
     */
    public void FoundTitle(ActionEvent actionEvent) {
        try {
            String movieTitle = fieldMovie.getText();
            con = getConnection(Constants.URL, getProperties());

            String query = "SELECT * FROM wows WHERE Movie = ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setString(1, movieTitle);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
                while(rs.next()) {
                    data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                            rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
                }
            tableWows.setItems(data);
        }

        catch (SQLException se) {
            se.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Поиск значений, где общее количество Wow больше, чем n
     */
    public void FoundWow(ActionEvent actionEvent) {
        try {
            int tWow = Integer.parseInt(fieldWow.getText());
            con = getConnection(Constants.URL, getProperties());

            String query = "SELECT * FROM wows WHERE TotalWow > ?";
            PreparedStatement s = con.prepareStatement(query);
            s.setInt(1, tWow);
            ResultSet rs = s.executeQuery();
            data = FXCollections.observableArrayList();
            Load();
            while(rs.next()) {
                data.add(new Wow(rs.getInt("WowID"), rs.getString("Movie"), rs.getInt("movieYear"), rs.getDate("ReleaseDate").toLocalDate(), rs.getString("Director"), rs.getString("MovieCharacter"), rs.getTime("MovieDuration").toLocalTime(), rs.getTime("TimestampM").toLocalTime(), rs.getString("FullLine"),
                        rs.getInt("CurrentWow"), rs.getInt("TotalWow"), rs.getString("Poster"), rs.getString("Video"), rs.getString("Audio")));
            }
            tableWows.setItems(data);
        }
        catch (NumberFormatException e){
            MessageBox(Alert.AlertType.WARNING, null, "Incorrect value!");
        }
        catch (SQLException se) {
            se.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    /**
     * Очистка таблицы
     */
    public void ClearTable(ActionEvent actionEvent) {
        try
        {
            con = getConnection(Constants.URL, getProperties());
            String query = "TRUNCATE TABLE wows;";
            Statement stm = con.createStatement();
            stm.execute(query);
            LoadDT(actionEvent);
            stm.close();
            MessageBox(Alert.AlertType.INFORMATION, null, "Table is clear!");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
    }

    /**
     * Вспомогательный метод для вывода месседж боксов
     */
    private void MessageBox(Alert.AlertType type, String info, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(alert.getAlertType().toString());
        alert.setHeaderText(info);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
