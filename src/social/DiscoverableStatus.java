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
public class DiscoverableStatus {
    
    private double status;
    private int accountID;
    
    public DiscoverableStatus(int accID){
        accountID = accID;
    }
    
    public double checkDiscoverable() {
        double stat = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT isDiscoverable FROM Accounts where accountID=" + accountID + ";");
            while (rs.next()) {
                stat = rs.getDouble("isDiscoverable");

            }
            status = stat;
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return status;
    }

}
