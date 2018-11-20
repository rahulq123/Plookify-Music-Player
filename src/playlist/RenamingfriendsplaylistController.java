/*
 * Changes only the friends playlist
 */
package playlist;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Priyanka
 */
public class RenamingfriendsplaylistController implements Initializable {
    @FXML
    TextField newfriendsplaylistname;
    @FXML
    Button okbutton;

    private PLAYLISTController controller;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Okbutton();
    }    

    @FXML
    private void Okbutton() {
        controller = PLAYLISTSELECTEDLISTCONTROLLER.getInstance().getController();
        
        String friendsPlay;
        friendsPlay = controller.getSelectedfriendsPlaylist();         //gets the selected friends playlist and stores it in friendsPlay.
        String newfriendsplaylistNAME = newfriendsplaylistname.getText();
        
        if(friendsPlay!= null){
            updatingplaylistName(newfriendsplaylistNAME, friendsPlay);     //updates the database
            controller.displayFRplaylist();
        }
        
        
    }
    /*
     * updates the database, replaces the old name with a new name the user just typed in.
    */
    public void updatingplaylistName(String newNAME, String playlistselected){
       System.out.println(newNAME);
        Connection c = null;
        Statement stat = null;
            try{
                c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                stat = c.createStatement();
                String sql = "UPDATE playlist SET PlaylistName = '"+newNAME+"' WHERE PlaylistName = '"+playlistselected+"' and PlaylistType = 'Friends'; ";   //and accountID='"+account.UserController.getAccountID()+"'
                stat.executeUpdate(sql);
                stat.close(); 
                c.close();
            }
            catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new RuntimeException("Database connection failed!", ex);
            }  
    }
    
}
