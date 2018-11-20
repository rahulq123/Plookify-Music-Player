/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

import account.UserController;
import static account.UserController.accountID;
import account.ViewDeviceController;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ec14211
 */
public class PLAYLISTController implements Initializable {

    public String USER;
    public String PASS;
    public String accountType;
    public String Pstatus;

    @FXML
    Button addPlaylist, addToNowPlayingPlaylist, search, addwholeplaylist, renamePlaylist, switchtoAccount, switchtoPlayer, switchtoRadio, switchtoSocial;
    @FXML
    TextField SearchDescription, newPlaylistName;
    @FXML
    ListView<String> FriendListView, PrivateListView, displaylistview;
    @FXML
    TabPane playlistType;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    Tab privatePlaylist, friendsPlaylist;
    @FXML
    Label addingnewplaylist, label1, selectplaylist, warning;
    @FXML
    AnchorPane change, leftanchor;
    @FXML
    BorderPane mainborder;
    @FXML
    Button switchtoplaylist;
    @FXML
    ContextMenu playlistcontextmenu, contexteMenu, friendsContextMenu;
    @FXML
    MenuItem display, display2, deleteSongs, renamePPlaylist, renameFPlaylist, wholeplaylist, wholeplaylistfriends;

    public static String ArtistNAME;
    public static String PRIVATEPlaylist;
    public static String FRIENDSPlaylist;
    ObservableList<String> FriendsList = FXCollections.observableArrayList();
    ObservableList<String> PrivateList = FXCollections.observableArrayList();
    private NowPlayingPlaylistController control;
    static ObservableList<String> Name = FXCollections.observableArrayList();
    static ObservableList<String> arTist = FXCollections.observableArrayList();
    static ObservableList<String> genre = FXCollections.observableArrayList();
    static ObservableList<Integer> songlength = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList();

    /**
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.setValue("Private");

        //displays the friends and private playlist
        FriendsList = getFriendplaylist();
        FriendListView.setItems(FriendsList);
        PrivateList = getPrivateplaylist();
        PrivateListView.setItems(PrivateList);

        PLAYLISTSELECTEDLISTCONTROLLER.getInstance().setController(this);
        displaylistview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void getUserInfo(String user, String pass, int accID, String paymentStatus) {
        USER = user;
        PASS = pass;
        accountID = accID;
        Pstatus = paymentStatus;
    }

    /*
     * get playlists that user already has in his database
     * only friends playlist
     */
    public ObservableList<String> getFriendplaylist() {
        ObservableList<String> friendlist = FXCollections.observableArrayList();
        Connection c = null;

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");

            stat = c.prepareStatement("SELECT PlaylistName FROM playlist WHERE PlaylistType='Friends' and accountID = '" + account.UserController.getAccountID() + "';");

            ResultSet rs = stat.executeQuery();

            //while loop which stores all the playlist names from resultset
            while (rs.next()) {
                String current = rs.getString("PlaylistName");
                friendlist.add(current);
            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }

        return friendlist;
    }

    /*
     * get playlists that user already has in his database
     * only private playlist
     */
    public ObservableList<String> getPrivateplaylist() {
        Connection c = null;
        ObservableList<String> privatelist = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("SELECT PlaylistName FROM playlist WHERE PlaylistType='Private' and accountID = '" + account.UserController.getAccountID() + "';");

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                String current = rs.getString("PlaylistName");
                privatelist.add(current);
                System.out.println(current);
            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }

