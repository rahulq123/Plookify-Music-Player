package radio;

import account.UserController;
import static account.UserController.accountID;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class GuiController implements Initializable {
    
    public String USER;
    public String PASS;
    public String accountType;
    public String Pstatus;
    
    
    

    @FXML
    private Button submit, social, account, playlist, player;
    @FXML
    private TextField searchfield;
    @FXML
    private ListView<String> ListedSearch1s;
    @FXML
    private ListView<String> ArtistList;
    @FXML
    private ListView<String> GenreList;
    @FXML
    private TextField namedPlaylists;
    @FXML
    private Button savePlaylist;

    private ObservableList<String> data = FXCollections.observableArrayList();
    private ObservableList<String> data1 = FXCollections.observableArrayList();
    private ObservableList<String> data2 = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void getUserInfo(String user, String pass, int accID, String paymentStatus) {
        USER = user;
        PASS = pass;
        accountID = accID;
        Pstatus = paymentStatus;
    }

    @FXML
    private void DIsplayListView(ActionEvent event) {
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            Statement stmt = (Statement) c.createStatement();
            String artist = searchfield.getText(); // gets the artist name

            String genre = "SELECT genre FROM Tracks WHERE artist='" + artist + "'";
            ResultSet rs = stmt.executeQuery(genre);

            while (rs.next()) {
                genre = rs.getString("genre");
            }
            String SQL = "SELECT * FROM Tracks WHERE genre='" + genre + "'  ORDER BY RANDOM() limit 0,10";

            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                data.add(rs.getString("Name"));
                data1.add(rs.getString("Artist"));
                data2.add(rs.getString("Genre"));

                ListedSearch1s.setItems(data);
                ArtistList.setItems(data1);
                GenreList.setItems(data2);

            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    @FXML
    private void SavedPlaylist(ActionEvent event) {
        String newName = namedPlaylists.getText();    //gets the playlist name.

        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            Statement stmt = (Statement) c.createStatement();
 stmt.executeUpdate("INSERT INTO playlist" +" (PlaylistType,PlaylistName )"+"VALUES"+"('Private','"+newName+"')");

          stmt.executeUpdate("SELECT rowid, * FROM playlistSongs");

          stmt.executeUpdate("INSERT INTO playlistSongs" +" (rowid,PlaylistID, Name )"+"VALUES"+"(15,15,'"+data+"')");

          /* THIS IS WHERE IT REFUSES TO ADD THE SONGS

          stmt.executeUpdate("INSERT INTO playlist" +" (PlaylistType,PlaylistName )"+"VALUES"+"('Private','"+newName+"')");

          int play = stmt.executeUpdate("SELECT PlaylistID, * FROM playlist WHERE PlaylistName ='"+newName+"'");

          stmt.executeUpdate("INSERT INTO playlistSongs WHERE PlaylistID =' "+play+"'" +" (Name)"+"VALUES"+"('"+data+"')");

        

          */
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    @FXML
    private void PlaylistCall(ActionEvent event) {

    }

   

    
    public void AccountCall(ActionEvent event) throws SQLException, ParseException {
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
