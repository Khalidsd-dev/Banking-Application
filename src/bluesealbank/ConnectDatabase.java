/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bluesealbank;

import bluesealbank.DBPrompt;
import bluesealbank.config.config;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
import javax.swing.JOptionPane;
import settings.AppSettings;

/**
 *
 * @author Shadrack
 */
public class ConnectDatabase {
private Connection conn;
private PreparedStatement pstmt;
private ResultSet rs;

    
    // Constructor to establish the connection at startup
    public ConnectDatabase() {
        try {
            conn = DriverManager.getConnection(config.url, config.user, config.pass);
            System.out.println("Database connected Succesfully!");
            
      
        }
        catch(SQLException ex) {
            System.out.println(config.url + "\n" + config.user + "\n" + config.pass);
            JOptionPane.showMessageDialog(null, "Database conneciton failed:\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
   
    
    /**
     * Verifies if a user exists in a database.
     * @param userID the user entered.
     * @param password the password entered.
     * @return true if credentials are correct, false otherwise.
     */
    public boolean verifyUser(String email, String password) {
        
        boolean exists = false;
        try {
           conn = DriverManager.getConnection(config.url, config.user, config.pass);
          
          String sql = "SELECT COUNT(*) FROM users WHERE email=? AND password=? ";
       
          
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, email);
          pstmt.setString(2, password);
          rs = pstmt.executeQuery();
          
          
          
          if(rs.next() && rs.getInt(1) > 0) {
              exists = true;
              
              String currencySQL = "SELECT Preferred_Currency_Type FROM userdetails WHERE email=?";
              
              PreparedStatement currencyStmt = conn.prepareStatement(currencySQL);
              currencyStmt.setString(1, email);
              ResultSet currencyRs = currencyStmt.executeQuery();
              
              if (currencyRs.next()) {
                  String currencyType = currencyRs.getString("Preferred_Currency_Type");
                  AppSettings.setCurrency(currencyType);
                  System.out.println("Currency loaded: " + currencyType);
          }
              currencyRs.close();
              currencyStmt.close();
          }
          
          pstmt.close();
          rs.close();
          conn.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error while checking user credentials: " + ex.getMessage());
        }
        
        return exists;
    }
    
    
    public boolean withdrawMoney(double amount) throws SQLException {
        
        double availableBalance = UserModel.getBalance(); // Were getting the current balnce from the USerModel class
        
        double result; // This will be your end result of your (current balance - your withdrawal amount) specified.
        
        if (amount > availableBalance) {
            JOptionPane.showMessageDialog(null, "Insufficient Funds");
            System.out.println("Insufficient Funds");
        }
        else{
           result = availableBalance - amount;
        
        try{
            conn = DriverManager.getConnection(config.url, config.user, config.pass);
            
            String withdrawSQL = "UPDATE userdetails SET balance = ? WHERE User_id =?";
            
            pstmt = conn.prepareStatement(withdrawSQL);
            pstmt.setDouble(1, result);
            pstmt.setString(2, UserModel.getUserId());
            int rowsAffected = pstmt.executeUpdate();
            
           if(rowsAffected > 0) {
               UserModel.setBalance((int)result);
               
               // Were keepuing Model in Sync
               System.out.println("Succesfuly withdrew Money from your Account!"); 
               
               return true;
           }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to withdraw Money: " + ex.getMessage());
        }
        
        finally{
           pstmt.close();
           conn.close();
        } 
        }        
    return false;
    }
    
}