        return privatelist;
    }

    /*
     * choose between making the new playlist private or open to friends
     */
    @FXML
    private void handleComboBox() {

    }

    /*
     * handles the add playlist button
     * when user clicks on add playlist button; we get the new name from the text box,
     * get the combo box value, if the combobox value is selected, we check if the user is a paid subscriber or a free user
     * if user is free user a mssage is displayed, he/she cannot create a friend playlist.
     * if the comboBox value is private, 
     */
    @FXML
    private void handleButton(Event e) {

        String newName = newPlaylistName.getText();    //gets the playlist name.

        String status = null;
        String selected = (String) comboBox.getValue(); //gets selected choice from combo box.
        System.out.println(selected);

        //displays the friend and private list
        FriendListView.setItems(FriendsList);
        PrivateListView.setItems(PrivateList);
        //gets the value from comboBox, determines if it goes in friends or private list.
        if (selected.equals("Friends")) {
            FriendsList.addAll(newName);
            FriendListView.setItems(FriendsList);
            status = "Friends";
            checkstatus();
        }

        if (selected.equals("Private")) {
            PrivateList.addAll(newName);
            PrivateListView.setItems(PrivateList);
            status = "Private";
        }
        refresh();

        connect.getInstance().insertlist(newName, status);     //inserts the new playlist name in the database.

    }

    /*
     * checks if the user is a paid subscriber or free user
     */
    public void checkstatus() {
        Connection c = null;
        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("SELECT accountType FROM Accounts WHERE and accountID = '" + account.UserController.getAccountID() + "';"); //need to get account id!!!!!!!
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                String current = rs.getString("accountType");
                if (current.equals("free")) {
                    warning.setText("Please Upgrade you account");
                    warning.setText("");
                }

            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }

    }

    /*
     *search for songs using artist and genre       
     */
    @FXML
    public void handlesearching() throws IOException {

        ArtistNAME = SearchDescription.getText();
        Main.changeToSearchView();                    //changes the centre AnchorPane to the search ListView.        

    }

    /* 
     *to refresh the playlist name text field after it has been added to the listview.    
     */
    private void refresh() {
        newPlaylistName.setText(null);
    }

    @FXML
    public void renamingPlaylist() throws IOException {
        ObservableList<String> privatelist = FXCollections.observableArrayList();
        ObservableList<String> friendslist = FXCollections.observableArrayList();

        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.

            PRIVATEPlaylist = getSelectedprivatePlaylist();                     //gets the name of the private playlist selected
            Main.changescene();                                                 //changes the scene to rename private playlist.

        }
        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.

            FRIENDSPlaylist = getSelectedfriendsPlaylist();                     //gets the name of the friends playlist selected.
            Main.changetofriendscene();                                         //changes the scene to rename friends playlist.
        }

        System.out.println("does it do this bit");
        privatelist = getPrivateplaylist();
        friendslist = getFriendplaylist();

        //PrivateListView.getItems().clear();
        PrivateListView.setItems(PrivateList);
        FriendListView.setItems(FriendsList);
    }

    /*
     * Gets the selected private playlist 
     */
    public String getSelectedprivatePlaylist() {
        String privatePlay = PrivateListView.getSelectionModel().getSelectedItem();    //gets the selected item from private playlist.

        return privatePlay;                                                            //returns the selected private playlist name

    }

    /*
     * Gets the friends playlist selected 
     */
    public String getSelectedfriendsPlaylist() {
        String friendsPlay = FriendListView.getSelectionModel().getSelectedItem();     //gets the selected item from private playlist
        return friendsPlay;                                                            //returns the selected friends playlist name

    }

    /*
     * displaying songs in playlists
     */
    @FXML
    public void displaysongs() {
        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.
            System.out.println("privateplaylist selected");

            getPrivateSongs();
        }
        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.
            System.out.println("friends playlist selected");

            getFriendssongs();
        }
    }

    /*
     * Gets the songs from database and displays on list view.
     */
    public void getPrivateSongs() {
        Connection c = null;
        int current = 0;
        String privateplay = getSelectedprivatePlaylist();
        ObservableList<String> list = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");

            stat = c.prepareStatement("SELECT PlaylistID FROM playlist WHERE PlaylistName = '" + privateplay + "' and accountID = '" + account.UserController.getAccountID() + "';");  //and UserID = 12 //gets the playlistID
            ResultSet rs = stat.executeQuery();

            //gets the playlistID
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

            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        //displaylistview.setItems(list);
        displaylistview.setItems(list);

    }

    /*
     * looks for the playlist having a certain playlistID
     * stores the id and makes a new search for songs using that particular ID
     * displays the result.
     */
    public void getFriendssongs() {
        Connection c = null;
        int current = 0;
        String friendsplay = getSelectedfriendsPlaylist();
        System.out.println(friendsplay);

        ObservableList<String> list = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("SELECT PlaylistID FROM playlist WHERE PlaylistName = '" + friendsplay + "' and accountID = '" + account.UserController.getAccountID() + "';");  //and UserID = 12 //gets the playlistID

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
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        displaylistview.setItems(list);
    }

    /*
     * adds all tracks from playlist to the now playing
     */
    public void addingwholePlaylist() throws IOException {
        control = NowplayingPlaylist.getInstance().getController();
        Main.changeNowPlaying();                                               //changes the scene(centre one).
        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.
            list = getPsongs();  //gets id of the selected private playlist and theose of the tracks in the playlist.

            control.displayrecentlyAdded(list); //sets the list on the listview in the fxml file.
        }
        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.

            list = getFsongs();  //gets id of the selected private playlist and theose of the tracks in the playlist.

            control.displayrecentlyAdded(list); //sets the list on the listview in the fxml file.
        }
    }

    public ObservableList<String> getPsongs() {
        Connection c = null;
        int current = 0;
        String privateplay = getSelectedprivatePlaylist();
        ObservableList<Integer> list1 = FXCollections.observableArrayList();
        ObservableList<String> listsongs = FXCollections.observableArrayList();
        ObservableList<String> artists = FXCollections.observableArrayList();
        ObservableList<String> songtype = FXCollections.observableArrayList();
        ObservableList<Integer> length = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");

            stat = c.prepareStatement("SELECT PlaylistID FROM playlist WHERE PlaylistName = '" + privateplay + "' and accountID = '" + account.UserController.getAccountID() + "';");  //and UserID = 12 //gets the playlistID
            ResultSet rs = stat.executeQuery();

            //gets the playlistID
            while (rs.next()) {
                current = rs.getInt("PlaylistID");

            }

            stat = c.prepareStatement("SELECT TrackID FROM playlistSongs WHERE PlaylistID ='" + current + "';");   //gets the song names               
            rs = stat.executeQuery();
            //gets the trackID
            while (rs.next()) {
                int songs = rs.getInt("TrackID");
                list1.add(songs);            //add all the trackID's to an observablelist.

                String songname = rs.getString("Name");
                listsongs.add(songname);
            }
            for (int i = 0; i < list1.size(); i++) {
                stat = c.prepareStatement("select Name , Length, Artist, Genre from Tracks where TrackID ='" + list1.get(i) + "';");   //gets the song names               
                rs = stat.executeQuery();

                while (rs.next()) {
                    //stores song name
                    String songname = rs.getString("Name");
                    listsongs.add(songname);
                    //stores artist 
                    String artistname = rs.getString("Artist");
                    artists.add(artistname);
                    //stores the genre
                    String type = rs.getString("Genre");
                    songtype.add(type);
                    //stores length
                    int duration = rs.getInt("Length");
                    length.add(duration);
                }
            }
           // NEED TO INSERT NOW

            //insert into NowPlaying(Name,Length, Artist, Genre,accountID)VALUES('kjnev','21','eccec','121');
            rs.close();
            stat.close();
            c.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        return listsongs;
    }

    public ObservableList<String> getFsongs() {
        Connection c = null;
        int current = 0;
        String friendsplay = getSelectedfriendsPlaylist();
        ObservableList<Integer> list2 = FXCollections.observableArrayList();
        ObservableList<String> listsongs = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");

            stat = c.prepareStatement("SELECT PlaylistID FROM playlist WHERE PlaylistName = '" + friendsplay + "' and accountID = '" + account.UserController.getAccountID() + "';");  //and UserID = 12 //gets the playlistID
            ResultSet rs = stat.executeQuery();

            //gets the playlistID
            while (rs.next()) {
                current = rs.getInt("PlaylistID");

            }

            stat = c.prepareStatement("SELECT TrackID, Name FROM playlistSongs WHERE PlaylistID ='" + current + "';");   //gets the song names               
            rs = stat.executeQuery();
            //gets the trackID
            while (rs.next()) {
                int songs = rs.getInt("TrackID");
                list2.add(songs);            //add all the trackID's to an observablelist.

                String songname = rs.getString("Name");
                listsongs.add(songname);
            }

            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        return listsongs;
    }

    public void storesongs() {
        Connection c = null;

    }

    /*
     * deletes the selected item or items from the list view.
     */
    @FXML
    public void delete() {
        ObservableList<String> list = displaylistview.getSelectionModel().getSelectedItems();
        ObservableList<String> newlist = FXCollections.observableArrayList();

        int playlistid = 0;
        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.
            playlistid = getprivatePlaylistID();
            removetracks(playlistid, list);

            newlist = getPFsongs(playlistid);
            displaylistview.setItems(newlist);
        }
        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.
            playlistid = getfriendPlaylistID();
            removetracks(playlistid, list);

            newlist = getPFsongs(playlistid);
            displaylistview.setItems(newlist);
        }
    }

    /*
     * gets the private playlist ID and returns the ID
     */
    private int getprivatePlaylistID() {
        Connection c = null;
        PreparedStatement stat = null;
        PRIVATEPlaylist = getSelectedprivatePlaylist();
        int current = 0;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("select PlaylistID from playlist where PlaylistName= '" + PRIVATEPlaylist + "' and PlaylistType = 'Private' and accountID = '" + account.UserController.getAccountID() + "'");  //and accountID= 113;  

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                current = rs.getInt("PlaylistID");

            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        return current;
    }

    /*
     * deletes the songs which are in the playlist passed on as an argument.
     */
    public void removetracks(int playlistid, ObservableList<String> list) {
        Connection c = null;
        Statement stat = null;
        String sql;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.createStatement();
            for (int i = 0; i < list.size(); i++) //this loop will delete all the songs selected by the user.
            {
                sql = "delete from playlistSongs where playlistID= '" + playlistid + "' and Name = '" + list.get(i) + "'";
                System.out.println(sql);
                stat.executeUpdate(sql);

            }

            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
    }

    /*
     * gets the songs in a particular playlist using the ID to identify the playlist.
     */
    public ObservableList<String> getPFsongs(int playlistid) {
        Connection c = null;
        ObservableList<String> songlist = FXCollections.observableArrayList();

        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("select Name from playlistSongs where PlaylistID = '" + playlistid + "'");

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                String current = rs.getString("Name");
                songlist.add(current);           //adds the name of the songs to the observablelist songlist.

            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }

        return songlist;    //returns the observable list of the track names.
    }

    /*
     * gets the friends playlist id from database for the selected playlist from the list view
     */
    private int getfriendPlaylistID() {
        Connection c = null;
        PreparedStatement stat = null;
        FRIENDSPlaylist = getSelectedfriendsPlaylist();   //gets the selected playlist from friends list view and stores it in a static variable
        int current = 0;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("select PlaylistID from playlist where PlaylistName= '" + FRIENDSPlaylist + "' and PlaylistType = 'Friends' and accountID = '" + account.UserController.getAccountID() + "'");  //and accountID= 113;  

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                current = rs.getInt("PlaylistID");

            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        return current;
    }

    /*
     * gets the observablelist for trackID and track name, from searchviewController
     * checks if the selected playlist is private or friends.
     * gets the playlist id and adds the songs selected to the user's playlist. 
     */
    public int addnewsongstoPlaylist() {
        String playlist;
        int playlistid = 0;

        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.
            System.out.println("privateplaylist selected");
            playlistid = getprivatePlaylistID();                                //gets the selected private playlist

            //getPrivateSongs();
        }

        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.
            System.out.println("friends playlist selected");
            playlistid = getfriendPlaylistID();                                 //gets the ID of the selected playlist.

            // getFriendssongs();
        }

        if (playlistType.getSelectionModel().getSelectedItem().equals(null)) {            //if user did not select any playlist
            selectplaylist.setText("Please select a playlist.");
        }
        return playlistid;
    }
    /*
     *displays the songs.
     */

    public void addtocorrectplaylist() {
        if (playlistType.getSelectionModel().getSelectedItem().equals(privatePlaylist)) {       //checks if the selected item is from private playlist.

            getPrivateSongs();
        }

        if (playlistType.getSelectionModel().getSelectedItem().equals(friendsPlaylist)) {       //checks if the selected item is from friends playlist.

            getFriendssongs();
        }
    }


    /*
     * displays the private playlist in the private playlist list view.
     */
    public void displayPrplaylist() {

        PrivateList = getPrivateplaylist();
        System.out.println(PrivateList);
        PrivateListView.setItems(PrivateList);
    }

    /*
     * displays the friends playlist in the list view.
     */
    public void displayFRplaylist() {
        FriendsList = getFriendplaylist();
        FriendListView.setItems(FriendsList);
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
