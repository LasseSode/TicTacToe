import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * A class representing a tic tac toe game where the player plays against an AI.
 * @author Lasse Sode, Mathias Ryborg
 * @version 15/10 2018
 */
public class KrydsOgBolle{
    private JFrame frame;
    private int roundNo;
    private ArrayList<JButton> buttons;
    private AIKrydsOgBolleImpl ai;
    private Difficulty diff;
    private boolean removing;

    /**
     * Creates a TicTacToeGUI object and thereby a tic tac toe game
     */
    public KrydsOgBolle(Difficulty diff){
        this.diff = diff;
        ai = new AIKrydsOgBolleImpl(diff);
        buttons = new ArrayList<>();
        roundNo=0;
        removing = false;
        frame = new JFrame("Tic tac toe");
        frame.setLayout(new GridLayout(3,3));
        frame.setMinimumSize(new Dimension(300,300));

        //closes program when pressing x
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addBoxes();

        frame.setVisible(true);
    }

    /**
     * Updates the GUI
     */
    private void updateGUI(){
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Adds the buttons/boxes to the frame. This is the grid of the GUI.
     */
    private void addBoxes(){
        JButton button1 = new JButton("");
        setUpButtons(button1);
        frame.add(button1);

        JButton button2 = new JButton("");
        setUpButtons(button2);
        frame.add(button2);

        JButton button3 = new JButton("");
        setUpButtons(button3);
        frame.add(button3);

        JButton button4 = new JButton("");
        setUpButtons(button4);
        frame.add(button4);

        JButton button5 = new JButton("");
        setUpButtons(button5);
        frame.add(button5);

        JButton button6 = new JButton("");
        setUpButtons(button6);
        frame.add(button6);

        JButton button7 = new JButton("");
        setUpButtons(button7);
        frame.add(button7);

        JButton button8 = new JButton("");
        setUpButtons(button8);
        frame.add(button8);

        JButton button9 = new JButton("");
        setUpButtons(button9);
        frame.add(button9);
    }

    /**
     * Sets up the size and font of the buttons and adds them to the list of buttons
     * also specifies the action to happen if the button is clicked
     * @param b  The button to set up.
     */
    private void setUpButtons(JButton b){
        b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,36));
        b.setMinimumSize(new Dimension(100,100));
        b.addActionListener(e -> {
            if(!removing) {
                if(roundNo%2==0) {
                    b.setText("x");
                    b.setEnabled(false);
                    roundNo++;
                    updateGUI();
                    AITurn();
                }
                else{
                    b.setText("o");
                    b.setEnabled(false);
                    roundNo++;
                    updateGUI();
                    AITurn();
                }
            }
            else{
                b.setText("");
                b.setEnabled(false);
                updateGUI();
                restoreEnabled();
                removing=false;
            }
        });
        buttons.add(b);
    }

    /**
     * Makes the right buttons enabled for the user to place an X
     */
    public void restoreEnabled(){
        for(JButton button : buttons){
            if(button.getText().equals("")){
                button.setEnabled(true);
            }
            else{
                button.setEnabled(false);
            }

        }
    }

    /**
     * The AI's turn
     * It will compute a move if the game is not yet finished
     */
    public void AITurn(){
        if(!diff.equals(Difficulty.OneVsOne)) {
            if (!isGameFinished()) {
                if (roundNo >= 7) {
                    buttons = ai.computeRemove(buttons, roundNo);
                }
                buttons = ai.computeMove(buttons, roundNo);
                updateGUI();
                roundNo++;
            }
        }
        if(!isGameFinished()&&roundNo>=6) {
            removeMark();
            updateGUI();
        }
    }

    /**
     * Allows the player to remove one of his x's before placing a new one
     */
    public void removeMark(){
        for(JButton b : buttons){
            if(!b.isEnabled()){
                if (roundNo%2 == 0)
                switch (b.getText()){
                    case "x" :
                        b.setEnabled(true);
                        break;
            } else if (b.getText().equals("o")) {b.setEnabled(true);
                }
            }
            else if(b.isEnabled()){
                b.setEnabled(false);
            }
        }
        removing= true;
    }

    /**
     * Checks if the game is finished and makes a dialog with the winner and options to reset or exit
     * @return  A boolean saying whether the game is finished or not
     */
    public boolean isGameFinished(){
        if(!ai.getWinner(buttons).equals("")){
            makeDialog("The winner is " + ai.getWinner(buttons) + "!");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Creates a dialog containing options to reset game or exit
     * @param message  The message the dialog will show
     */
    public void makeDialog(String message){
        Object[] options = {"Try again", "Exit"};
        JOptionPane pane = new JOptionPane();
        if(pane.showOptionDialog(frame,message, "Game finished", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,
                null,options, options[1]) == JOptionPane.YES_OPTION) {
            resetGame();
        }
        else{
            frame.dispose();
        }
    }

    /**
     * Resets the game
     */
    public void resetGame(){
        roundNo = 0;
        ai = new AIKrydsOgBolleImpl(diff);
        buttons = new ArrayList<>();
        frame.getContentPane().removeAll();
        addBoxes();
        updateGUI();
    }

    /**
     * Main method of the class. Creates a TicTacToe GUI object which starts the game
     * @param args
     */
    public static void main(String[] args) {
        KrydsOgBolle t = new KrydsOgBolle(Difficulty.INSANE);

    }
}
