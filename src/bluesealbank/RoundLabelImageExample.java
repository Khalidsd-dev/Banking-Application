/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bluesealbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RoundLabelImageExample extends JFrame {
    private RoundLabel lblImage;
    private BufferedImage image;

    public RoundLabelImageExample() {
        setTitle("Round Image Label Example");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create custom round label to hold the image
        lblImage = new RoundLabel();
        lblImage.setBounds(50, 50, 150, 150); // Set the label size
        lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(lblImage);

        // Button to open file chooser and select an image
        JButton btnChooseImage = new JButton("Choose Image");
        btnChooseImage.setBounds(50, 220, 150, 30);
        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });
        add(btnChooseImage);

        setVisible(true);
    }

    private void chooseImage() {
        // Open file chooser to select an image
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Load and scale the image
                image = ImageIO.read(selectedFile);
                Image scaledImage = scaleImage(image, lblImage.getWidth(), lblImage.getHeight());
                // Set the scaled image to the round label
                lblImage.setImage(scaledImage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Image scaleImage(BufferedImage originalImage, int width, int height) {
        // Scale the image to fit within the label's size
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    // Custom JLabel to make the label round
    class RoundLabel extends JLabel {
        private Image image;

        public RoundLabel() {
            setOpaque(false); // Make sure the background is transparent
        }

        public void setImage(Image image) {
            this.image = image;
            repaint(); // Repaint to show the new image
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                // Make the label round by clipping it into an oval shape
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setClip(new Ellipse2D.Float(0, 0, getWidth(), getHeight()));
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoundLabelImageExample());
    }
}
