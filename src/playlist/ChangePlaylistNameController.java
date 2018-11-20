/*
 * Changes only the private playlist
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ec14211
 */
public class ChangePlaylistNameController implements Initializable {
    @FXML
    TextField newPlaylistName;
    @FXML
    Button okButton;
    
   
    
    private PLAYLISTController controller;
     ObservableList<String> privatelist = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         handleOKbutton();        
    }    
    
    public void handleOKbutton(){
        String privatePlay;
        controller = PLAYLISTSELECTEDLISTCONTROLLER.getInstance().getController();
        privatePlay = controller.getSelectedprivatePlaylist();                  //gets the selected private playlist from PLAYLISTController class.
       
        String newNAME = newPlaylistName.getText();                             //gets new playlist name.
        
        if(newNAME!= null){
             updatingprivatepaylistName(newNAME, privatePlay);
             
        }    
        else{
            System.out.println("please type in a new name.");
        }
    }
    
    /*
     * updates the database, replaces the old name with a new name the user just typed in.
    */
    public void updatingprivatepaylistName(String newNAME, String playlistselected) {      
      System.out.println(newNAME);
        Connection c = null;
        Statement stat = null;
            try{
                
                c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                stat = c.createStatement();
                String sql = "UPDATE playlist SET PlaylistName = '"+newNAME+"' WHERE PlaylistName = '"+playlistselected+"' and PlaylistType = 'Private''.";//and accountID= '"+account.UserController.getAccountID()+"
                stat.executeUpdate(sql);
                stat.close(); 
                c.close();
            }
            catch (SQLException ex) {
                            ex.getMessage();
            } 
            controller.displayPrplaylist();    //calls the method that will set the items on the playlist list view again, after updating the database.
    }    
}
