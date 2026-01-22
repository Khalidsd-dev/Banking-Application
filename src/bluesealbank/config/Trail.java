/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bluesealbank.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Shadrack
 */
public class Trail {
    
    private String Department;
    private int value;
    

    
 
    
    public Trail(String Department, int value) {
        this.Department = Department;
        this.value = value;
    }
    
    public Trail() {
        
    }
       
    
    public String getDepartment() {
        return Department;
    }
    
    public void setDepartment(String Department) {
        this.Department = Department;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    // Store history to a file
    
    public void createNewFile() {
        
        try {
            File file = new File("History.txt");
            file.createNewFile();
         
        }
        catch(Exception ex) {
            ex.printStackTrace();
    }

}


}