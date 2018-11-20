/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist;
 
 //import java.sql,*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
 /*
 * @author ec14211
 */
 public class connect {
    // public static void main(String[]args){
         //JOptionPane.showMessageDialog(null, "The application works.");
        public static final connect INSTANCE = new connect();
        Connection c = null;
        
        public connect(){
            try {
			c = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Database connection failed!", ex);
		}
        }
        
        public static connect getInstance() {
		return INSTANCE;
	}
	
	public Connection getConnection(){
		return this.c;
	}
        
        
        
        /* 
         *Query for adding a new playlist     
         */
        public void insertlist(String name, String status) {
        
        Statement stat = null;
            try{
                stat = c.createStatement();
                String sql = "INSERT INTO playlist (PlaylistName,PlaylistType)VALUES('" +name + "', '" +status+"')";
                stat.executeUpdate(sql);
                stat.close(); 
                
            }
            catch (SQLException ex) {
                            ex.printStackTrace();
                            throw new RuntimeException("Database connection failed!", ex);
            }                
        }
 }
         
         
      
        
        
      
 