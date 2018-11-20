/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import finaltrackplayer.FXMLDocumentController;
import static java.awt.Color.red;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import playlist.PLAYLISTController;
import radio.GuiController;
import social.DiscoverableController;

/**
 * FXML Controller class
 *
 * @author rahul
 */
public class UserController implements Initializable {

    Connection connection = null;
    Statement prepareStatement = null;
    ResultSet rs = null;
    Statement stmt = null;

    String[] devicename = {"mobile,games console, pc, mac, tablet"};

    public String USER;
    public String PASS;
    public String accountType;
    public String Pstatus;

    public static int accountID;

    @FXML
    private Label username;
    @FXML
    private Label PaymentStatus;
    @FXML
    private Button OKButton;
    @FXML
    private Button subscribeButton;
    @FXML
    private Button viewDeviceButton;

    @FXML
    private Button signoutDeviceButton;
    @FXML
    private Button closeButton;

    /////    features button
    @FXML
    private Button trackButton;
    @FXML
    private Button playlistButton;
    @FXML
    private Button socialButton;
    @FXML
    private Button radioButton;

    
    public String subscribedDate;
    public int minusDate;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //SubscribeStatus.setText("hello");
        // no device is added. Add

    }
    
    public void getBackFromPlaylist(int ID)
        {
            accountID = ID;
        }

    public void getUserInfo(String user, String pass, int accID, String paymentStatus) throws ParseException {
        OKButton.setDisable(true);
        subscribeButton.setDisable(true);
        username.setText("User : " + user);
        USER = user;
        PASS = pass;
        accountID = accID;
        Pstatus = paymentStatus;

        try {
            getSubscribeDate();
            minusSubscribeDateWithCurrentDate();
            getaccountType();

        } catch (Exception e) {
        }
        if (accountType.equals("free") || (accountType.equals("Free"))) {

            socialButton.setDisable(true);
            radioButton.setDisable(true);

        } else {
            PaymentStatus.setText("Your account is upto to date");
            socialButton.setDisable(false);
            radioButton.setDisable(false);

        }

        try {
            if (Pstatus.equals("Outstanding Payment")) {
                if ((minusDate >= 0) && (minusDate <= 5)) {
                    subscribeButton.setDisable(false);
                    PaymentStatus.setText("You account has : " + paymentStatus + "\nyou have " + minusDate + " day remaining for your trail version. Click on Subscribe button now");

                    subscribeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            try {

                                Class.forName("org.sqlite.JDBC");
                                connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                                connection.setAutoCommit(false);

                                stmt = connection.createStatement();
                                String sqlite = "UPDATE Payment set PaymentStatus = NULL where accountID= '" + accountID + "';";
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

                                try {
                                    ((Node) event.getSource()).getScene().getWindow().hide();
                                    Stage primaryStage = new Stage();
                                    FXMLLoader loader = new FXMLLoader();
                                    Pane root = (Pane) loader.load(getClass().getResource("/account/UpdateSubscription.fxml").openStream());
                                    UpdateSubscriptionController updateSubscriptionController = (UpdateSubscriptionController) loader.getController();
                                    updateSubscriptionController.getUserUpdate(accountID, USER, PASS);
                                    Scene scene = new Scene(root);
                                    //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
                                    primaryStage.setTitle("Plookify");
                                    primaryStage.setScene(scene);
                                    primaryStage.show();
                                } catch (Exception e) {

                                }

                            }

                        }
                    });
                } else {

                    int newminusDate = minusDate - 5;
                    makeButonDisable();

                    PaymentStatus.setText("You account has : " + paymentStatus + "\nyou have passed ( " + newminusDate + " ) day from your trail version. Your account will now revert to free Account");
                    JOptionPane.showMessageDialog(null, "You account has : " + paymentStatus + "\nyou have passed ( " + newminusDate + " ) day from your trail version. Your account will now revert to free Account");
                    updateAccountToFree();

                    JOptionPane.showMessageDialog(null, "Your acount will revert to free account by clicking on OK button on your Home Page");
                    OKButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            try {
                                Class.forName("org.sqlite.JDBC");
                                connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                                connection.setAutoCommit(false);
                                //System.out.println("Opened database successfully");

                                prepareStatement = connection.createStatement();
                                String deleteaccount = "DELETE from Payment where accountID= '" + accountID + "';";
                                prepareStatement.executeUpdate(deleteaccount);
                                connection.commit();

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    rs.close();
                                } catch (Exception e) { /* ignored */ }
                                try {
                                    prepareStatement.close();
                                } catch (Exception e) { /* ignored */ }
                                try {
                                    connection.close();
                                } catch (Exception e) { /* ignored */ }

                                JOptionPane.showMessageDialog(null, "Account reverted successfully");

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
                    });

                }
            } else {
                PaymentStatus.setText("Your account is upto to date");
            }
        } catch (Exception e) {
        }

    }

    public void signOut(ActionEvent event) {
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

        }
    }

    public void closeAccount(ActionEvent event) throws SQLException {

        getID(USER);

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            connection.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            prepareStatement = connection.createStatement();
            String deleteaccount = "DELETE from Accounts where username= '" + USER + "';";
            prepareStatement.executeUpdate(deleteaccount);
            connection.commit();

            rs = prepareStatement.executeQuery("SELECT * FROM Accounts;");
            while (rs.next()) {
                int id = rs.getInt("accountID");
                String accounttype = rs.getString("accountType");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstname = rs.getString("firstName");
                String lastname = rs.getString("lastName");
                String address = rs.getString("address");
                String contactnumber = rs.getString("contactNumber");
                System.out.println("ID = " + id);
                System.out.println("Accounttype = " + accounttype);
                System.out.println("username = " + username);
                System.out.println("password = " + password);
                System.out.println("firstname = " + firstname);
                System.out.println("lastname = " + lastname);
                System.out.println("ADDRESS = " + address);
                System.out.println("contactnumber = " + contactnumber);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                prepareStatement.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

            deletePayment(USER);
            deleteDevice(accountID);

            JOptionPane.showMessageDialog(null, "Account closed successfully");

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

    public void getaccountType() {
        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select accountType from Accounts where accountID = '" + accountID + "';");

            while (rs.next()) {
                accountType = rs.getString("accountType");
                System.out.println("accountType = " + accountType);
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

    public void getID(String user) {

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select accountID from Accounts where username= '" + USER + "';");

            while (rs.next()) {
                accountID = (rs.getInt("accountID"));
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

    public void deletePayment(String user) throws SQLException {

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            connection.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            prepareStatement = connection.createStatement();
            String deleteaccount = "DELETE from Payment where accountID= '" + accountID + "';";
            prepareStatement.executeUpdate(deleteaccount);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) { /* ignored */ }
            try {
                prepareStatement.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

        }

    }

    public void deleteDevice(int id) {
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            connection.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            prepareStatement = connection.createStatement();
            String deleteaccount = "DELETE from Device where accountID= '" + accountID + "';";
            prepareStatement.executeUpdate(deleteaccount);
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                rs.close();
//            } catch (Exception e) { /* ignored */ }
            try {
                prepareStatement.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

        }

    }

    public void getSubscribeDate() {
        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select SubscribedDate from Accounts where accountID= '" + accountID + "';");

            while (rs.next()) {
                subscribedDate = rs.getString("SubscribedDate");
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

    public void minusSubscribeDateWithCurrentDate() throws ParseException {
        GetDate date = new GetDate();

        try {
            minusDate = date.minusSubscribeDate(subscribedDate);
        } catch (Exception e) {
        }
    }

    public void updateAccountToFree() {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sqlite = "UPDATE Accounts set accountType = 'Free', SubscribedDate = NULL  where accountID= '" + accountID + "';";
            stmt.executeUpdate(sqlite);
            c.commit();

//            rs = stmt.executeQuery("SELECT * FROM Accounts;");
//            while (rs.next()) {
//                int id = rs.getInt("accoutnID");
//                String accounttype = rs.getString("accountType");
//                String date = rs.getString("SubscribedDate");
//                System.out.println("ID = " + id);
//                System.out.println("accountType = " + accounttype);
//                System.out.println("date = " + date);
//                System.out.println();
//            }
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

    public void makeButonDisable() {
        // keep it false
        OKButton.setDisable(false);

        // keep it true
        subscribeButton.setDisable(true);
        viewDeviceButton.setDisable(true);
        trackButton.setDisable(true);
        playlistButton.setDisable(true);
        socialButton.setDisable(true);
        radioButton.setDisable(true);
        signoutDeviceButton.setDisable(true);
        closeButton.setDisable(true);

    }

    public static int getAccountID() {
        return accountID;

    }

//     public void gotoTrack(ActionEvent event) {
//         System.out.print("hello");
//         
//        try {
//            
//            ((Node) event.getSource()).getScene().getWindow().hide();
//            Stage primaryStage = new Stage();
//            FXMLLoader loader = new FXMLLoader();
//            Pane root = (Pane) loader.load(getClass().getResource("//FXMLDocument.fxml").openStream());
//            Scene scene = new Scene(root);
//            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
//            primaryStage.setTitle("Plookify");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (Exception e) {
//
//        }
//        
//        System.out.print("hello");
//    }
    public void viewDevice(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/account/ViewDevice.fxml").openStream());
            ViewDeviceController viewDeviceController = (ViewDeviceController) loader.getController();
            viewDeviceController.getUserInfo(USER, PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }
    
    public void goToPlaylist(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/playlist/PLAYLIST.fxml").openStream());
            PLAYLISTController PlaylistController = (PLAYLISTController) loader.getController();
            PlaylistController.getUserInfo(USER,PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }
    
    public void goToRadio(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/radio/gui.fxml").openStream());
            GuiController guiController = (GuiController) loader.getController();
            guiController.getUserInfo(USER,PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }
    
    
    public void goToTrack(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/finaltrackplayer/FXMLDocument.fxml").openStream());
            FXMLDocumentController fxmlDocumentController = (FXMLDocumentController) loader.getController();
            fxmlDocumentController.getUserInfo(USER,PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }
    
    public void goToSocial(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/social/Discoverable.fxml").openStream());
            DiscoverableController discoverableController = (DiscoverableController) loader.getController();
            discoverableController.getUserInfo(USER,PASS, accountID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }

}
