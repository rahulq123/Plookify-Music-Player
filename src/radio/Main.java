/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Zee
 */
public class Main extends Application {
    static BorderPane layout;
    @Override
    public void start(Stage primaryStage)throws Exception {        
        
        primaryStage.setTitle("Plookify");              
        layout = (BorderPane) FXMLLoader.load(Main.class.getResource("gui.fxml"));
        Scene scene = new Scene(layout); 
 
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();      
        
    }
    public static void main (String[] args){
        launch(args);
    }
}
