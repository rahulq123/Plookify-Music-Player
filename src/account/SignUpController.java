/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author rahul
 */
public class SignUpController implements Initializable {
    
  
    

    Connection connection = null;
    PreparedStatement prepareStatement = null;
    ResultSet rs = null;

    public int randomnumberID;
    public int accountID;
    public String user;
    public String pass;

//    public SignUpModel signupmodel = new SignUpModel();
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField Password;
    @FXML
    private PasswordField ConfirmPassword;
    @FXML
    private TextField FirstName;
    @FXML
    private TextField LastName;
    @FXML
    private TextField Address;
    @FXML
    private TextField ContactNumber;
    @FXML
    private Button registerfreebutton;
    @FXML
    private Button registersubscribedbutton;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
//        if (signupmodel.isDbConnected()) {
//            //            isConnected.setText("Connected");
//        } else {
//            //            isConnected.setText("Not Connected");
//        }
    }

    public void goToLoginPage(ActionEvent event) throws SQLException {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/account/Login.fxml").openStream());
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerFree(ActionEvent event) throws SQLException {

//        Random rand = new Random();
//        randomnumberID = rand.nextInt(1000) + 1;
//        accountID = Integer.toString(randomnumberID);
        getID();

        if (event.getSource() == registerfreebutton) {

            String free = "Free";

            if (Password.getText().equals(ConfirmPassword.getText())) {
                
                String ID = Integer.toString(accountID);
                double zero = 0.0;
                

                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Accounts values(?,?,?,?,?,?,?,?,?,?)");
                    prepareStatement.setString(1, ID);
                    prepareStatement.setString(2, free);
                    prepareStatement.setString(3, UserName.getText());
                    prepareStatement.setString(4, Password.getText());
                    prepareStatement.setString(5, FirstName.getText());
                    prepareStatement.setString(6, LastName.getText());
                    prepareStatement.setString(7, Address.getText());
                    prepareStatement.setString(8, ContactNumber.getText());
                    prepareStatement.setString(9, null);
                    prepareStatement.setString(10, "0");
                    prepareStatement.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
//                    try {
//                        rs.close();
//                    } catch (Exception e) { /* ignored */ }
                    try {
                        prepareStatement.close();
                    } catch (Exception e) { /* ignored */ }
                    try {
                        connection.close();
                    } catch (Exception e) { /* ignored */ }

                    JOptionPane.showMessageDialog(null, "Free Account Created Successfully");

                    try {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        Pane root = (Pane) loader.load(getClass().getResource("/account/Login.fxml").openStream());
                        Scene scene = new Scene(root);
                        //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
                        primaryStage.setTitle("Plookify");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Password Does Not Match");
            }
        } else {
            //                UserName.clear();
            //                Password.clear();
            //                ConfirmPassword.clear();
        }
    }

    public void registerSubscribe(ActionEvent event) throws SQLException {

//        Random rand = new Random();
//        randomnumberID = rand.nextInt(1000) + 1;
//        accountID = Integer.toString(randomnumberID);
        getID();
        GetDate date = new GetDate();
        String mydate = date.currentDate();
        
        if (event.getSource() == registersubscribedbutton) {

            String subscribe = "Subscribed";

            if (Password.getText().equals(ConfirmPassword.getText())) {
                user = UserName.getText();
                pass = Password.getText();
                try {
                    String ID = Integer.toString(accountID);
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Accounts values(?,?,?,?,?,?,?,?,?,?)");
                    prepareStatement.setString(1, ID);
                    prepareStatement.setString(2, subscribe);
                    prepareStatement.setString(3, UserName.getText());
                    prepareStatement.setString(4, Password.getText());
                    prepareStatement.setString(5, FirstName.getText());
                    prepareStatement.setString(6, LastName.getText());
                    prepareStatement.setString(7, Address.getText());
                    prepareStatement.setString(8, ContactNumber.getText());
                    prepareStatement.setString(9, mydate);
                    prepareStatement.setString(10, "0");
                    prepareStatement.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
//                    try {
//                        rs.close();
//                    } catch (Exception e) { /* ignored */ }
                    try {
                        prepareStatement.close();
                    } catch (Exception e) { /* ignored */ }
                    try {
                        connection.close();
                    } catch (Exception e) { /* ignored */ }

                    JOptionPane.showMessageDialog(null, "Subscribe Account Created Successfully");

                    try {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        Pane root = (Pane) loader.load(getClass().getResource("/account/SubscriptionType.fxml").openStream());
                        SubscriptionTypeController subscriptiontypecontroller = (SubscriptionTypeController) loader.getController();
                        subscriptiontypecontroller.getUserInfo(accountID, user, pass);
                        Scene scene = new Scene(root);
                        //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
                        primaryStage.setTitle("Plookify");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Password Does Not Match");
            }
        } else {
            //                UserName.clear();
            //                Password.clear();
            //                ConfirmPassword.clear();
        }

    }

    public void clear(ActionEvent event) {

        UserName.clear();
        Password.clear();
        ConfirmPassword.clear();
        FirstName.clear();
        LastName.clear();
        Address.clear();
        ContactNumber.clear();

    }

    public void getID() {

        Connection c = null;
        Statement stmt = null;
        
        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select MAX(accountID)id from Accounts;");
            
            while (rs.next()) {
                accountID = rs.getInt("id")+1;
                System.out.println("accountID = " + accountID );
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) { /* ignored */ }
            try {
                c.close();
            } catch (Exception e) { /* ignored */ }

        }
    }
}
