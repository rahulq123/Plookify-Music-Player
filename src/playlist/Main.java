/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author ec14211
 */
public class Main extends Application {
  
    static BorderPane layout;
    @Override
    public void start(Stage primaryStage)throws Exception {        
        
        primaryStage.setTitle("Plookify");              
        layout = (BorderPane) FXMLLoader.load(Main.class.getResource("PLAYLIST.fxml"));
        Scene scene = new Scene(layout); 
        
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();      
        
        
    }
    
    
    
    
    /*
     * change the centre anchorpane to a new fxml file where you can rename a private playlist.
    */
    public static void changescene() throws IOException{
        AnchorPane mainWindow;
        mainWindow = (AnchorPane) FXMLLoader.load(playlist.Main.class.getResource("ChangePlaylistName.fxml"));
        layout.setCenter(mainWindow);
    }
    
    
    /*
     * change the centre anchorpane to a new fxml file where you can rename a friends playlist.
    */
    public static void changetofriendscene() throws IOException{
        AnchorPane mainWindow;
        mainWindow = (AnchorPane) FXMLLoader.load(playlist.Main.class.getResource("renamingfriendsplaylist.fxml"));
        layout.setCenter(mainWindow);
    }
    
    /*
     * change the centre anchorpane to search listView.
     */
    public static void changeToSearchView() throws IOException{
        AnchorPane mainWindow = (AnchorPane) FXMLLoader.load(playlist.Main.class.getResource("SearchView.fxml"));
        layout.setCenter(mainWindow);
    }
    
    /*
     * change the centre anchorpane to now playing playlist.
     */
    public static void changeNowPlaying() throws IOException{
        AnchorPane mainWindow = (AnchorPane) FXMLLoader.load(playlist.Main.class.getResource("NowPlayingPlaylist.fxml"));
        layout.setCenter(mainWindow);
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
