/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaltrackplayer;

import account.UserController;
import static account.UserController.accountID;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Sami
 */
public class FXMLDocumentController implements Initializable {

    Connection connection = null;

    ResultSet rs = null;
    Statement stmt = null;

    PreparedStatement prepareStatement = null;

    public String USER;
    public String PASS;
    public String accountType;
    public String Pstatus;

//    static Connection connection = null;
    static PlayerObject playerO = new PlayerObject();
    static String trackNameToBeAdded = "";
    static int secondsPassed = 0;
    static Timeline aSecond;
    public static long secondsToBeSet;
    static Timeline fTimeLabel;
    static Boolean playFirst = true;
    static ResultTable rt;
    static int totalInNowP = 0;
    static int lastOne = -1;

    @FXML
    TableView<NowPTable> table;

    @FXML
    TableView<ResultTable> resultTable;

    @FXML
    Button addButton;

    @FXML
    Button removeButton;

    @FXML
    TextField searchText;

    @FXML
    Button searchButton;

    @FXML
    Button playButton;

    @FXML
    Button pauseButton;

    @FXML
    Button stopButton;

    @FXML
    Button forwardButton;

    @FXML
    Button rewindButton;

    @FXML
    Button skipButton;

    @FXML
    TextField skipText;

    @FXML
    TextField minuteText;

    @FXML
    Label durationLabel;

    @FXML
    Label elapsedLabel;

    @FXML
    Label forwardLabel;

    @FXML
    Label rewindLabel;

    @FXML
    Label errorLabel;

    @FXML
    Label errorLabel2;

    @FXML
    Label nameLabel;

    public int ID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ID = account.UserController.getAccountID();
        //connection = DBConnection.dbConnector();
       

        aSecond = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondsPassed++;

                int remainder = secondsPassed % 60;
                int minutesI = secondsPassed / 60;
                String sps = String.valueOf(minutesI);
                String sps2 = String.valueOf(remainder);

                //String sps = String.valueOf(secondsPassed);
                elapsedLabel.setText("0" + sps + ":" + remainder);
                if (secondsPassed >= playerO.trackLength) {
                    secondsPassed = 0;

                    int remainder2 = playerO.trackLength % 60;
                    int minutesI2 = playerO.trackLength / 60;
                    String sps3 = String.valueOf(minutesI2);
                    String sps4 = String.valueOf(remainder2);

                    elapsedLabel.setText("0" + sps3 + ":" + remainder2);
                    aSecond.stop();

                    table.getSelectionModel().selectNext();
                    int currentIndex = table.getSelectionModel().getSelectedIndex();

                    if (lastOne == totalInNowP) {

                    } else {
                        playFromNowP();
                    }
                    if (currentIndex == totalInNowP - 1) {
                        lastOne = totalInNowP;
                    }
                }
            }
        }));
        aSecond.setCycleCount(Timeline.INDEFINITE);

        
        
        
        TableColumn<NowPTable, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setMinWidth(100);
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<NowPTable, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setMinWidth(100);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<NowPTable, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.setItems(getNowPTable());
        table.getColumns().addAll(nameColumn, artistColumn, genreColumn);

        TableColumn<ResultTable, String> artistColumn2 = new TableColumn<>("Artist");
        artistColumn2.setMinWidth(100);
        artistColumn2.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<ResultTable, String> genreColumn2 = new TableColumn<>("Genre");
        genreColumn2.setMinWidth(100);
        genreColumn2.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<ResultTable, String> nameColumn2 = new TableColumn<>("Name");
        nameColumn2.setMinWidth(150);
        nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));

        resultTable.setItems(getResultTable());
        resultTable.getColumns().addAll(nameColumn2, artistColumn2, genreColumn2);

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stmt = connection.createStatement();
            String query = "SELECT * From NowP";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("Name");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                String lengthS = rs.getString("Length");
                int length = Integer.parseInt(lengthS);

                NowPTable track = new NowPTable();
                track.setArtist(artist);
                track.setGenre(genre);
                track.setName(name);
                track.setLength(length);
                table.getItems().add(track);
                totalInNowP++;
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {

        }

    }

    public void getUserInfo(String user, String pass, int accID, String paymentStatus) {
        USER = user;
        PASS = pass;
        accountID = accID;
        Pstatus = paymentStatus;
    }

    @FXML
    public void searchButtonClicked() {
        rt = resultTable.getSelectionModel().getSelectedItem();
        resultTable.requestFocus();
        while (rt != null) {
            ObservableList<ResultTable> productSelected, allProducts;
            allProducts = resultTable.getItems();
            productSelected = resultTable.getSelectionModel().getSelectedItems();
            productSelected.forEach(allProducts::remove);
            rt = resultTable.getSelectionModel().getSelectedItem();
        }

        String text = searchText.getText();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stmt = connection.createStatement();
            String query = "SELECT * From Tracks Where Artist LIKE " + "'" + text + "'"
                    + " OR Name LIKE " + "'" + text + "'" + " OR Genre LIKE " + "'" + text + "';";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("Name");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                String lengthS = rs.getString("Length");
                int length = Integer.parseInt(lengthS);

                ResultTable track = new ResultTable();
                track.setArtist(artist);
                track.setGenre(genre);
                track.setName(name);
                track.setLength(length);
                resultTable.getItems().add(track);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {

        }
    }

    @FXML
    public void addButtonClicked() throws SQLException {
        totalInNowP++;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stmt = connection.createStatement();
            String query = "INSERT INTO NowP SELECT * FROM Tracks WHERE Name = '" + FXMLDocumentController.trackNameToBeAdded + "';";
            stmt.executeQuery(query);

            stmt.close();
            connection.close();

        } catch (Exception e) {

        }
