/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Govind
 */
public class DiscoverableController implements Initializable {

//    DiscoverableStatus discStatObj = new DiscoverableStatus(112);
    private double disc;
    private int temporaryID;

    public String USER;
    public String PASS;
    public String accountType;
    public String Pstatus;

    @FXML
    Slider discoverySlider;

    @FXML
    Button searchFriendButton;

    @FXML
    TextField searchFriendBox;

    @FXML
    ContextMenu contextMenu;
    @FXML
    ContextMenu contextMenu1;
    @FXML
    ContextMenu contextMenu2;
    @FXML
    ContextMenu contextMenu3;

    @FXML
    MenuItem addContext;
    @FXML
    MenuItem contextRemove;
    @FXML
    MenuItem aRequest;
    @FXML
    MenuItem dRequest;
    @FXML
    MenuItem Ptable;
    @FXML
    MenuItem tracks;

    @FXML
    Label songsLabel;
    @FXML
    Label playlistLabels;
    @FXML
    Label friendsLabel;

    @FXML
    private ListView<String> tableID;
    @FXML
    private ListView<String> pendingTable;
    @FXML
    private ListView<String> friendIDTable;
    @FXML
    private ListView<String> friendPlaylist;
    @FXML
    private ListView<String> playlistSongs;

    private ObservableList<String> data1 = FXCollections.observableArrayList();
    private ObservableList<String> data2 = FXCollections.observableArrayList();
    private ObservableList<String> data3 = FXCollections.observableArrayList();
    private ObservableList<String> data4 = FXCollections.observableArrayList();
    private ObservableList<String> data5 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        discoverySlider.setValue(disc);
        //tempID();
        temporaryID = accountID;
        DiscoverableStatus discStatObj = new DiscoverableStatus(temporaryID);
        disc = discStatObj.checkDiscoverable();
        discoverySlider.setValue(disc);
        fillTable();
        fillPendingTable();
        fillFriendTable();
        
    }

    public void getUserInfo(String user, String pass, int accID, String paymentStatus) {
        USER = user;
        PASS = pass;
        accountID = accID;
        Pstatus = paymentStatus;
    }

    public void tempID() {
//        temporaryID = Integer.parseInt(tempID.getText());
        System.out.println("Current User " + temporaryID);

        DiscoverableStatus discStatObj = new DiscoverableStatus(temporaryID);
        disc = discStatObj.checkDiscoverable();
        discoverySlider.setValue(disc);
        fillTable();
        fillPendingTable();
        fillFriendTable();
    }

    public void discoverableToggled() {

        disc = discoverySlider.getValue();
        Discoverable discObj = new Discoverable(temporaryID, disc);
        discObj.changeDiscoverable();
    }

    @FXML
    public void SearchFriend() {
        String name = searchFriendBox.getText();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            data1.clear();
            fillTable();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM Accounts WHERE username = '" + name + "' AND isDiscoverable='0.0' AND accountType='Subscribed';");
            while (rs.next()) {
                data1.clear();
                data1.add(rs.getString("username"));
                tableID.setItems(data1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully");
    }

    public void acceptRequest() {
        String selected = pendingTable.getSelectionModel().getSelectedItem();
        int friendID1 = 0;

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                if (rs.getString("username").equals(selected)) {
                    friendID1 = rs.getInt("accountID");
                }

                stmt = c.createStatement();
                String sql = "UPDATE FriendList set FrStatus = 1 where AccountID='" + temporaryID + "' AND FriendID='" + friendID1 + "';";
                stmt.executeUpdate(sql);
                String sq2 = "UPDATE FriendList set FrStatus = 1 where AccountID='" + friendID1 + "' AND FriendID='" + temporaryID + "';";
                stmt.executeUpdate(sq2);
                c.commit();

            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Operation done successfully");
        fillFriendTable();
    }

    @FXML
    public void fillTable() {

        data1.clear();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts WHERE isDiscoverable=0.0 AND accountType='Subscribed';");
            while (rs.next()) {
                if (temporaryID != rs.getInt("AccountID")) {
                    data1.add(rs.getString("username"));
                    tableID.setItems(data1);
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Operation done successfully");
    }

    @FXML
    public void fillPendingTable() {

        ArrayList<Integer> friendTableTemp = new ArrayList<Integer>();
        data2.clear();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            try (ResultSet rs = stmt.executeQuery("SELECT FriendID FROM FriendList WHERE AccountID=" + temporaryID + " AND FrStatus=0;")) {

                while (rs.next()) {
                    friendTableTemp.add(rs.getInt("FriendID"));
                }
                for (int i = 0; i < friendTableTemp.size(); i++) {
                    ResultSet rs1 = stmt.executeQuery("SELECT username from Accounts WHERE AccountID=" + friendTableTemp.get(i) + ";");
                    while (rs1.next()) {
                        data2.add(rs1.getString("username"));
                        pendingTable.setItems(data2);
                    }
                    rs1.close();
                }
                rs.close();
            }

            stmt.close();
            c.close();
        } catch (Exception e) {
            data2.add("No friend requests available");
            pendingTable.setItems(data2);
        }

    }

    @FXML
    public void fillFriendTable() {

        ArrayList<Integer> friendTableTemp = new ArrayList<Integer>();
        data3.clear();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT FriendID FROM FriendList WHERE AccountID=" + temporaryID + " AND FrStatus=1;")) {
                while (rs.next()) {
                    friendTableTemp.add(rs.getInt("FriendID"));
                }
                for (int i = 0; i < friendTableTemp.size(); i++) {
                    ResultSet rs1 = stmt.executeQuery("SELECT username from Accounts WHERE AccountID=" + friendTableTemp.get(i) + ";");
                    while (rs1.next()) {
                        data3.add(rs1.getString("username"));
                        friendIDTable.setItems(data3);
                    }
                    rs1.close();
                }
                rs.close();
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            data3.add("No Friends available");
            pendingTable.setItems(data3);
        }
        fillPendingTable();
    }

    @FXML
    public void fillPlaylistTable() {

        String selected = friendIDTable.getSelectionModel().getSelectedItem();
        playlistLabels.setText(selected + "'s Playlist");
        int friendID1 = 0;
        data4.clear();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                if (rs.getString("username").equals(selected)) {
                    friendID1 = rs.getInt("accountID");
                    break;
                }
            }
            rs.close();

            ResultSet rs1 = stmt.executeQuery("SELECT * FROM playlist WHERE accountID='" + friendID1 + "';");
            while (rs1.next()) {
                //if (temporaryID != rs1.getInt("accountID")) {
                data4.add(rs1.getString("PlaylistName"));
                friendPlaylist.setItems(data4);
                //}
            }
            rs1.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
        System.out.println("Operation done successfully");
    }

    public void getFriendssongs() {
        Connection c = null;
        int current = 0;
        String friendsplay = friendPlaylist.getSelectionModel().getSelectedItem();
        songsLabel.setText(friendsplay + " songs");

        ObservableList<String> list = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("SELECT PlaylistID FROM playlist WHERE PlaylistName = '" + friendsplay + "';");  //and UserID = 12 //gets the playlistID
            System.out.println(friendsplay);
            ResultSet rs = stat.executeQuery();

            //gets the playlist ID
            while (rs.next()) {
                current = rs.getInt("PlaylistID");
                System.out.println(current);
            }

            stat = c.prepareStatement("SELECT Name FROM playlistSongs WHERE PlaylistID ='" + current + "';");   //gets the song names
            rs = stat.executeQuery();

            while (rs.next()) {
                String songs = rs.getString("Name");
                System.out.println(songs);
                list.add(songs);            //add all the names to an observable list.
            }
            playlistSongs.setItems(list);

            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        playlistSongs.setItems(list);
    }

    @FXML
    public void sendRequest() {

        String selected = tableID.getSelectionModel().getSelectedItem();
        int accountID = temporaryID;
        int friendID1 = 0;
        boolean isNotFriend = true;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                if (rs.getString("username").equals(selected)) {
                    friendID1 = rs.getInt("accountID");
                    break;
                }
            }
            rs.close();
            ResultSet rs1 = stmt.executeQuery("SELECT FriendID FROM FriendList WHERE AccountID=" + temporaryID + ";");
            while (rs1.next()) {
                if (rs1.getInt("FriendID") == friendID1) {
                    isNotFriend = false;
                }
            }

            if (isNotFriend) {
                String sql = "INSERT INTO FriendList (AccountID,FriendID,FrStatus) "
                        + "VALUES (" + accountID + ", " + friendID1 + ",2 );";
                stmt.executeUpdate(sql);
                String sq1 = "INSERT INTO FriendList (AccountID,FriendID,FrStatus) "
                        + "VALUES (" + friendID1 + ", " + accountID + ",0 );";
                stmt.executeUpdate(sq1);
                c.commit();
            } else {

            }
            rs1.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void delete() {
        String selected = pendingTable.getSelectionModel().getSelectedItem();
        int accountID = temporaryID;
        int friendID1 = 0;

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                if (rs.getString("username").equals(selected)) {
                    friendID1 = rs.getInt("accountID");
                }
            }
            System.out.println(temporaryID + " " + friendID1);
            String sql = ("DELETE from FriendList where AccountID='" + temporaryID + "' AND FriendID='" + friendID1 + "';");
            stmt.executeUpdate(sql);
            String sq2 = ("DELETE from FriendList where AccountID='" + friendID1 + "' AND FriendID='" + temporaryID + "';");
            stmt.executeUpdate(sq2);

            c.commit();

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully");
        fillPendingTable();
    }

    public void deleteFriend() {
        String selected = friendIDTable.getSelectionModel().getSelectedItem();
        int accountID = temporaryID;
        int friendID1 = 0;

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                if (rs.getString("username").equals(selected)) {
                    friendID1 = rs.getInt("accountID");
                }
            }
            System.out.println(temporaryID + " " + friendID1);
            String sql = ("DELETE from FriendList where AccountID='" + temporaryID + "' AND FriendID='" + friendID1 + "';");
            stmt.executeUpdate(sql);
            String sq2 = ("DELETE from FriendList where AccountID='" + friendID1 + "' AND FriendID='" + temporaryID + "';");
            stmt.executeUpdate(sq2);

            c.commit();

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully");
        fillFriendTable();
        fillPendingTable();
    }

    public void goToHome(ActionEvent event) throws SQLException, ParseException {
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
