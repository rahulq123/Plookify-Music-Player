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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class SubscriptionTypeController implements Initializable {
     

    Connection connection = null;
    PreparedStatement prepareStatement = null;
    ResultSet resultSet = null;
    public int accountID;
    public String paid = "Paid";
    public String outstandingPayment = "Outstanding Payment";

    // ------------Card Variable----------------
    @FXML
    private TextField Cfirstname;
    @FXML
    private TextField Clastname;
    @FXML
    private TextField Ccreditcardnumber;
    @FXML
    private TextField Cpaymenttype;
    @FXML
    private TextField Cexpirationdate;
    @FXML
    private TextField Cbillingaddress;
    @FXML
    private Button CardButton;
  

    // ------------Paypal Variable----------------
    @FXML
    private Label Ptext;
    @FXML
    private TextField Pusername;
    @FXML
    private PasswordField Ppassword;
   

    public String userName;
    public String userPass;
    public String paymentMethod;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        // TODO
    }

    public void getUserInfo(int ID, String user, String pass) {
        accountID = ID;
        this.userName = user;
        this.userPass = pass;

    }



    public void registerPaypal(ActionEvent event) throws SQLException {
        paymentMethod = "Paypal";

        if (userName.equals(Pusername.getText()) && (userPass.equals(Ppassword.getText()))) {

            try {
                paid = "Paid";
                String ID = Integer.toString(accountID);
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                prepareStatement = connection.prepareStatement("insert into Payment values(?,?,?,?,?,?,?,?,?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(3, paymentMethod);
                prepareStatement.setString(4, paid);
                prepareStatement.executeUpdate();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SubscriptionTypeController.class.getName()).log(Level.SEVERE, null, ex);
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

                JOptionPane.showMessageDialog(null, "Paypal Subscribed Successfully");

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
            Ptext.setText("Username and Password in not corrent");

        }

    }

    public void registerCard(ActionEvent event) throws SQLException {
        paymentMethod = "Card";

        if (event.getSource() == CardButton) {

            try {
                paid = "Paid";
                String ID = Integer.toString(accountID);
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                prepareStatement = con.prepareStatement("insert into Payment values(?,?,?,?,?,?,?,?,?)");
                prepareStatement.setString(1, ID);
                prepareStatement.setString(2, Cpaymenttype.getText());
                prepareStatement.setString(3, paymentMethod);
                prepareStatement.setString(4, paid);
                prepareStatement.setString(5, Ccreditcardnumber.getText());
                prepareStatement.setString(6, Cfirstname.getText());
                prepareStatement.setString(7, Clastname.getText());
                prepareStatement.setString(8, Cexpirationdate.getText());
                prepareStatement.setString(9, Cbillingaddress.getText());
                prepareStatement.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
//                try {
//                    rs.close();
//                } catch (Exception e) { /* ignored */ }
                try {
                    prepareStatement.close();
                } catch (Exception e) { /* ignored */ }
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }

                JOptionPane.showMessageDialog(null, "Card Registered Successfully");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            //                UserName.clear();
            //                Password.clear();
            //                ConfirmPassword.clear();
        }

    }

    public void subscribeLater(ActionEvent event) throws SQLException {
        //paymentMethod = "Card";
//        String getDate = 
        int five = 5;

        try {
            String ID = Integer.toString(accountID);
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            prepareStatement = con.prepareStatement("insert into Payment values(?,?,?,?,?,?,?,?,?)");
            prepareStatement.setString(1, ID);
            prepareStatement.setString(4, outstandingPayment);
            prepareStatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
//                try {
//                    rs.close();
//                } catch (Exception e) { /* ignored */ }
            try {
                prepareStatement.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

            JOptionPane.showMessageDialog(null, "You have " + five + " Days free trail for premium use");

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

//    public boolean isPaypal(String user, String pass) throws SQLException {
//        //paymentMethod = "Paypal";
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
//            String query = "select * from Accounts where username = ? and password = ?";
//            prepareStatement = connection.prepareStatement(query);
//            prepareStatement.setString(1, user);
//            prepareStatement.setString(2, pass);           
//            resultSet = prepareStatement.executeQuery();
//
//            if (resultSet.next()) {
//                return true;
//            } else {
//                return false;
//            }
//
//        } catch (Exception e) {
//            return false;
//        } finally {
//            try {
//                resultSet.close();
//            } catch (Exception e) { /* ignored */ }
//            try {
//                prepareStatement.close();
//            } catch (Exception e) { /* ignored */ }
//            try {
//                connection.close();
//            } catch (Exception e) { /* ignored */ }
//        }
//    }