//            finally {
//            try {
//                stmt.close();
//            } catch (Exception e) {
//            }
//            try {
//                connection.close();
//            } catch (Exception e) {
//            }
//        }

//        try {
//
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
//            connection.setAutoCommit(false);
//            stmt = connection.createStatement();
//            String sqlite = "UPDATE NowP set  accountID = '" + ID + "';";
//            stmt.executeUpdate(sqlite);
//            connection.commit();
//        } catch (Exception e) {
//
//        }
//        finally {
//            try {
//                stmt.close();
//            } catch (Exception e) {
//            }
//            try {
//                connection.close();
//            } catch (Exception e) {
//            }
//        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stmt = connection.createStatement();
            String query = "SELECT * From NowP WHERE Name = '" + FXMLDocumentController.trackNameToBeAdded + "';";
            rs = stmt.executeQuery(query);
            String name = rs.getString("Name");
            String artist = rs.getString("Artist");
            String genre = rs.getString("Genre");
            String lengthS = rs.getString("Length");
            int length = Integer.parseInt(lengthS);

            NowPTable track = new NowPTable();
            track.setArtist(artist);
            track.setGenre(genre);
            track.setName(name);
            track.setLength(length);
            table.getItems().add(track);

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {

        }
//        finally {
//            try {
//                rs.close();
//            } catch (Exception e) {
//            }
//            try {
//                stmt.close();
//            } catch (Exception e) {
//            }
//            try {
//                connection.close();
//            } catch (Exception e) {
//            }
//        }
    }

    @FXML
    public void removeButtonClicked() {
        totalInNowP--;
        NowPTable nt = table.getSelectionModel().getSelectedItem();
        ObservableList<NowPTable> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stmt = connection.createStatement();
            String query = "DELETE FROM NowP WHERE Name = '" + nt.getName() + "';";
            stmt.executeQuery(query);

            stmt.close();
            connection.close();
        } catch (Exception e) {

        }

