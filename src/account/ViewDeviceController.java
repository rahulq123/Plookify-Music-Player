/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static account.UserController.accountID;

/**
 * FXML Controller class
 *
 * @author rahul
 */
public class ViewDeviceController implements Initializable {

    GetDate date = new GetDate();

    Connection connection = null;
    PreparedStatement prepareStatement = null;
    ResultSet rs = null;
    Statement stmt = null;

    public int ID = UserController.getAccountID();
    public String USER;
    public String deviceName;
    public String deviceYes;
    public String deviceNo;
    public String PASS;
    public String Pstatus;
    public String addedDate;
    public int minusDate;

    @FXML
    private Label username;
    @FXML
    private Label Display1;
    @FXML
    private Label Display2;
    @FXML
    private Label Display3;
    @FXML
    private Label Display4;
    @FXML
    private Label Display5;
    @FXML
    private Label dateStatus;
    @FXML
    private Label DeviceMessage;
    @FXML
    private ComboBox<String> DeviceCombo1;
    @FXML
    private ComboBox<String> DeviceCombo2;
    @FXML
    private ComboBox<String> DeviceCombo3;
    @FXML
    private ComboBox<String> DeviceCombo4;
    @FXML
    private ComboBox<String> DeviceCombo5;

    ObservableList<String> devicelist = FXCollections.observableArrayList("mobile", "pc", "mac", "gamesconsole", "tablet");
    ArrayList<String> DEVICELIST = new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // comboBox 1
        DeviceCombo1.setItems(devicelist);

        // comboBox 2
        DeviceCombo2.setItems(devicelist);

        // comboBox 3
        DeviceCombo3.setItems(devicelist);

        // comboBox 4
        DeviceCombo4.setItems(devicelist);

        // comboBox 5
        DeviceCombo5.setItems(devicelist);

