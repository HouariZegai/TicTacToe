package com.houarizegai.TicTacToc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PlayWindow extends JFrame implements ActionListener{
	
	private JLabel 	TableOfScore; // Print Table Of Score
	private int xScore = 0; // This Value Is The Point Wined By X
    private int oScore = 0; // This Value Is The Point Wined By O
    private JButton buttonsXO[]; // This Button Using For Input XO 
    private JButton btnReset; // This Button Using For Clear Screen & Reset Score
    private JButton btnClear; // This Button Using For Clean Screen
    private JButton btnBackToMain; // This Button Using For Back To The Main Window
    private final static int BUTTON_XO_WIDTH = 80; // The Width Of The Button XO
    private final static int BUTTON_XO_HEIGHT = 80; // The Height  Of The Button XO
	private final static int POSITION_XO_H[] = {30, BUTTON_XO_WIDTH * 1 + 30, BUTTON_XO_WIDTH * 2 + 30, BUTTON_XO_WIDTH * 3 + 30}; // Dimension Of Button Horizontal
	private final static int POSITION_XO_V[] = {120, BUTTON_XO_WIDTH * 1 + 120, BUTTON_XO_WIDTH * 2 + 120, BUTTON_XO_WIDTH * 3 + 120 + 20}; // Dimontion Of Button Vertical & The Value 20 For Margin-Top
	private static int i = 0; // This Is Counter Using in Loop

	private final int CHOIX_LEVEL;
    /* This Constants Using For Check The Game If It Is WithFriend, Easy , Medium Or Hard */
    private final int CHOIX_FRIEND = 0;
    private final int CHOIX_EASY 	= 1;
    private final int CHOIX_MEDIUM = 2;
    private final int CHOIX_HARD	= 3;
    
    /* This Variables Using In Case Play With Friend */
    private static boolean player1 = true; // This Variable Tell Me Witch Player Play Now
    
    /* This Variables Using In Case Play With PC Level Easy */
    private static Random rand = new Random(); // Get Random Value
    private boolean printRand = true; // Check This Value if True Print XO For PC
    
    /* This Variables Using In Case Play With PC Level Medium OR Hard*/
    private int arrayRows[] = new int[8]; // This Table is add : ( 1 For X ) And ( -1 For O )
    private boolean mCenterFirst = false;
    private boolean mCornerFirst = false;
    private boolean mHvFirst = false;
    private boolean mHvAfterCorner = false;
    private boolean mCornerAfterHv = false;
    private boolean mCornerAfterCenter = false;
    private boolean mHvAfterHv = false;
    private int mCounter = 0;
    private boolean mCorner = false;
    private boolean mHvAfterCenter = false;  
    
    PlayWindow(int CHOIX_LEVEL) {  // CHOIX_LEVEL = 0:WithFriend, 1:Easy, 2:Medium, 3:Hard
    	
    	this.CHOIX_LEVEL = CHOIX_LEVEL;
    	
    	if (this.CHOIX_LEVEL == CHOIX_FRIEND)
    		this.setTitle("Play With Friend");
    	else if (this.CHOIX_LEVEL == CHOIX_EASY)
    		this.setTitle("Play With PC Easy");
    	else if (this.CHOIX_LEVEL == CHOIX_MEDIUM)
    		this.setTitle("Play With PC Medium");
    	else
    		this.setTitle("Play With PC Hard");
    	
    	TableOfScore = new JLabel("");
        printScore(xScore, oScore); // Print Format
        TableOfScore.setBounds(170, 10, 200, 100); // Dimension
        TableOfScore.setFont(new Font("Comic Sans MS", Font.PLAIN, 16)); // change Font Of Label ( FontFamily , FontWeight , FontSize )
        this.add(TableOfScore);

        buttonsXO = new JButton[9];        

        for (i = 0; i < buttonsXO.length; i++) { // This Loop For Initialize & Modify The Size & Position Of The Button & Adding To The Screen
            buttonsXO[i] = new JButton();
            if (i < 3) {
                buttonsXO[i].setBounds(POSITION_XO_H[i], POSITION_XO_V[0], BUTTON_XO_WIDTH, BUTTON_XO_HEIGHT);
            } else if (i < 6) {
                buttonsXO[i].setBounds(POSITION_XO_H[i - 3], POSITION_XO_V[1], BUTTON_XO_WIDTH, BUTTON_XO_HEIGHT);
            } else if (i < 9) {
                buttonsXO[i].setBounds(POSITION_XO_H[i - 6], POSITION_XO_V[2], BUTTON_XO_WIDTH, BUTTON_XO_HEIGHT);
            }

            buttonsXO[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
            buttonsXO[i].setBackground(Color.WHITE); // Change The Color of The Button
            this.add(buttonsXO[i]);
        }
        for (i = 0; i < 9; i++) {
            buttonsXO[i].addActionListener(this);
        }

        btnReset = new JButton("Reset");
        btnReset.setBounds(POSITION_XO_H[0], POSITION_XO_V[3], BUTTON_XO_WIDTH * 3 / 2 - 10, 40);
        btnReset.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnReset.addActionListener(event -> {
        	clear();
        	resetScore();
        });
        this.add(btnReset);

        btnClear = new JButton("Clear");
        btnClear.setBounds(POSITION_XO_H[0] + btnReset.getWidth() + 20, POSITION_XO_V[3], BUTTON_XO_WIDTH * 3 / 2 - 10, 40);
        btnClear.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnClear.addActionListener(event -> {
        	clear();
        });
        this.add(btnClear);

        btnBackToMain = new JButton("Back To Main");
        btnBackToMain.setBounds(POSITION_XO_H[0], POSITION_XO_V[3] + 50, BUTTON_XO_WIDTH * 3, 40);
        btnBackToMain.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnBackToMain.addActionListener(event -> {
        	this.setVisible(false);
        	new MainWindow();
        });
        add(btnBackToMain);

        this.setBounds(400, 170, buttonsXO[0].getWidth() * 3 + 75, TableOfScore.getHeight() + buttonsXO[0].getHeight() * 3 + btnReset.getHeight() + btnBackToMain.getHeight() + 120);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
    	
    	if (CHOIX_LEVEL == CHOIX_FRIEND) {
    		for(i = 0; i < 9; i++){
        		if (e.getSource().equals(buttonsXO[i])) // If ButtonsXO[i] Is Clicked ?
        			printXOForFriend(i); // Fill The ButtonXO In The Index i By X Or O 
    	    }
    	}else if (CHOIX_LEVEL == CHOIX_EASY) {
    		for (i = 0; i < 9; i++) {
                if (e.getSource() == buttonsXO[i])
                    printXOForEasy(i);
            }
		}else if (CHOIX_LEVEL == CHOIX_MEDIUM) {
			for (i = 0; i < 9; i++)
                if (e.getSource() == buttonsXO[i]){
                    if (buttonsXO[i].getText().equals("")) {
                        printXOForMeMedium(i);
                        
                        if(getResult(true)) {
                            printXOForPcMedium();
                            getResult(false);
                        }
                    } 
                }	
		}else if (CHOIX_LEVEL == CHOIX_HARD) {
			for (i = 0; i < 9; i++)
                if (e.getSource() == buttonsXO[i]){
                    if (buttonsXO[i].getText().equals("")) {
                        printXOForMeHard(i);
                        
                        if(getResult(true)) {
                            printXOForPcHard();
                            getResult(false);
                        }
                    } 
                }
		}

    }
    
    /* This Function Bellow Using In All Play Case */
    
    private void printScore(int x, int o) { // This Function For Modify Table of Score
        String xFormat = setColorOnly(String.valueOf(x), "green"),
                oFormat = setColorOnly(String.valueOf(o), "green");
        if (x > o) {
            xFormat = setColorOnly(String.valueOf(x), "green");
            oFormat = setColorOnly(String.valueOf(o), "red");
        } else if (x < o) {
            xFormat = setColorOnly(String.valueOf(x), "red");
            oFormat = setColorOnly(String.valueOf(o), "green");
        }
        
        String p1 = "You";
        String p2 = "Pc";
        
        if (CHOIX_LEVEL == CHOIX_FRIEND) {
        	p1 = "X";
        	p2 = "O";
        }
        	
        TableOfScore.setText("<html><table border='1'><tr><th colspan='2'>Score :&nbsp;&nbsp;&nbsp;</th></tr>"
                                            + "<tr><td><b>"+ p1 + "</b></td><td>" + xFormat + "</td></tr>"
                                            + "<tr><td><b>"+ p2 + "</b></td><td>" + oFormat + "</td></tr></html>");
    }
    
	private boolean getResult(boolean Player1Win) { // This Function Show A dialog If Any Player Win Or If All Button fill ( Null )
        if (((buttonsXO[0].getText().equals(buttonsXO[3].getText())) && (buttonsXO[0].getText().equals(buttonsXO[6].getText())) && (!buttonsXO[0].getText().equals("")))
                || ((buttonsXO[1].getText().equals(buttonsXO[4].getText())) && (buttonsXO[1].getText().equals(buttonsXO[7].getText())) && (!buttonsXO[1].getText().equals("")))
                || ((buttonsXO[2].getText().equals(buttonsXO[5].getText())) && (buttonsXO[2].getText().equals(buttonsXO[8].getText())) && (!buttonsXO[2].getText().equals("")))
                || ((buttonsXO[0].getText().equals(buttonsXO[1].getText())) && (buttonsXO[0].getText().equals(buttonsXO[2].getText())) && (!buttonsXO[0].getText().equals("")))
                || ((buttonsXO[3].getText().equals(buttonsXO[4].getText())) && (buttonsXO[3].getText().equals(buttonsXO[5].getText())) && (!buttonsXO[3].getText().equals("")))
                || ((buttonsXO[6].getText().equals(buttonsXO[7].getText())) && (buttonsXO[6].getText().equals(buttonsXO[8].getText())) && (!buttonsXO[6].getText().equals("")))
                || ((buttonsXO[0].getText().equals(buttonsXO[4].getText())) && (buttonsXO[0].getText().equals(buttonsXO[8].getText())) && (!buttonsXO[0].getText().equals("")))
                || ((buttonsXO[2].getText().equals(buttonsXO[4].getText())) && (buttonsXO[2].getText().equals(buttonsXO[6].getText())) && (!buttonsXO[2].getText().equals("")))) {
        	
            if (Player1Win) {
                xScore++;
                if (CHOIX_LEVEL == CHOIX_FRIEND)
                	JOptionPane.showMessageDialog(null, "<html>" + setColorOnly("Player 1 (X) ", "green") + setColorOnly("Win !" , "green")  + "</html>");
                else
                	JOptionPane.showMessageDialog(null, setColor("You Win  ^_^ !" , "green"));
            } else {
                oScore++;
                if (CHOIX_LEVEL == CHOIX_FRIEND)
                	JOptionPane.showMessageDialog(null, "<html>" + setColorOnly("Player 2 (O) ", "blue") + setColorOnly("Win !" , "green")  + "</html>");
                else
                	JOptionPane.showMessageDialog(null, "<html>" + setColorOnly("You Lose : (", "red") + "<br>PC " + setColor("Win !", "green") + "</html>");
            }
            printScore(xScore, oScore);
            clear();
            return false;
        } else {
            if (!buttonsXO[0].getText().equals("")
	                && !buttonsXO[1].getText().equals("")
	                && !buttonsXO[2].getText().equals("")
	                && !buttonsXO[3].getText().equals("")
	                && !buttonsXO[4].getText().equals("")
	                && !buttonsXO[5].getText().equals("")
	                && !buttonsXO[6].getText().equals("")
	                && !buttonsXO[7].getText().equals("")
	                && !buttonsXO[8].getText().equals("")) {
                JOptionPane.showMessageDialog(null, " Draw !");
                clear();
                return false;
            } else {
                printRand = true;
            }
        }
        return true;
    }
	
    private void clear() { // This Function For Clean The Screen & Initialize All Variables
        for (i = 0; i < 9; i++) {
            buttonsXO[i].setText(""); // Remove The Contain Of ButtonXO
        }
        if(CHOIX_LEVEL == CHOIX_EASY) // This Is For Easy Case
        	printRand = false;
        else { // This Is For Medium OR Hard Case
        	for (i = 0; i < 8; i++)
                arrayRows[i] = 0;
        	mCenterFirst = false;
            mCornerFirst = false;
            mHvFirst = false;
            mHvAfterCorner = false;
            mCornerAfterHv = false;
            mCornerAfterCenter = false;
            mHvAfterHv = false;
            mCorner = false;
            mHvAfterCenter = false;
            mCounter = 0;
        }
    }

    private void resetScore() { // Call Clear() Function & This Function For initialize The Score
        clear(); // Clean The ButtonsXO
        printScore(xScore = 0, oScore = 0); // Initialize The Table Of Score & The Variables (xScore & oScore) By 0 
    }
    
    protected static String setColor(String before, String color) { // Change Color Of String
        return "<html><font color='" + color + "'>" + before + "</font></html>";
    }
    
    private static String setColorOnly(String before, String color) { // Change Color Of String But With Out Add HTML Tag (Before & After The String)
        return "<font color='" + color + "'>" + before + "</font>";
    }

    /* This Function Bellow Using In Play With Friend Case */
    
    private void printXOForFriend(int index) { // Fill The ButtonXO[index] By XO & Check If Any Player Win ( Call The Function getResult() )
    	if (buttonsXO[index].getText().equals("")) {
            if (player1) { // If Player 1 Is The Tour For Play
            	buttonsXO[index].setText(setColor("X", "green"));
            	getResult(player1); // Check If The Player1 Win After Playing
                player1 = false;
            } else { // If Player 2 Is The Tour For Play
            	buttonsXO[index].setText(setColor("O", "blue"));
            	getResult(player1); // Check If The Player2 Win After Playing
                player1 = true;
            }
    	}
    }
    
    /* This Function Bellow Using In Easy Case */
    
    private void printXOForEasy(int i) { // Fill The Gaps By XO & Call The Function getResult()
        if (buttonsXO[i].getText().equals("")) { // Check If The ButtonXO Is Empty To Filling By X or O
            buttonsXO[i].setText(setColor("X", "green"));
            getResult(true); // Check If I Wined !

            if (printRand) {
                while (true) { // Execute This Block Until Pc Fill 1 Gaps By The Random Value 
                    int index = rand.nextInt(9); // Get Random Value Between 0 And 8
                    if (buttonsXO[index].getText().equals("")) {
                    	try{
                    		Thread.sleep(100); // Stop Execution 100 MilliSeconds (0.1 Seconds)
                    	}catch (InterruptedException e){}
                    	
                        buttonsXO[index].setText(setColor("O", "blue"));
                        getResult(false); // Check If The Pc Wined !
                        break;
                    }
                }
            }
        }
    }

    /* This Function Bellow Using For Medium & Hard Case */
    
    private void printXOForMeMedium(int index) {
        buttonsXO[index].setText(setColor("X", "green"));
        if (index == 4) {
            if (mCounter == 0) { // Check If The First Checked Is The Center
                mCenterFirst = true;
            }
        } else if (index == 0 || index == 2 || index == 6 || index == 8) { // The Corner
            mCorner = true;
        } else {
            mCorner = false;
        }
        fillArray(index, 1);
        mCounter++;
    }
    
    private void printXOForMeHard(int index) { // Fill The Gaps By 0 & Call The Function getResult()
        buttonsXO[index].setText(setColor("X", "green"));
        if (index == 4) {
            if (mCounter == 0) { // Check If The First Checked Is The Center
                mCenterFirst = true;
            }
        } else if (index == 0 || index == 2 || index == 6 || index == 8) { // Les Courner
            if (mCounter == 0) { // Check If The First Checked Is The Corner
                mCornerFirst = true;
            } else {
                if (mCenterFirst) {
                    mCornerAfterCenter = true;
                    mCenterFirst = false;
                }
                mCorner = true;
                mCornerFirst = false;
                if (mHvFirst) {
                    mCornerAfterHv = true;
                }
                mHvFirst = false;
            }
        } else {
            if (mCounter == 0) {
                mHvFirst = true;
            } else {
                if (mCornerFirst) {
                    mHvAfterCorner = true;
                    mCornerFirst = false;
                } else if (mHvFirst) {
                    mHvAfterHv = true;
                    mHvFirst = false;
                } else if (mCenterFirst) {
                    mHvAfterCenter = true;
                    mCornerAfterCenter = false;
                    mCenterFirst = false;
                } else {
                    mCorner = false;
                }
            }
        }
        fillArray(index, 1);
        mCounter++;  
    }
    
    private void printXOForPcMedium() {
        if (mCenterFirst) {
            //TODO play in corner
            if (buttonsXO[0].getText().equals("")) {
                fillCasePc(0);
            } else if (buttonsXO[2].getText().equals("")) {
                fillCasePc(2);
            } else if (buttonsXO[6].getText().equals("")) {
                fillCasePc(6);
            } else if (buttonsXO[8].getText().equals("")) {
                fillCasePc(8);
            }
            mCenterFirst = false;
        } else if (buttonsXO[4].getText().equals("")) {
            fillCasePc(4);
        } else if (meWantWin() != -1) {
            //TODO go and win
            int indexOfTheRow = meWantWin();
            int[] theTargetRow = getTargetRow(indexOfTheRow);
            int indexOfTheTargetCase = getIndexOfTheEmptyCase(theTargetRow);
            fillCasePc(indexOfTheTargetCase);
            mCorner = false;
            mCornerAfterCenter = false;
            mHvAfterHv = false;
            mHvAfterCorner = false;
            mCornerAfterHv = false;
        } else if (adverserWantWin() != -1) {
            //TODO stop him
            int indexOfTheRow = adverserWantWin();
            int[] theTargetRow = getTargetRow(indexOfTheRow);
            int indexOfTheTargetCase = getIndexOfTheEmptyCase(theTargetRow);
            fillCasePc(indexOfTheTargetCase);
            mCorner = false;
            mCornerAfterCenter = false;
            mHvAfterHv = false;
            mHvAfterCorner = false;
            mCornerAfterHv = false;
        } else if (mCorner) {
            //TODO play in hv
            if (buttonsXO[1].getText().equals("")) {
                fillCasePc(1);
            } else if (buttonsXO[3].getText().equals("")) {
                fillCasePc(3);
            } else if (buttonsXO[5].getText().equals("")) {
                fillCasePc(5);
            } else if (buttonsXO[7].getText().equals("")) {
                fillCasePc(7);
            }
        } else {
                if (buttonsXO[0].getText().equals("")) {
                    fillCasePc(0);
                } else if (buttonsXO[2].getText().equals("")) {
                    fillCasePc(2);
                } else if (buttonsXO[6].getText().equals("")) {
                    fillCasePc(6);
                } else if (buttonsXO[8].getText().equals("")) {
                    fillCasePc(8);
                }
            }
    }
    
    private void printXOForPcHard() {
        if (mCenterFirst) {
            //TODO play in corner
            if (buttonsXO[0].getText().equals("")) {
                fillCasePc(0);
            } else if (buttonsXO[2].getText().equals("")) {
                fillCasePc(2);
            } else if (buttonsXO[6].getText().equals("")) {
                fillCasePc(6);
            } else if (buttonsXO[8].getText().equals("")) {
                fillCasePc(8);
            }
        } else if (mCornerFirst) {
            //TODO play in center
            fillCasePc(4);
        } else if (mHvFirst) {
            fillCasePc(4);
            //TODO play4);
        } else if (mHvAfterHv) {
            int indexOfTheBestCorner = getBestCorner();
            fillCasePc(indexOfTheBestCorner);
            mCorner = false;
            mHvAfterHv = false;
            //TODO play in best Corner
        } else if (mCornerAfterCenter) {
            if (buttonsXO[0].getText().equals("")) {
                fillCasePc(0);
            } else if (buttonsXO[2].getText().equals("")) {
                fillCasePc(2);
            } else if (buttonsXO[6].getText().equals("")) {
                fillCasePc(6);
            } else if (buttonsXO[8].getText().equals("")) {
                fillCasePc(8);
            }
            mCornerAfterCenter = false;
            //todo play in corner
        } else if (meWantWin() != -1) {
            //TODO go and win
            int indexOfTheRow = meWantWin();
            int[] theTargetRow = getTargetRow(indexOfTheRow);
            int indexOfTheTargetCase = getIndexOfTheEmptyCase(theTargetRow);
            fillCasePc(indexOfTheTargetCase);
            mCorner = false;
            mCornerAfterCenter = false;
            mHvAfterHv = false;
            mHvAfterCorner = false;
            mCornerAfterHv = false;
        } else if (adverserWantWin() != -1) {
            //TODO stop him
            int indexOfTheRow = adverserWantWin();
            int[] theTargetRow = getTargetRow(indexOfTheRow);
            int indexOfTheTargetCase = getIndexOfTheEmptyCase(theTargetRow);
            fillCasePc(indexOfTheTargetCase);
            mCorner = false;
            mCornerAfterCenter = false;
            mHvAfterHv = false;
            mHvAfterCorner = false;
            mCornerAfterHv = false;
        } else if (mCornerAfterHv) {
            int indexOfBestHV = getBestHV();
            fillCasePc(indexOfBestHV);
            mCorner = false;
            mCornerAfterHv = false;
            //TODO play in best Corner en face
        } else if (mHvAfterCorner) {
            int indexOfBestHV = getBestHV();
            fillCasePc(indexOfBestHV);
            mCorner = false;
            mHvAfterCorner = false;
        } else if (mCorner) {
            //TODO play in hv
            if (buttonsXO[1].getText().equals("")) {
                fillCasePc(1);
            } else if (buttonsXO[3].getText().equals("")) {
                fillCasePc(3);
            } else if (buttonsXO[5].getText().equals("")) {
                fillCasePc(5);
            } else if (buttonsXO[7].getText().equals("")) {
                fillCasePc(7);
            }
        } else {
            if (mCounter <= 4) {
                int indexOfTheBestCorner = getBestCorner();
                fillCasePc(indexOfTheBestCorner);
                mCorner = false;
                //TODO play in best corner

            } else {
                if (buttonsXO[0].getText().equals("")) {
                    fillCasePc(0);
                } else if (buttonsXO[2].getText().equals("")) {
                    fillCasePc(2);
                } else if (buttonsXO[6].getText().equals("")) {
                    fillCasePc(6);
                } else if (buttonsXO[8].getText().equals("")) {
                    fillCasePc(8);
                }
            }
        }
    }
    
    private void fillArray(int cases, int current) {
        switch (cases) {
            case 0:
                arrayRows[0] += current;
                arrayRows[3] += current;
                arrayRows[7] += current;
                break;
            case 1:
                arrayRows[0] += current;
                arrayRows[4] += current;
                break;
            case 2:
                arrayRows[0] += current;
                arrayRows[5] += current;
                arrayRows[6] += current;
                break;
            case 3:
                arrayRows[1] += current;
                arrayRows[3] += current;
                break;
            case 4:
                arrayRows[1] += current;
                arrayRows[4] += current;
                arrayRows[6] += current;
                arrayRows[7] += current;
                break;
            case 5:
                arrayRows[1] += current;
                arrayRows[5] += current;
                break;
            case 6:
                arrayRows[2] += current;
                arrayRows[3] += current;
                arrayRows[6] += current;
                break;
            case 7:
                arrayRows[2] += current;
                arrayRows[4] += current;
                break;
            case 8:
                arrayRows[2] += current;
                arrayRows[5] += current;
                arrayRows[7] += current;
                break;
        }
    }
    
    private void fillCasePc(int index){
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){}
        
        buttonsXO[index].setText(setColor("O", "blue"));
        fillArray(index, -1);
        mCounter++;
        mCorner = false;
    }

    private int adverserWantWin() {
        int index = -1;
        for (int i = 0; i < arrayRows.length; i++)
            index = (arrayRows[i] == 2)? i: index;
        return index;
    }

    private int meWantWin() {
        int index = -1;
        for (int i = 0; i < arrayRows.length; i++)
            index = (arrayRows[i] == -2)? i : index;
        return index;
    }

    private static int[] getTargetRow(int index) {
        switch (index) {
            case 0:
                return new int[]{0, 1, 2};
            case 1:
                return new int[]{3, 4, 5};
            case 2:
                return new int[]{6, 7, 8};
            case 3:
                return new int[]{0, 3, 6};
            case 4:
                return new int[]{1, 4, 7};
            case 5:
                return new int[]{2, 5, 8};
            case 6:
                return new int[]{2, 4, 6};
            case 7:
                return new int[]{0, 4, 8};
            default:
                return new int[]{};
        }
    }

    private int getIndexOfTheEmptyCase(int[] array) {
        int index = -1;
        for (int i = 0; i < 3; i++)
            if (buttonsXO[array[i]].getText().equals(""))
                index = array[i];
        
        return index;
    }

    private int getBestHV() {
        int ZeroFour = arrayRows[0] + arrayRows[4];
        int TwoFour = arrayRows[2] + arrayRows[4];
        int OneThree = arrayRows[1] + arrayRows[3];
        int OneFive = arrayRows[1] + arrayRows[5];

        if ((ZeroFour <= OneFive) && (ZeroFour <= TwoFour) && (ZeroFour <= OneThree)) {
            return 1;
        } else if ((TwoFour <= OneThree) && (TwoFour <= ZeroFour) && (TwoFour <= OneFive)) {
            return 7;
        } else if ((OneThree <= TwoFour) && (OneThree <= ZeroFour) && (OneThree <= OneFive)) {
            return 3;
        } else {
            return 5;
        }
    }

    private int getBestCorner() {
        int ZeroThree = arrayRows[0] + arrayRows[3];
        int TwoThree = arrayRows[2] + arrayRows[3];
        int ZeroFive = arrayRows[0] + arrayRows[5];
        int TwoFive = arrayRows[2] + arrayRows[5];

        if ((ZeroThree >= TwoThree) && (ZeroThree >= ZeroFive) && (ZeroThree >= TwoFive)) {
            return 0;
        } else if ((TwoThree >= ZeroThree) && (TwoThree >= ZeroFive) && (TwoThree >= TwoFive)) {
            return 6;
        } else if ((ZeroFive >= ZeroThree) && (ZeroFive >= TwoThree) && (ZeroFive >= TwoFive)) {
            return 2;
        } else {
            return 8;
        }
    }
}