/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bluesealbank;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import panels.pnAccountDetails;
import bluesealbank.BlueSealBank;
import bluesealbank.config.Trail;
import bluesealbank.config.config;
import java.awt.PopupMenu;
import java.sql.*;
import javax.swing.JFrame;
import panels.Deposit;
import panels.Withdraw;
import settings.AppSettings;

/**
 *
 * @author Shadrack
 */
public class dashBoard extends javax.swing.JFrame {

    private BufferedImage image;
    //Track expansion state
    private final boolean isExpanded =  false;
    private JPanel pnAccountDetails;
    public JPanel pnDeposit;
    private int balance;
    public AppSettings settings;
    
   
    
    // JDB Object
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    
    /**
     * Creates new form dashBoard
     */
    public dashBoard() {
        initComponents();
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/bank (1).png"));
        setIconImage(icon.getImage());
        setTitle("BlueSeal Bank");
        setResizable(false);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
       // display User full name in Label at top
       lblUser.setText(" " + UserModel.getName() + " " + UserModel.getSurname());
       
       getBalance();
       
       // Show account details
       getDetails();
       
       /**
        * The Trail class will get all the breadcrumbs you leave when you do a transaction
        * 
        */
       Trail trail = new Trail();
       trail.createNewFile(); //       trail.getInfo();
       
//       updateBalance(UserModel.getBalance());
    }
    
    public void chooseUserIcon() {
        
        try {
            // look And Feel by OS
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Open file chooser to select an image
            JFileChooser ch = new JFileChooser();
            ch.setDialogTitle("Select Image");
        
        // Add filter Images
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif");
        ch.setFileFilter(filter);
        
        // Show Dialog
        int result = ch.showOpenDialog(this);
        
        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = ch.getSelectedFile();
            
            try {
                // Load and scale image
                image = ImageIO.read(selectedFile);
                Image scaledImage = scaleImage(image, lblIcon.getWidth(), lblIcon.getHeight());
                
                // Set the scaled image to the label with circular clipping
                lblIcon.setIcon(new ImageIcon(createCircularImage(scaledImage)));
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            
        }
        else {
            JOptionPane.showMessageDialog(null,"No File Chooser!");
        }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(dashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private Image scaleImage(BufferedImage originalImage, int width, int height) {
        //scale the image to fit within the label's size
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    private BufferedImage createCircularImage(Image img) {
        // Create a circular BufferedImage
        int diameter = Math.min(img.getWidth(null), img.getHeight(null));
        BufferedImage circleImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleImage.createGraphics();
        
        // Set the clipping region to a circle
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        
        return circleImage;
    }
    
    // load Image method - coming soon
    
    
     // Connects to database , fetch the data then return and save the data locally for reusability
    private void getBalance() {
      
        try{
           conn = DriverManager.getConnection(config.url, config.user, config.pass);
           
           String balanceQuery = "SELECT Balance From userdetails WHERE ID_Number=?";
           pstmt = conn.prepareStatement(balanceQuery);
           pstmt.setLong(1, UserModel.getIdNumber());
           rs = pstmt.executeQuery();
           
           while(rs.next()) {
               balance = rs.getInt("balance");
               System.out.println("Saved!");
           }
           
           UserModel.setBalance(balance);
           
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        

    }
    
    public void updateBalance(){
        AppSettings.loadData(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        pnDashBoard = new javax.swing.JPanel();
        sidePanel2 = new panels.sidePanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        lblID = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblWithdraw = new javax.swing.JLabel();
        lblDepo = new javax.swing.JLabel();
        lblHistory = new javax.swing.JLabel();
        pnDetails = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblAcc = new javax.swing.JLabel();
        lblTlimit = new javax.swing.JLabel();
        lblWlimit = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        lblCreation = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        pnAccountDetails1 = new panels.pnAccountDetails();
        pndepo = new javax.swing.JPanel();
        deposit1 = new panels.Deposit();
        jPanel5 = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblLogOut = new javax.swing.JLabel();
        mnBar = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        mnAvatar = new javax.swing.JMenu();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnDashBoard.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Account");

        javax.swing.GroupLayout sidePanel2Layout = new javax.swing.GroupLayout(sidePanel2);
        sidePanel2.setLayout(sidePanel2Layout);
        sidePanel2Layout.setHorizontalGroup(
            sidePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );
        sidePanel2Layout.setVerticalGroup(
            sidePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Current Account Summary");

        jLabel3.setText("Account ID");

        jLabel4.setText("Amount");

        jLabel5.setText("Type");

        lblID.setText("jLabel6");

        lblAmount.setText("jLabel6");

        lblType.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAmount)))
                .addGap(7, 7, 7)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblType))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setOpaque(false);

        lblWithdraw.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWithdraw.setText("Withdraw");
        lblWithdraw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWithdrawMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblWithdrawMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblWithdrawMouseExited(evt);
            }
        });

