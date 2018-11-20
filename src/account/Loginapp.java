/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author rahul
 */
public class Loginapp extends Application {

   
    
   

    
   
    @Override
    public void start(Stage primarystage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/account/Login.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primarystage.setTitle("Plookify");
            primarystage.setScene(scene);
            primarystage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Loginapp();
        launch(args);
    }
    
   
        

}
