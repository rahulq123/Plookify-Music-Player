//<<<<<<< Upstream, based on origin/master
/////*
//// * To change this license header, choose License Headers in Project Properties.
//// * To change this template file, choose Tools | Templates
//// * and open the template in the editor.
//// */
////package account;
////
////import javax.swing.JOptionPane;
////
/////**
//// *
//// * @author rahul
//// */
//////
////public class ComponentLoader {
////    
////    public static void main(String [] args)
////    {
////        JOptionPane.showMessageDialog(null, "The applicaton works.");
////        
////        
////       
////    
////    }
////    
////}
//=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package account;
//
//
//import java.sql.*;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author rahul
// */
//public class ComponentLoader {
//    
//    public static void main(String [] args)
//    {
//        Connection c = null;
//    try {
//      Class.forName("org.sqlite.JDBC");
//      c = DriverManager.getConnection("jdbc:sqlite:Account.sqlite");
//    } catch ( Exception e ) {
//      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      System.exit(0);
//    }
//    System.out.println("Opened database successfully");
//
//        JOptionPane.showMessageDialog(null, "The applicaton works.");
//    
//    }
//    
//}
//>>>>>>> 4eb772b 
