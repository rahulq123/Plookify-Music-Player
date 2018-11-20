/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import static playlist.PLAYLISTController.Name;
import static playlist.PLAYLISTController.arTist;
import static playlist.PLAYLISTController.genre;
import static playlist.PLAYLISTController.songlength;

/**
 * FXML Controller class
 *
 * @author ec14211
 */
public class NowPlayingPlaylistController implements Initializable {
    @FXML
    Label nowplaying;
    @FXML
    ListView<String> displaynowplaying;
    
    private PLAYLISTController controller;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NowplayingPlaylist.getInstance().setController(this);
    }    
    
    /*
     * displays the search in the table
    */
    public void displayrecentlyAdded(ObservableList<String>sNames){
        
        displaynowplaying.setItems(sNames);
        
    }
}
