package com.houarizegai.TicTacToc;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutWindow extends JDialog implements MouseListener {
    
    private static JLabel labelCopyRight; // This Label Using For Write Copy Right
    private static JLabel labelImg[]; // This Label Contain Image Of Social Media & Account About EasyCodeClub
    private static JLabel labelLogo; // This Label Using For Display Logo Of My Team
    private final static String IMAGE_NAME[] = {"Facebook","Twitter", "Youtube", "GitHub"}; // This Table Contain The Name Of My Icon Of Social Media , And Using To Import this Icon 
    
    AboutWindow() {    
        ImageIcon imageIcon = new ImageIcon("src\\icon\\Logo.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        labelLogo = new JLabel(imageIcon); // Create New Instance From JLabel & Import Icon From WorkSpace To Replace In This Label
    	labelLogo.setBounds(20, 20, 285, 250); // Set The Position , Height And Width Of The Label setBounds(x, y, Width, Height)
    	this.add(labelLogo); // Add The Label To My About Window
    	
    	/* This Part Bellow For Change The Label Social Media  */
    	
    	labelImg = new JLabel[4]; // Initialize The Table By Four JLabel
        for (int i = 0; i < 4; i++) {
        	labelImg[i] = new JLabel(new ImageIcon("src\\icon\\" + IMAGE_NAME[i]  + ".png"));
        	labelImg[i].setBounds(90 + 40 * i, 290, 32, 32);
                labelImg[i].addMouseListener(this);
        	this.add(labelImg[i]); // Add My Label Image To My About Window
            labelImg[i].setCursor(new Cursor(Cursor.HAND_CURSOR)); // If I Move The Cursor In The LabelImg Change The Cursor Design By Hand
        }
        
        /* This Part Bellow For Change The Label Of Copy Right */

        labelCopyRight = new JLabel("Copyright � EasyCodeClub All Right Reserved 2017."); // Create New Instance From JLabel & Set By Paragraph 
        labelCopyRight.setBounds(15, 330, 300, 30);
        this.add(labelCopyRight);
        
        /* This Is Parameter For My About Window */
        
        this.setBounds(400, 200, 330, 400);
        this.setTitle("About This App"); // Change The Title For My Window By This Value (in argument)
        this.setResizable(false); // You Can Not Change The Size Of My About Window
        this.setAlwaysOnTop(true); // I Put My About Window Always In Top
        this.setLayout(null); // Disable All Layout Because I Control The Position Of My Elements By The Function setBounds(x, y, Width, Height)
        this.setVisible(true); // Show The Window To My Screen
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i < labelImg.length; i++)
        if (e.getSource().equals(labelImg[i]))
            try {
                Desktop.getDesktop().browse(new URI("https://www." + IMAGE_NAME[i] + ".com/HouariZegai"));
        } catch (Exception ex) {
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}