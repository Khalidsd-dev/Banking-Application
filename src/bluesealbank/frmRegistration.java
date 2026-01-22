/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bluesealbank;

import bluesealbank.config.config;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import settings.AppSettings;

/**
 *
 * @author Shadrack
 */
public class frmRegistration extends javax.swing.JFrame {

    
    /**
     * Creates new form frmRegistration
     */
    
   
    private Connection conn;
    private boolean passVisible = false;
    
    
    public frmRegistration() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/bank (1).png"));
        setIconImage(icon.getImage());
        setTitle(" BlueSeal Bank");
        
        // Set Submit button color
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(Color.decode("#EC6F66"));
        btnSubmit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#EC6F66")));
        
        // Add Currency checboxes to Button Group
        ButtonGroup currencyButtonGroup = new ButtonGroup();
        currencyButtonGroup.add(cbUSD);
        currencyButtonGroup.add(cbZAR);
        currencyButtonGroup.add(cbEUR);
        currencyButtonGroup.add(cbGBP);
        
        // Add Account Type Checkboxes to ButtonGroup
        ButtonGroup accountButtongroup = new ButtonGroup();
        accountButtongroup.add(cbSavings);
        accountButtongroup.add(cbChecking);
        accountButtongroup.add(cbCredit);
        
        
        this.setResizable(false);
        
    }
    
    // Function to get selected currency
    public String getSelectedCurrency() {
        if (cbUSD.isSelected()) {
            return "USD";
        }
        else if (cbZAR.isSelected()) {
            return "ZAR";
        } 
        else if (cbEUR.isSelected()) {
            return "EUR";
        } 
        else if (cbGBP.isSelected()) {
            return "GBP";
        }
        return ""; // Default empty string if none is selected
    }
    
    private void registerUser() {
        // Set selected currency globally
        AppSettings.setCurrency(getSelectedCurrency());
        
        String name = txtFullname.getText();
        String surname = txtSurname.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String accoutType = "";
        String currency = AppSettings.getCurrency();
        String password = pPassword.getText();
        String gender = cbGender.getSelectedItem().toString();
        int Balance = 0;
        
        String nationality = cbNationality.getSelectedItem().toString();
        String idNumber = txtIdNumber.getText().trim();
        
        String dobString = txtDate.getText().trim();
        Date dob = convertToSqlDate(dobString);
        
        // Retrieve selected account type
        if(cbSavings.isSelected()) {
            accoutType = "Savings";
        }
        else if(cbChecking.isSelected()) {
            accoutType = "Checking";
        }
        else if(cbCredit.isSelected()) {
            accoutType = "Credit";
        }
        
        //Generate UUID (java UUID format) and convert to bytes for mysql BINARY(16)
        UUID userUuid = UUID.randomUUID();
        byte[] userUuidBytes = uuidToBytes(userUuid);
        
        String UserQuery = "INSERT INTO Users (User_id, Name, Email, Password, balance) VALUES (?, ?, ?, ?, ?)";
        String detailsQuery = "INSERT INTO UserDetails (Detail_id, User_id, Date_of_Birth, Residential_Address, Preferred_Account_Type, Preferred_Currency_Type, Nationality, ID_Number, Surname, Email, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
        // Storing the email amnd password in the usermodel    
        UserModel.setEmail(email);
        UserModel.setPassword(password);

        try{
            
             conn = DriverManager.getConnection(config.url, config.user, config.pass);
            
             PreparedStatement userStmt = conn.prepareStatement(UserQuery);
             PreparedStatement detailsStmt = conn.prepareStatement(detailsQuery);
             
             // insert into Users table
             userStmt.setBytes(1, userUuidBytes);
             userStmt.setString(2, name);
             userStmt.setString(3, email);
             userStmt.setString(4, password);
             userStmt.setInt(5, Balance);
             userStmt.executeUpdate();
             
             
             // insert into userdetails table
             detailsStmt.setBytes(1, uuidToBytes(UUID.randomUUID()));// unique Details_id
             detailsStmt.setBytes(2, userUuidBytes);
             detailsStmt.setDate(3, dob);
             detailsStmt.setString(4, address);
             detailsStmt.setString(5, accoutType);
             detailsStmt.setString(6, currency);
             detailsStmt.setString(7, nationality);
             detailsStmt.setString(8, idNumber);
             detailsStmt.setString(9, surname);
             detailsStmt.setString(10, email);
             detailsStmt.setString(11, gender);
             detailsStmt.executeUpdate();
             
             JOptionPane.showMessageDialog(null, "User registered successfully!");
             frmlogin login = new frmlogin();
             login.show();
             this.hide();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // Converts UUID to byte array for MySQL BINARY(16)
    private byte[] uuidToBytes(UUID uuid) {
        byte[] bytes = new byte[16];
        System.arraycopy(longToBytes(uuid.getMostSignificantBits()), 0, bytes, 0, 8);
        System.arraycopy(longToBytes(uuid.getLeastSignificantBits()), 0, bytes, 8, 8);
        return bytes;
    }
    
    private static byte[] longToBytes(long value) {
        byte[] bytes = new byte[8];
        for(int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }
        
    // Method to convert java.date.util to java.sql.date
    public Date convertToSqlDate(String dateStr) {
        // in this code there is a logical bug, basically if no date unput has been specified it throws an error
       
        try {
            
            if(dateStr.isBlank()) {
             System.out.println("No Date has been specified!");   
            }
            // Convert string (DD/MM/YYYY) -> java.util.Date
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            inputFormat.setLenient(false); // Strict validation
            
            // Convert java.util.Date to java.sql.Date
            java.util.Date parsedDate = inputFormat.parse(dateStr);
            
            // Convert java.util.Date -> java.sql.Date (MySQl format YYYY-MM-DD)
            return new java.sql.Date(parsedDate.getTime());
            
        }
        catch(ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtFullname = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbSavings = new javax.swing.JCheckBox();
        cbChecking = new javax.swing.JCheckBox();
        cbCredit = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        cbUSD = new javax.swing.JCheckBox();
        cbZAR = new javax.swing.JCheckBox();
        cbEUR = new javax.swing.JCheckBox();
        cbGBP = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        cbNationality = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtIdNumber = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lblPolicy = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        pPassword = new javax.swing.JPasswordField();
        lblLock = new javax.swing.JLabel();
        lblBack = new javax.swing.JLabel();
        lblDateMessage = new javax.swing.JLabel();
        lblEmailMessage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbGender = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Account Registration");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 28, 553, 47));

        txtEmail.setToolTipText("");
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 246, 441, 39));

        txtSurname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 149, 442, 39));

        txtAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });
        jPanel1.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 351, 406, 39));

        txtFullname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtFullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 149, 380, 39));

        txtDate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDateFocusLost(evt);
            }
        });
        txtDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDateMouseClicked(evt);
            }
        });
        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });
        jPanel1.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 246, 191, 39));

        jLabel2.setText("Full Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 121, -1, -1));

        jLabel3.setText("Surname");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 121, -1, -1));

        jLabel4.setText("Date of Birth  (DD / MM / YYYY)");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 218, 224, -1));

        jLabel5.setText("Email Address");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 218, -1, -1));

        jLabel6.setText("Residential Address");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 329, -1, -1));

        jLabel7.setText("Preffered Account Type");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 419, -1, -1));

        cbSavings.setBackground(new java.awt.Color(255, 255, 255));
        cbSavings.setText("Savings");
        jPanel1.add(cbSavings, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 453, -1, -1));

        cbChecking.setBackground(new java.awt.Color(255, 255, 255));
        cbChecking.setText("Checking");
        jPanel1.add(cbChecking, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 453, -1, -1));

        cbCredit.setBackground(java.awt.Color.white);
        cbCredit.setText("Credit");
        jPanel1.add(cbCredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 453, -1, -1));

        jLabel9.setText("Preffered Currentcy Type");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 419, -1, -1));

        cbUSD.setBackground(new java.awt.Color(255, 255, 255));
        cbUSD.setText("USD");
        jPanel1.add(cbUSD, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 453, -1, -1));

        cbZAR.setBackground(new java.awt.Color(255, 255, 255));
        cbZAR.setText("ZAR");
        jPanel1.add(cbZAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 453, -1, -1));

        cbEUR.setBackground(new java.awt.Color(255, 255, 255));
        cbEUR.setText("EUR");
        jPanel1.add(cbEUR, new org.netbeans.lib.awtextra.AbsoluteConstraints(542, 453, -1, -1));

        cbGBP.setBackground(new java.awt.Color(255, 255, 255));
        cbGBP.setText("GBP");
        jPanel1.add(cbGBP, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 453, -1, -1));

        jLabel10.setText("Nationality");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 505, -1, -1));

        cbNationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "South Africa", "America", "Canada", "England", "Portugal", "argentina", "Spain", "Brazil", "South Arabia", "South Korea", "North Korea", "India", "New zealand", "Australia" }));
        jPanel1.add(cbNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 527, 227, 31));

        jLabel11.setText("ID Number");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 505, 60, -1));

        txtIdNumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtIdNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 528, 443, 31));

        jLabel13.setText("<html>\n<style>\n\nbody {\n     color:#0575E6;\n}\n\nb{\n  color: #fc4a1a;\n}\n\n\n</style>\n<body>\n<p>I confirm that the information provided is accurate and agree to the<b> terms and conditions</b> of the bank.\n</p>\n</body>\n</html>");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 565, 785, 28));

        lblPolicy.setForeground(Color.decode("#fc4a1a"));
        lblPolicy.setText("Terms & Conditions");
        lblPolicy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPolicy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPolicyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPolicyMouseExited(evt);
            }
        });
        jPanel1.add(lblPolicy, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 599, -1, -1));

        btnSubmit.setText("Submit");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmitMouseExited(evt);
            }
        });
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jPanel1.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 627, 357, 37));

        jLabel15.setText("Password");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 329, -1, -1));

        pPassword.setForeground(new java.awt.Color(102, 102, 102));
        pPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0))));
        pPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pPasswordMouseClicked(evt);
            }
        });
        jPanel1.add(pPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 351, 374, 32));

        lblLock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/show.png"))); // NOI18N
        lblLock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLockMouseClicked(evt);
            }
        });
        jPanel1.add(lblLock, new org.netbeans.lib.awtextra.AbsoluteConstraints(829, 313, -1, -1));

        lblBack.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBack.setText("<");
        lblBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackMouseClicked(evt);
            }
        });
        jPanel1.add(lblBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 15, 24, -1));

        lblDateMessage.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(lblDateMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 297, 322, 26));

        lblEmailMessage.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(lblEmailMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 297, 322, 26));

        jLabel8.setText("Gender");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 218, 86, -1));

        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", " " }));
        cbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGenderActionPerformed(evt);
            }
        });
        jPanel1.add(cbGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 246, 174, 39));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void pPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pPasswordMouseClicked
        // TODO add your handling code here:

        pPassword.setText("");
        pPassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_pPasswordMouseClicked

    private void lblLockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLockMouseClicked
        // TODO add your handling code here:
        passVisible = !passVisible;
        updateImage();

    }//GEN-LAST:event_lblLockMouseClicked

    private void lblPolicyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPolicyMouseEntered
        // TODO add your handling code here:
        
        lblPolicy.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#fc4a1a")));
    }//GEN-LAST:event_lblPolicyMouseEntered

    private void lblPolicyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPolicyMouseExited
        // TODO add your handling code here:
        
        lblPolicy.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#fc4a1a")));
    }//GEN-LAST:event_lblPolicyMouseExited

    private void lblBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseClicked
        // TODO add your handling code here:
        
        // back to login form
        frmlogin login = new frmlogin();
        login.show();
        this.hide();
        
    }//GEN-LAST:event_lblBackMouseClicked

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        
        // Register the user
        registerUser();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnSubmitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseEntered
        // TODO add your handling code here:
        btnSubmit.setForeground(Color.BLACK);
        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    }//GEN-LAST:event_btnSubmitMouseEntered

    private void btnSubmitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseExited
        // TODO add your handling code here:
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(Color.decode("#EC6F66"));
        btnSubmit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#EC6F66")));
    }//GEN-LAST:event_btnSubmitMouseExited

    private void txtDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDateMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtDateMouseClicked

    private void txtDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDateFocusLost
        // TODO add your handling code here:
        String datePattern = "\\d{2}/\\d{2}/\\d{4}";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(txtDate.getText());
        
        if(!matcher.matches()) {
            
            lblDateMessage.setText("Invalid Date Format");
            // Clear invalid input
            txtDate.setText("");
        }
        else {
            lblDateMessage.setText(""); // Clear error messsage 
        }
    }//GEN-LAST:event_txtDateFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost

        // TODO add your handling code here:
        if(!isValidEmail(txtEmail.getText().trim())) {
            lblEmailMessage.setText("Invalid Email!, Please try Again");
            txtEmail.setText(""); // Clear invalid input
        }
        else{
            lblEmailMessage.setText("");
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void cbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGenderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JCheckBox cbChecking;
    private javax.swing.JCheckBox cbCredit;
    private javax.swing.JCheckBox cbEUR;
    private javax.swing.JCheckBox cbGBP;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbNationality;
    private javax.swing.JCheckBox cbSavings;
    private javax.swing.JCheckBox cbUSD;
    private javax.swing.JCheckBox cbZAR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblDateMessage;
    private javax.swing.JLabel lblEmailMessage;
    private javax.swing.JLabel lblLock;
    private javax.swing.JLabel lblPolicy;
    private javax.swing.JPasswordField pPassword;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtIdNumber;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables

    private void updateImage() {
        if(passVisible) {
            ImageIcon cn = new ImageIcon(getClass().getResource("/icons/hide.png"));
            lblLock.setIcon(cn);
            pPassword.setEchoChar('\u0000');
        }
        else {
            ImageIcon cn = new ImageIcon(getClass().getResource("/icons/show.png"));
            lblLock.setIcon(cn);
            pPassword.setEchoChar('*');
        }
            }
    
    public static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }
    
    
    
}