//        finally {
////            try {
////                rs.close();
////            } catch (Exception e) {
////            }
//            try {
//                stmt.close();
//            } catch (Exception e) {
//            }
//            try {
//                connection.close();
//            } catch (Exception e) {
//            }
//        }
    }

    @FXML
    public void play() {
        if (playFirst) {
            errorLabel.setText("No track has been clicked.");
        }
        playerO.resume();
        aSecond.play();
    }

    @FXML
    public void pause() {
        playerO.pause();
        aSecond.pause();
    }

    @FXML
    public void stop() {
        playerO.stop();
        secondsPassed = 0;
        elapsedLabel.setText("0");
        aSecond.stop();
    }

    @FXML
    public void rewindPressed() {
        if (secondsPassed == 0) {
            System.out.println(secondsPassed);
            errorLabel.setText("Please select a new track");
        } else {
            playerO.rewindStart();

            fTimeLabel = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                int NumI = 1;
                String Num = String.valueOf(NumI);

                @Override
                public void handle(ActionEvent event) {
                    if (NumI < 64) {
                        NumI *= 2;
                        Num = String.valueOf(NumI);
                    }
                    rewindLabel.setText("x" + Num);
                }
            }));
            fTimeLabel.setCycleCount(Timeline.INDEFINITE);

            fTimeLabel.play();
        }
    }

    @FXML
    public void rewindReleased() {
        errorLabel.setText("");
        errorLabel2.setText("");
        playerO.rewindStop();
        secondsPassed += secondsToBeSet;
        if (secondsPassed < 0) {
            secondsPassed = 0;
        }
        String sps = String.valueOf(secondsPassed);
        rewindLabel.setText("");
        fTimeLabel.stop();
        elapsedLabel.setText(sps);
    }

    @FXML
    public void forwardPressed() {
        if (secondsPassed == 0) {
            System.out.println(secondsPassed);
            errorLabel.setText("Please select a new track");
        } else {
            playerO.forwardStart();

            fTimeLabel = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                int NumI = 1;
                String Num = String.valueOf(NumI);

                @Override
                public void handle(ActionEvent event) {
                    if (NumI < 64) {
                        NumI *= 2;
                        Num = String.valueOf(NumI);
                    }
                    forwardLabel.setText("x" + Num);
                }
            }));
            fTimeLabel.setCycleCount(Timeline.INDEFINITE);

            fTimeLabel.play();
        }
    }

    @FXML
    public void forwardReleased() {
        errorLabel.setText("");
        playerO.forwardStop();
        secondsPassed += secondsToBeSet;
        String sps = String.valueOf(secondsPassed);
        elapsedLabel.setText(sps);
        System.out.println(sps);
        forwardLabel.setText("");
        fTimeLabel.stop();
    }

    @FXML
    public void playFromResults() {
        playFirst = false;
        errorLabel.setText("");
        ResultTable rt = resultTable.getSelectionModel().getSelectedItem();
        playerO.stop();

        String newTrackName = "";
        if (rt.getLength() == 157) {
            newTrackName = "AcousticBreeze";
        } else if (rt.getLength() == 190) {
            newTrackName = "CrystalWaters";
        } else if (rt.getLength() == 124) {
            newTrackName = "Dubstep";
        } else if (rt.getLength() == 206) {
            newTrackName = "Motion";
        } else if (rt.getLength() == 146) {
            newTrackName = "Ukulele";
        }

        String song = System.getProperty("user.dir") + "\\src\\finaltrackplayer\\Tracks\\" + newTrackName + ".mp3";
        playerO.play(song);
        playerO.trackLength = rt.getLength();
        FXMLDocumentController.trackNameToBeAdded = rt.getName();

        nameLabel.setText(rt.getName() + " - " + rt.getArtist());

        int remainder = rt.getLength() % 60;
        int minutes = rt.getLength() / 60;
        String minutesS = String.valueOf(minutes);
        String seconds = String.valueOf(remainder);

        durationLabel.setText("0" + minutesS + ":" + seconds);
        elapsedLabel.setText("0");
        secondsPassed = 0;
        aSecond.play();
    }

    @FXML
    public void playFromNowP() {
        playFirst = false;
        errorLabel.setText("");
        NowPTable nt = table.getSelectionModel().getSelectedItem();
        playerO.stop();

        String newTrackName = "";
        if (nt.getLength() == 157) {
            newTrackName = "AcousticBreeze";
        } else if (nt.getLength() == 190) {
            newTrackName = "CrystalWaters";
        } else if (nt.getLength() == 124) {
            newTrackName = "Dubstep";
        } else if (nt.getLength() == 206) {
            newTrackName = "Motion";
        } else if (nt.getLength() == 146) {
            newTrackName = "Ukulele";
        }

        String song = System.getProperty("user.dir") + "\\src\\finaltrackplayer\\Tracks\\" + newTrackName + ".mp3";
        playerO.play(song);
        playerO.trackLength = nt.getLength();
        int remainder = nt.getLength() % 60;
        int minutes = nt.getLength() / 60;
        String minutesS = String.valueOf(minutes);
        String seconds = String.valueOf(remainder);

        nameLabel.setText(nt.getName() + " - " + nt.getArtist());

        durationLabel.setText("0" + minutesS + ":" + seconds);
        elapsedLabel.setText("0");
        secondsPassed = 0;
        aSecond.play();
    }

    @FXML
    public void skip() {
        String text = skipText.getText();
        String text2 = minuteText.getText();
        try {
            secondsPassed = Integer.parseInt(text);
        } catch (Exception e) {
            secondsPassed = 0;
        }
        int minutesPassed = 0;
        try {
            minutesPassed = Integer.parseInt(text2);
        } catch (Exception e) {
            minutesPassed = 0;
        }
        minutesPassed *= 60;
        secondsPassed += minutesPassed;

        playerO.skip(secondsPassed, playerO.trackLength);
    }

    public ObservableList<NowPTable> getNowPTable() {
        ObservableList<NowPTable> tracks = FXCollections.observableArrayList();
        return tracks;
    }

    public ObservableList<ResultTable> getResultTable() {
        ObservableList<ResultTable> tracks = FXCollections.observableArrayList();
        return tracks;
    }

    public void switchtoAccount(ActionEvent event) throws SQLException, ParseException {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/account/User.fxml").openStream());
            UserController userController = (UserController) loader.getController();
            userController.getUserInfo(USER, PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
