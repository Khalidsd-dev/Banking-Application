/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package settings;

import bluesealbank.UserModel;
import bluesealbank.dashBoard;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Shadrack
 */
public class AppSettings {
    private static String selectedCurrency = "";
    
    // Currency abbraviation-to-symbol mapping
    private static final Map<String, String> currencySymbols =new HashMap<>();
    
    static {
        currencySymbols.put("USD", "$");
        currencySymbols.put("ZAR", "R");
        currencySymbols.put("EUR", "€");
        currencySymbols.put("GBP", "£");
    }
    
    public static String getCurrency() {
        return selectedCurrency;
    }
    
    public static void setCurrency(String currency) {
        selectedCurrency = currency;
    }
    
    // Get symbol based on stored currency
    protected static String getCurrencySymbol() {
        return currencySymbols.getOrDefault(selectedCurrency, selectedCurrency);
    }
    
    public static void loadData(dashBoard dash) {
        double Balance = UserModel.getBalance();
        dash.lblBalance.setText(AppSettings.getCurrencySymbol() + String.valueOf(Balance));
        dash.lblAmount.setText(AppSettings.getCurrencySymbol() + String.valueOf(Balance));
    }
    
}
