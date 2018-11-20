package account;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rahul
 */
public class LoginController implements Initializable {

    //public LoginModel loginmodel = new LoginModel();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Label isConnected;
    @FXML
    private TextField txtusername;
    @FXML
    private PasswordField txtpassword;

    public String paymentStatus;
    public String USER;
    public int accountID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        if (loginmodel.isDbConnected()) {
//            //isConnected.setText("Connected");
//        } else {
//            //isConnected.setText("Not Connected");
//        }
    }

    @FXML
    public void Login(ActionEvent event) throws SQLException, ParseException {

        try {
            if (isLogin(txtusername.getText(), txtpassword.getText())) {
                isConnected.setText("username and password is correct");

                USER = txtusername.getText();
                getID(USER);
                System.out.println(accountID);

                Connection c = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {

                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    c.setAutoCommit(false);
                    //System.out.println("Opened database successfully");

                    stmt = c.createStatement();
                    rs = stmt.executeQuery("select PaymentStatus from Payment where accountID = '" + accountID + "';");

                    while (rs.next()) {
                        paymentStatus = rs.getString("PaymentStatus");
                        System.out.println("PaymentStatus = " + paymentStatus);
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

                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = (Pane) loader.load(getClass().getResource("/account/User.fxml").openStream());
                    UserController userController = (UserController) loader.getController();
                    userController.getUserInfo(txtusername.getText(),txtpassword.getText(), accountID, paymentStatus);
                    Scene scene = new Scene(root);
                    //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
                    primaryStage.setTitle("Plookify");
                    primaryStage.setScene(scene);
                    primaryStage.show();

                }

            } else {
                isConnected.setText("username and password is not correct");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void signUp(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/account/SignUp.fxml").openStream());
//                SignUpController signupController =  (SignUpController)loader.getController();
//                signupController.getUser(txtusername.getText());
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }

    public boolean isLogin(String user, String pass) throws SQLException {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            String query = "select * from Accounts where username = ? and password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                preparedStatement.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connection.close();
            } catch (Exception e) { /* ignored */ }

        }

    }

    public void getID(String user) {

        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select accountID from Accounts where username=  '"+ USER +"' ;");

            while (rs.next()) {
                accountID = rs.getInt("accountID");
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
