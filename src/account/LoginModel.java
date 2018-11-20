/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rahul
 */
public class LoginModel {

//    Connection connection = null;
//    PreparedStatement preparedStatement = null;
//    ResultSet resultSet = null;
//
//    public LoginModel() {
//        connection = SqliteConnection.Connector();
//        if (connection == null) {
//            System.out.println("connection not successful");
//            System.exit(1);
//
//        }
//    }
//
//    public boolean isDbConnected() {
//        try {
//            return !connection.isClosed();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean isLogin(String user, String pass) throws SQLException {
//
//        String query = "select * from Accounts where username = ? and password = ?";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, user);
//            preparedStatement.setString(2, pass);
//            resultSet = preparedStatement.executeQuery();
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
//                preparedStatement.close();
//            } catch (Exception e) { /* ignored */ }
//            try {
//                connection.close();
//            } catch (Exception e) { /* ignored */ }
//        }
//
//    }

}
