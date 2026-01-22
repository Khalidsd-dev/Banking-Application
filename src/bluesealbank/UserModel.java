/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bluesealbank;

import java.util.Date;

/**
 * Model class for user details.
 * @author Shadrack
 */
public class UserModel {

    
    private static int accountNumber; // Readable ID for users
    private static String userId; // UUID stored as a String
    private static String name;
    private static String surname;
    private static String email;
    private static long idNumber;
    private static String nationality;
    private static String dateOfBirth;
    private static String password;
    private static String gender;
    private static String address;
    private static String preferredAccountType;
    private static String preferredCurrency;
    private static String createdAt;
    private static double balance;
    
   
    
    // Static instance for tracking logged-in User
    private static UserModel currentUser = null;
    
    // custom constrictor
    public UserModel(String email, String password) {
        UserModel.email = email;
        UserModel.password =password;
    }
    
    public UserModel() {
        
    }
    
    // Constructor
    public UserModel(int accountNumber, String userId, String name, String surname, String email, 
                     long idNumber, String nationality, String dateOfBirth, String password, 
                     String gender, String address, String preferredAccountType, 
                     String preferredCurrency, double balance, String createdAt) {
        
        UserModel.accountNumber = accountNumber;
        UserModel.userId = userId;
        UserModel.name = name;
        UserModel.surname = surname;
        UserModel.email = email;
        UserModel.idNumber = idNumber;
        UserModel.nationality = nationality;
        UserModel.dateOfBirth = dateOfBirth;
        UserModel.password = password;
        UserModel.gender = gender;
        UserModel.address = address;
        UserModel.preferredAccountType = preferredAccountType;
        UserModel.preferredCurrency = preferredCurrency;
        UserModel.balance = balance;
        UserModel.createdAt = createdAt;
    }
    
    
    // Getters and Setters
    public static int getAccountNumber() { 
        return accountNumber; 
    }
    
    public static void setAccountNumber(int accountNumber) { 
        UserModel.accountNumber = accountNumber;
    }

    public static String getUserId() { 
        return userId;
    }
    
    public static void setUserId(String userId) {
        UserModel.userId = userId;
    }

    public static String getName() { 
        return name; 
    }
    
    public static void setName(String name) { 
        UserModel.name = name; 
    }
    

    public static String getSurname() { 
        return surname; 
    }
    
    public static void setSurname(String surname) { 
        UserModel.surname = surname;
    }
    

    public static String getEmail() { 
        return email; 
    }
    
    public static void setEmail(String email) { 
        UserModel.email = email;
    }
    

    public static long getIdNumber() { 
        return idNumber; 
    }
    
    public static void setIdNumber(long idNumber) { 
        UserModel.idNumber = idNumber; 
    }
    
    public static String getNationality() {
        return nationality; 
    }
    
    public static void setNationality(String nationality) { 
        UserModel.nationality = nationality; 
    }

    public static String getDateOfBirth() { 
        return dateOfBirth;
    }
    
    public static void setDateOfBirth(String dateOfBirth) { 
        UserModel.dateOfBirth = dateOfBirth;
    }

    public static String getPassword() { 
        return password; 
    }
    public static void setPassword(String password) { 
        UserModel.password = password;
    }

    public static String getGender() { 
        return gender;
    }
    
    public static void setGender(String gender) { 
        UserModel.gender = gender; 
    }

    public static String getAddress() { 
        return address; 
    }
    
    public static void setAddress(String address) {
        UserModel.address = address;
    }
    
    public static String getPreferredAccountType() { 
        return preferredAccountType; 
    }
    
    public static void setPreferredAccountType(String preferredAccountType) { 
        UserModel.preferredAccountType = preferredAccountType; 
    }

    public static String getPreferredCurrency() { 
        return preferredCurrency;
    }
    
    public static void setPreferredCurrency(String preferredCurrency) { 
        UserModel.preferredCurrency = preferredCurrency; 
    }
    
    public static void setBalance(double balance) {
        UserModel.balance = balance;
    }
    
    public static double getBalance() {
        return balance;
    }

    public static String getCreatedAt() { 
        return createdAt;
    }
    
    public static void setCreatedAt(String createdAt) { 
        UserModel.createdAt = createdAt; 
    }
    
    // store the current logged-in User
    public static void setCurrentUser(UserModel user) {
            currentUser = user;
}
    
    // Get the Logged-in user
    public static UserModel getCurrentUser() {
        return currentUser;
    }
}
    

