/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bluesealbank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Shadrack
 */
public class RememberMeUtil {
    
    
    private static final String FILE_NAME = "config.properties";
    
    public static void saveRememberMe(String email, String password) {
        try (FileOutputStream output = new FileOutputStream(FILE_NAME)) {
            Properties prop = new Properties();
            prop.setProperty("email", email);
            prop.setProperty("password", password);
            prop.setProperty("rememberMe", "true");
            prop.store(output, "User Login Info");
            
        } catch (IOException ex) {
            Logger.getLogger(RememberMeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Properties loadRememberMe() {
        Properties prop = new Properties();
        try(FileInputStream input = new FileInputStream(FILE_NAME)) {
            prop.load(input);
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "No remembered login found");
        }
        return prop;
    }
    
    public static void clearRememberMe() {
        try(FileOutputStream output = new FileOutputStream(FILE_NAME)) {
            Properties prop = new Properties();
            prop.setProperty("rememberMe", "false");
            prop.store(output, "Clear Remeember Me");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
