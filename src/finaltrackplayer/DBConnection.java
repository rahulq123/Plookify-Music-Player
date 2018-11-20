package finaltrackplayer;

import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Sami
 */
public class DBConnection {
    Connection conn = null;
    
    public static Connection dbConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            return conn;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}