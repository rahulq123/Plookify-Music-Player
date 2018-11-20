/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ec14211
 */
public class SearchViewController implements Initializable {    
    @FXML
    Button selectingone, addingtoNewPlaylist;
    @FXML
    ListView <String> searchListView;
    @FXML
    AnchorPane searchAnchorpane;
    @FXML
    ContextMenu contextmenu;
    @FXML
    MenuItem ADDtoPlaylist;
    
    private PLAYLISTController controller;

    static ObservableList<String> ArtistSongsList;
    
    String Trackname;
    int newlist=0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            listsearch();
        } catch (IOException ex) {
            Logger.getLogger(SearchViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       //searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);       
    }
    
    public void listsearch() throws IOException {
       
        String Name;
        Name = PLAYLISTController.ArtistNAME; 
        System.out.println(Name);
       
        ArtistSongsList = searchArtist(Name);//Name);        //calls the search method and stores the returning list in ArtistSongsList.
        
        searchListView.setItems(ArtistSongsList);        //lists all the songs by the artist in searchListView.
    }
    
    /*
     * Searching for an artist name from database
     * return the artist songs 
     */
    public ObservableList<String> searchArtist(String Name) {//String artistname){

        Connection c = null;
        ObservableList<String> artistTracks;
        artistTracks = FXCollections.observableArrayList();
        PreparedStatement stat = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("SELECT Name FROM Tracks WHERE Artist LIKE?");                
            String name = Name + "%";
            stat.setString(1, name);
            ResultSet rs = stat.executeQuery();

            //while loop which stores all the artistTracks from resultset
            while (rs.next()) {
                String current = rs.getString("Name");
                System.out.println(current);
                artistTracks.add(current);
            }
            rs.close();
            stat.close();
            c.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        
        return artistTracks;
    }
    
    /*
     * adds the selected items to a playlist.
    */
    @FXML
    public void addToPlaylist(){
        //searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String list = searchListView.getSelectionModel().getSelectedItem();   //gets the selected items from the list view and stores it in an observable list.
        Trackname = list;  //trackname is filled with the tracks names(observablelist 'list')  
        selectsongs(list);  //select the list of songs from database.
        
        
        
        controller.addtocorrectplaylist();
    }
    
    /*
     * takes in a list of the selected items
     * looks for the trackID and stores it in an observable list: newlist
    */
    public void selectsongs(String list) {
        Connection c = null;  
        ResultSet rs = null;
        PreparedStatement stat = null;  
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            stat = c.prepareStatement("select TrackID from Tracks where Name = '" + list + "';");   //gets the track name from tracks table in the database
            rs = stat.executeQuery();
            while (rs.next()) {
                    int current = rs.getInt("TrackID");                      
                    newlist=current;                           //stores the result of selected trackid in newlist
                }
            stat.close();
            controller = PLAYLISTSELECTEDLISTCONTROLLER.getInstance().getController();
            int playlistid = controller.addnewsongstoPlaylist();
            
            stmt = c.prepareStatement("INSERT INTO playlistSongs (PlaylistID,TrackID,Name) VALUES('" + playlistid + "','" + newlist+ "','"+list+"');"); ////and accountID='"+account.UserController.getAccountID()+"'
            stmt.executeUpdate(); 
            
            rs.close();
            stat.close();
            stmt.close();
            c.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
    }
    
        
}