        System.out.println("device: " + accountID);

    }

    public void getUserInfo(String user, String pass, int accID, String paymentStatus) {

        username.setText("User : " + user);
        USER = user;
        PASS = pass;
        ID = accID;
        Pstatus = paymentStatus;

        isDeviceExits();

        if (deviceYes.equals("There is Device")) {

            Connection c = null;
            Statement stmt = null;

            try {

                String userid = Integer.toString(accountID);

                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                rs = stmt.executeQuery("select accountID,DeviceName from Device where accountID IN (Select accountID from Accounts where accountID = '" + userid + "');");

                while (rs.next()) {
                    DEVICELIST.add(rs.getString("DeviceName"));
                    deviceName = rs.getString("DeviceName");
                    System.out.println("showing from iF accountID: " + userid + " deviceName " + deviceName);
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

//             display User Device in the ComboBox 1
            if ((DEVICELIST.get(0).equals("mobile")) || (DEVICELIST.get(0).equals("mac")) || (DEVICELIST.get(0).equals("pc")) || (DEVICELIST.get(0).equals("tablet")) || (DEVICELIST.get(0).equals("gameconsole"))) {
                Display1.setText(DEVICELIST.get(0));

            } else {
                Display1.setText("No Device Added");

            }

//             display User Device in the ComboBox 2
            if ((DEVICELIST.get(1).equals("mobile")) || (DEVICELIST.get(1).equals("mac")) || (DEVICELIST.get(1).equals("pc")) || (DEVICELIST.get(1).equals("tablet")) || (DEVICELIST.get(1).equals("gameconsole"))) {
                Display2.setText(DEVICELIST.get(1));

            } else {
                Display2.setText("No Device Added");

            }

//             display User Device in the ComboBox 3
            if ((DEVICELIST.get(2).equals("mobile")) || (DEVICELIST.get(2).equals("mac")) || (DEVICELIST.get(2).equals("pc")) || (DEVICELIST.get(2).equals("tablet")) || (DEVICELIST.get(2).equals("gameconsole"))) {
                Display3.setText(DEVICELIST.get(2));

            } else {
                Display3.setText("No Device Added");

            }

              // display User Device in the ComboBox 4
            if ((DEVICELIST.get(3).equals("mobile")) || (DEVICELIST.get(3).equals("mac")) || (DEVICELIST.get(3).equals("pc")) || (DEVICELIST.get(3).equals("tablet")) || (DEVICELIST.get(3).equals("gameconsole"))) {
                Display4.setText(DEVICELIST.get(3));

            } else {
               // Display4.setText("No Device Added");

            }
            
              // display User Device in the ComboBox 5
            if ((DEVICELIST.get(4).equals("mobile")) || (DEVICELIST.get(4).equals("mac")) || (DEVICELIST.get(4).equals("pc")) || (DEVICELIST.get(4).equals("tablet")) || (DEVICELIST.get(4).equals("gameconsole"))) {
                Display5.setText(DEVICELIST.get(4));

            } else {
                Display5.setText("No Device Added");

            }
            DeviceMessage.setText("You Have a Device");

        } else {

            DeviceMessage.setText("You Don't Have any Device");

            Display1.setText(null);
            Display2.setText(null);
            Display3.setText(null);
            Display4.setText(null);
            Display5.setText(null);

        }
    }

    public void addDevice(ActionEvent event) throws SQLException {
//        Display1.setText(DeviceCombo1.getValue());
//        Display2.setText(DeviceCombo2.getValue());
//        Display3.setText(DeviceCombo3.getValue());
//        Display4.setText(DeviceCombo4.getValue());
//        Display5.setText(DeviceCombo5.getValue());

        String mydate = date.currentDate();
        String userid = Integer.toString(ID);

        ///////////  ***************   adding 1st device
        try {
            if (DeviceCombo1.getValue().equals("mobile") || (DeviceCombo1.getValue().equals("pc")) || (DeviceCombo1.getValue().equals("mac")) || (DeviceCombo1.getValue().equals("tablet")) || (DeviceCombo1.getValue().equals("gamesconsole"))) {
                Display1.setText(DeviceCombo1.getValue());
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Device values(?,?,?)");
                    prepareStatement.setString(1, userid);
                    prepareStatement.setString(2, DeviceCombo1.getValue());
                    prepareStatement.setString(3, mydate);

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

                }
            } else {
                JOptionPane.showMessageDialog(null, "Combobox 1 is not added");

            }
        } catch (Exception e) {
        }

        ///*********** adding second device
        try {
            if (DeviceCombo2.getValue().equals("mobile") || (DeviceCombo2.getValue().equals("pc")) || (DeviceCombo2.getValue().equals("mac")) || (DeviceCombo2.getValue().equals("tablet")) || (DeviceCombo2.getValue().equals("gamesconsole"))) {
                Display2.setText(DeviceCombo2.getValue());
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Device values(?,?,?)");
                    prepareStatement.setString(1, userid);
                    prepareStatement.setString(2, DeviceCombo2.getValue());
                    prepareStatement.setString(3, mydate);

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

                }
            } else {
                JOptionPane.showMessageDialog(null, "Combobox 1 is not added");

            }
        } catch (Exception e) {
        }

        //*********** adding Third device
        try {
            if (DeviceCombo3.getValue().equals("mobile") || (DeviceCombo3.getValue().equals("pc")) || (DeviceCombo3.getValue().equals("mac")) || (DeviceCombo3.getValue().equals("tablet")) || (DeviceCombo3.getValue().equals("gamesconsole"))) {
                Display3.setText(DeviceCombo3.getValue());
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Device values(?,?,?)");
                    prepareStatement.setString(1, userid);
                    prepareStatement.setString(2, DeviceCombo3.getValue());
                    prepareStatement.setString(3, mydate);

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

                }
            } else {
                JOptionPane.showMessageDialog(null, "Combobox 1 is not added");

            }
        } catch (Exception e) {
        }

        //*********** adding Fourth device
        try {
            if (DeviceCombo4.getValue().equals("mobile") || (DeviceCombo4.getValue().equals("pc")) || (DeviceCombo4.getValue().equals("mac")) || (DeviceCombo4.getValue().equals("tablet")) || (DeviceCombo4.getValue().equals("gamesconsole"))) {
                Display4.setText(DeviceCombo4.getValue());
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Device values(?,?,?)");
                    prepareStatement.setString(1, userid);
                    prepareStatement.setString(2, DeviceCombo4.getValue());
                    prepareStatement.setString(3, mydate);

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

                }
            } else {
                JOptionPane.showMessageDialog(null, "Combobox 1 is not added");

            }
        } catch (Exception e) {
        }

        //*********** adding Fifth device
        try {
            if (DeviceCombo5.getValue().equals("mobile") || (DeviceCombo5.getValue().equals("pc")) || (DeviceCombo5.getValue().equals("mac")) || (DeviceCombo5.getValue().equals("tablet")) || (DeviceCombo5.getValue().equals("gamesconsole"))) {
                Display5.setText(DeviceCombo5.getValue());
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
                    prepareStatement = connection.prepareStatement("insert into Device values(?,?,?)");
                    prepareStatement.setString(1, userid);
                    prepareStatement.setString(2, DeviceCombo5.getValue());
                    prepareStatement.setString(3, mydate);

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

                }
            } else {
                JOptionPane.showMessageDialog(null, "Combobox 1 is not added");

            }
        } catch (Exception e) {
        }

    }

    public void replaceDevice(ActionEvent event) {

        String mydate = date.currentDate();
        String userid = Integer.toString(ID);

        try {
            getAddedDate();
            minusAddedDateWithCurrentDate();

        } catch (Exception e) {
        }

        try {

            if (minusDate <= 31) {

                dateStatus.setText("Your device can only be replaced once per month. Its only been " + minusDate + "\n days since you added your device.");

            } else {

            }

        } catch (Exception e) {
        }

    }

    public void goBackToHome(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/account/User.fxml").openStream());
            UserController userController = (UserController) loader.getController();
            userController.getUserInfo(USER, PASS, ID, Pstatus);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass).getResource("application.css").toExternalForm());
            primaryStage.setTitle("Plookify");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }

    }

    public void isDeviceExits() {

        Connection c = null;
        Statement stmt = null;

        try {

            String userid = Integer.toString(accountID);

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select accountID,DeviceName from Device where accountID IN (Select accountID from Accounts where accountID = '" + userid + "');");

            while (rs.next()) {
                deviceName = rs.getString("DeviceName");

                if (deviceName.equals("mobile") || (deviceName.equals("mac")) || (deviceName.equals("pc")) || (deviceName.equals("gameconsole")) || (deviceName.equals("tablet"))) {
                    System.out.println("accountID: " + userid + " deviceName " + deviceName);
                    deviceYes = "There is Device";
                    DeviceMessage.setText("There is Device");
                } else {
                    deviceNo = "No Device";
                    DeviceMessage.setText("No Device");

                }

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

    public void getAddedDate() {
        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs = stmt.executeQuery("select DeviceAddedDate from Device where accountID= '" + accountID + "';");

            while (rs.next()) {
                addedDate = rs.getString("DeviceAddedDate");
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

    public void minusAddedDateWithCurrentDate() throws ParseException {

        try {
            minusDate = date.minusAddedDate(addedDate);
        } catch (Exception e) {
        }
    }

}