        lblDepo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDepo.setText("Deposit");
        lblDepo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDepoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDepoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDepoMouseExited(evt);
            }
        });

        lblHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHistory.setText("Transaction History");
        lblHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHistoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHistoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHistoryMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDepo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblWithdraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblDepo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblWithdraw, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Account");

        jLabel8.setText("Transaction limit");

        jLabel9.setText("Date Created");

        jLabel11.setText("Withdrawal Limit");

        jLabel10.setText("Balance");

        lblAcc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAcc.setText("jLabel12");

        lblTlimit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTlimit.setText("jLabel13");

        lblWlimit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWlimit.setText("jLabel14");

        lblBalance.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBalance.setText("jLabel15");

        lblCreation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCreation.setText("jLabel16");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCreation, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAcc, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(146, 146, 146)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTlimit, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWlimit, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 374, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAcc, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lblTlimit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWlimit)
                    .addComponent(lblBalance))
                .addGap(35, 35, 35)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(lblCreation)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnDetails.addTab("Balance", jPanel1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnAccountDetails1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnAccountDetails1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
        );

        pnDetails.addTab("Info", jPanel7);

        javax.swing.GroupLayout pndepoLayout = new javax.swing.GroupLayout(pndepo);
        pndepo.setLayout(pndepoLayout);
        pndepoLayout.setHorizontalGroup(
            pndepoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deposit1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
        );
        pndepoLayout.setVerticalGroup(
            pndepoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pndepoLayout.createSequentialGroup()
                .addComponent(deposit1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnDetails.addTab("Transfer", pndepo);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profile.png"))); // NOI18N
        jPanel5.add(lblIcon);

        lblUser.setText(" Sam Mustang");
        jPanel5.add(lblUser);

        jPanel6.setLayout(new java.awt.BorderLayout());

        lblLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        lblLogOut.setText("Log-Out");
        lblLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogOutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogOutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLogOutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnDashBoardLayout = new javax.swing.GroupLayout(pnDashBoard);
        pnDashBoard.setLayout(pnDashBoardLayout);
        pnDashBoardLayout.setHorizontalGroup(
            pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDashBoardLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(sidePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(373, 373, 373)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDashBoardLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblLogOut)))
            .addGroup(pnDashBoardLayout.createSequentialGroup()
                .addGroup(pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDashBoardLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDashBoardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(pnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnDashBoardLayout.setVerticalGroup(
            pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDashBoardLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDashBoardLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(sidePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogOut))
                .addGap(3, 3, 3)
                .addGroup(pnDashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDashBoardLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jMenu3.setText("Settings");

        mnAvatar.setBorder(null);
        mnAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user (1).png"))); // NOI18N
        mnAvatar.setText("Change Avatar");
        mnAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnAvatarMouseClicked(evt);
            }
        });
        mnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAvatarActionPerformed(evt);
            }
        });
        jMenu3.add(mnAvatar);

        mnBar.add(jMenu3);

        setJMenuBar(mnBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
// Set opaque true to show the new changed color of foreground and background
    private void lblHistoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoryMouseEntered
        // TODO add your handling code here:
     
        lblHistory.setForeground(Color.WHITE);
        lblHistory.setBackground(Color.decode("#ACBB78"));
        lblHistory.setOpaque(true);
    }//GEN-LAST:event_lblHistoryMouseEntered

    private void lblHistoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoryMouseExited
        // TODO add your handling code here:
        lblHistory.setForeground(Color.BLACK);
        lblHistory.setBackground(Color.WHITE);
    }//GEN-LAST:event_lblHistoryMouseExited

    private void lblDepoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDepoMouseEntered
        // TODO add your handling code here:
        lblDepo.setForeground(Color.WHITE);
        lblDepo.setBackground(Color.decode("#ACBB78"));
        lblDepo.setOpaque(true);
    }//GEN-LAST:event_lblDepoMouseEntered

    private void lblDepoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDepoMouseExited
        // TODO add your handling code here:
        lblDepo.setForeground(Color.BLACK);
        lblDepo.setBackground(Color.WHITE);
    }//GEN-LAST:event_lblDepoMouseExited

    private void lblWithdrawMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWithdrawMouseEntered
        // TODO add your handling code here:
        lblWithdraw.setForeground(Color.WHITE);
        lblWithdraw.setBackground(Color.decode("#ACBB78"));
        lblWithdraw.setOpaque(true);
    }//GEN-LAST:event_lblWithdrawMouseEntered

    private void lblWithdrawMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWithdrawMouseExited
        // TODO add your handling code here:
        lblWithdraw.setForeground(Color.BLACK);
        lblWithdraw.setBackground(Color.WHITE);
    }//GEN-LAST:event_lblWithdrawMouseExited

    private void mnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAvatarActionPerformed
        // TODO add your handling code here:
        
        chooseUserIcon();
    }//GEN-LAST:event_mnAvatarActionPerformed

    private void mnAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnAvatarMouseClicked
        // TODO add your handling code here:
        chooseUserIcon();
    }//GEN-LAST:event_mnAvatarMouseClicked

    private void lblLogOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogOutMouseEntered
        // TODO add your handling code here:
        lblLogOut.setForeground(Color.RED);
    }//GEN-LAST:event_lblLogOutMouseEntered

    private void lblLogOutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogOutMouseExited
        // TODO add your handling code here:
        lblLogOut.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblLogOutMouseExited

    private void lblLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogOutMouseClicked
        // TODO add your handling code here:
        RememberMeUtil.clearRememberMe();
        JOptionPane.showMessageDialog(null, "Succesfully Logged-Out!");
        frmlogin login = new frmlogin();
        login.setVisible(true);
        this.hide();
    }//GEN-LAST:event_lblLogOutMouseClicked

    private void lblDepoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDepoMouseClicked
        // TODO add your handling code here:
        // Display the Deposit window / frame
        frmDeposit depo = new frmDeposit();
        depo.show();
        this.hide();
        
       
        
    }//GEN-LAST:event_lblDepoMouseClicked

    private void lblHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoryMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_lblHistoryMouseClicked

    private void lblWithdrawMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWithdrawMouseClicked
        // TODO add your handling code here:
        
        Transaction action = new Transaction();
        action.show();
        this.hide();
    }//GEN-LAST:event_lblWithdrawMouseClicked

  
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
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashBoard().setVisible(true);
            }
        });
    }
    
    private void getDetails() {
        
        String type = UserModel.getPreferredAccountType();
        int accNumber = UserModel.getAccountNumber();
        String createdDate = UserModel.getCreatedAt();
        
        
        
        lblID.setText(String.valueOf(accNumber));
        
        lblType.setText(type);
        lblAcc.setText(type);
        
        // limits Hardcoded for educational purposes only: in general this is a bad practice in software development.
        lblTlimit.setText("R 10 000");
        lblWlimit.setText("R 2 000");
        lblCreation.setText(createdDate);
        AppSettings.loadData(this);
    }
    
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private panels.Deposit deposit1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblAcc;
    public static javax.swing.JLabel lblAmount;
    public javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblCreation;
    private javax.swing.JLabel lblDepo;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLogOut;
    private javax.swing.JLabel lblTlimit;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblWithdraw;
    private javax.swing.JLabel lblWlimit;
    private javax.swing.JMenu mnAvatar;
    private javax.swing.JMenuBar mnBar;
    private panels.pnAccountDetails pnAccountDetails1;
    private javax.swing.JPanel pnDashBoard;
    private javax.swing.JTabbedPane pnDetails;
    private javax.swing.JPanel pndepo;
    private panels.sidePanel sidePanel2;
    // End of variables declaration//GEN-END:variables
}



