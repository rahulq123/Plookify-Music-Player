/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Govind
 */
public class Discoverable {

    private int accountID;
    private double status;

    public Discoverable(int accID, Double accStat) {
        accountID = accID;
        status = accStat;
    }

    public void changeDiscoverable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            if (status == 100) {
                stmt = c.createStatement();
                String sql = "UPDATE Accounts set isDiscoverable = 100 where AccountID=" + accountID + " ;";
                stmt.executeUpdate(sql);
            } else {
                stmt = c.createStatement();
                String sql = "UPDATE Accounts set isDiscoverable = 0 where AccountID=" + accountID + " ;";
                stmt.executeUpdate(sql);
            }

            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

}
