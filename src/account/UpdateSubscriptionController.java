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
public class UpdateSubscriptionController implements Initializable {
    //int ID = UserController.getAccountID();

    Connection connection = null;
    PreparedStatement prepareStatement = null;
    Statement stmt = null;
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
        //System.out.println("from subscription " + ID);
        // TODO
    }

    public void getUserUpdate(int ID, String user, String pass) throws SQLException {
        accountID = ID;
        this.userName = user;
        this.userPass = pass;
    }

    public void updatePaypal(ActionEvent event) throws SQLException {

        if (userName.equals(Pusername.getText()) && (userPass.equals(Ppassword.getText()))) {

            try {
                paid = "Paid";
                String paypal = "Paypal";
                String ID = Integer.toString(accountID);
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                connection.setAutoCommit(false);

                stmt = connection.createStatement();
                String sqlite = "UPDATE Payment set  PaymentMethod = '" + paypal + "', PaymentStatus = '" + paid + "' where accountID= '" + ID + "';";
                stmt.executeUpdate(sqlite);
                connection.commit();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//            try {
//                rs.close();
//            } catch (Exception e) { /* ignored */ }
                try {
                    stmt.close();
                } catch (Exception e) { /* ignored */ }
                try {
                    connection.close();
                } catch (Exception e) { /* ignored */ }

                JOptionPane.showMessageDialog(null, "Paypal Updated Successfully. You need to Login to see the changes");

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

    public void updateCard(ActionEvent event) throws SQLException {

        try {

            paymentMethod = "Card";
            paid = "Paid";
            String ID = Integer.toString(accountID);

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            connection.setAutoCommit(false);

            stmt = connection.createStatement();
            String sqlite = "UPDATE Payment set  PaymentType = '" + Cpaymenttype.getText() + "', PaymentMethod = '" + paymentMethod + "', PaymentStatus = '" + paid + "', CreditCardNo = '" + Ccreditcardnumber.getText() + "', Firstname = '" + Cfirstname.getText() + "', Lastname = '" + Clastname.getText() + "', Expirationdate = '" + Cexpirationdate.getText() + "', Billingaddress = '" + Cbillingaddress.getText() + "'  where accountID= '" + ID + "';";
            stmt.executeUpdate(sqlite);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) { /* ignored */ }
            try {
                stmt.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

            JOptionPane.showMessageDialog(null, "Card Updated Successfully. You need to Login to see the changes.");

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

    }

}
