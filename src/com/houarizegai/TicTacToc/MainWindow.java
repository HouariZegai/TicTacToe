package com.houarizegai.TicTacToc;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame implements ActionListener{
    
    private final JLabel labelSelect; // OutPut String In Screen
    private JLabel labelChoix; // Print String In Screen
    private final JButton btnWithFriend; // Button Play With Friend   
    private final JButton btnWithPc; // Button Play With PC
    private final JButton btnAbout; // Button About This Application
    private JRadioButton choixWithPc[] = new JRadioButton[3]; // RadioButton Choix:  0:Easy, 1:Medium & :2Hard
    private boolean showHidePcPlay = false; // This Boolean Using For Show or Hide Choix
    private final Font FontForLabel = new Font("Comic Sans MS", Font.PLAIN, 16); // Create New Instance for Change this Value: Font(Font-Family, Font-Weight, Font-Size)
    private static int i = 0; // This Variable Using In Loop
    
    MainWindow() {
        
        labelSelect = new JLabel(PlayWindow.setColor("Please Select Type Of Game :", "blue")); // Create New Instance From JLabel & Add + Change The Color For The Text 
        labelSelect.setBounds(20, 40, 315, 50); // Set The Position , Height And Width Of The Label setBounds(x, y, Width, Height)
        labelSelect.setFont(new Font("Comic Sans Ms", Font.PLAIN, 15)); // Change The Font : (Font-Family, Font-Weight, Font-Size)
        this.add(labelSelect); // Add The Label To My About Window
        
        btnWithFriend = new JButton("Play With My Friend");
        btnWithFriend.setBounds(20, 100, 250, 50);
        btnWithFriend.setFont(FontForLabel);
        btnWithFriend.addActionListener(event -> { // If I Click This Button Call This Block To Execute 
        	new PlayWindow(0); // Create New Window From The Class PlayWithFriend 
            this.setVisible(false); // Hide My Main Window
        });
        this.add(btnWithFriend);
        
        btnWithPc = new JButton("Play With PC");
        btnWithPc.setBounds(20, 160, 250, 50);
        btnWithPc.setFont(FontForLabel);
        btnWithPc.addActionListener(event -> { // If I Click To "With PC" Button Execute This Block ( Show Or Hide Choix & Resize The Main Window )
        	showHidePcPlay = (showHidePcPlay)? false : true; // Inverse The Value Of The Variable By True Or False
        	labelChoix.setVisible(showHidePcPlay); // Show Or Hide The labelChoix
        	for(i = 0; i < 3; i++)
        		choixWithPc[i].setVisible(showHidePcPlay); // Show Or Hide The JRadioButton
        	
            this.setSize(310, (showHidePcPlay)?450:270);
        });
        this.add(btnWithPc);
        
        labelChoix = new JLabel(PlayWindow.setColor("Please Select Level :", "green"));
        labelChoix.setBounds(30, 220, 250, 50);
        labelChoix.setVisible(false);
        this.add(labelChoix);
        
        choixWithPc[0] = new JRadioButton("Easy");
        choixWithPc[1] = new JRadioButton("Medium");
        choixWithPc[2] = new JRadioButton("Hard");
        
        for(int i = 0; i < 3; i++) {
        	choixWithPc[i].setBounds(40, 260 + 30 * i, 150, 30);
        	choixWithPc[i].setVisible(false);
        	choixWithPc[i].addActionListener(this);
	        this.add(choixWithPc[i]);
        }
        
        btnAbout = new JButton("About");
        btnAbout.setBounds(200, 10, 70, 25);
        btnAbout.addActionListener(event -> {
            new AboutWindow();
        });
        this.add(btnAbout);
        
        setTitle("TicTacToc Game"); // Edit (Set) The Title Of My Main Window
        setBounds(400, 170, 310, 270);
        setLayout(null); // Disable All Layout Because I Control The Position Of My Elements By The Function setBounds(x, y, Width, Height)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If I Click To The Close Button Stop Process
        setVisible(true); // Show The Window To The Screen
        setIconImage(new ImageIcon(getClass().getResource("/com/houarizegai/TicTacToc/images/logo.png")).getImage());
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 3; i++)
			if(e.getSource() == choixWithPc[i]) {
			    	new PlayWindow(i+1);
			        this.setVisible(false);
			}
	}
	
	public static void main(String[] args) {
        new MainWindow(); // Create New Instance (Window) From Main Class
    }
}