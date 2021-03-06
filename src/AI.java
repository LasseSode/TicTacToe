import java.util.*;
import javax.swing.*;

/**
 * An AI class that computes moves and can check if anyone has won.
 */
public class AI{
    private Difficulty diff;
    private int numberOfO = 0;
    private int numberOfNothing = 0;
    private int numberOfX = 0;
    private int nrOfMoves = 1;

    private Random random;
    private ArrayList<JButton> fields = new ArrayList<>();

    public AI(Difficulty diff) {
        this.diff = diff;
    }

    /**
     * Sets the difficulty/intelligence of the AI
     */
    public void setDiff(Difficulty diff) {
        this.diff = diff;
    }

    /**
     * Counts number of x's, o's and empty strings.
     * @param i  The index of the fields list to be checked
     */
    public void findValues(int i) {
        random = new Random();
        String value = fields.get(i).getText();
        if (value.equals("o")) {
            numberOfO++;
        }
        if (value.equals("")) {
            numberOfNothing++;
        }
        if (value.equals("x")) {
            numberOfX++;
        }
    }

    /**
     * Get the current stored number of O's
     * @return  The current number of stored O's in the fields variable (not necessarily the total amount of O's on the board)
     */
    public int getNumberOfO() {
        return numberOfO;
    }

    /**
     * Get the current stored number of empty fields
     * @return  The current number of stored empty fields in the fields variable (not necessarily the total amount of O's on the board)
     */
    public int getNumberOfNothing() {
        return numberOfNothing;
    }

    /**
     * Get the current stored number of X's
     * @return  The current number of stored X's in the Field variable (not necessarily the total amount of O's on the board)
     */
    public int getNumberOfX() {
        return numberOfX;
    }

    /**
     * Computes the move the AI should make
     * @param buttons  The buttons in the game, containing the current state
     * @param roundNo  The current round number
     * @return  A new list of buttons containing their new state after the AI's move
     */
    public ArrayList<JButton> computeMove(ArrayList<JButton> buttons, int roundNo) {
        nrOfMoves=1;
        fields = buttons;
        //If the AI starts, put o in a corner
        if (roundNo == 0) {
            fields.get(2).setText("o");
            fields.get(2).setEnabled(false);
            nrOfMoves=0;
            return fields;
        }

        if(diff == Difficulty.MEDIUM || diff == Difficulty.HARD || diff == Difficulty.INSANE){
            //if player starts
            if (roundNo == 1) {
                //Player starts in middle
                if (fields.get(4).getText().equals("x")) {
                    fields.get(0).setText("o");
                    fields.get(0).setEnabled(false);
                    nrOfMoves=0;
                } else {
                    fields.get(4).setText("o");
                    fields.get(4).setEnabled(false);
                    nrOfMoves=0;
                }
                return fields;
            }

            if(nrOfMoves==1) {
                winMove();
            }
        }

        if(diff == Difficulty.HARD || diff == Difficulty.INSANE) {
            if (nrOfMoves == 1) {
                blockMove();
            }
        }

        if(diff == Difficulty.INSANE){
            if(nrOfMoves==1){
                forkMove();
            }
        }

        if(diff == Difficulty.INSANE) {
            if (nrOfMoves == 1) {
                predictForkMove(roundNo);
            }
        }

        if(nrOfMoves==1){
            randomMove();
        }

        return fields;
    }

    /**
     * Checks if the AI can make a winning move and makes the move if possible
     */
    public void winMove() {
        //Columns
        int empty = 0;
        for (int i = 0; i < 3; i++) {
            for (int n = i; n <= 8; n += 3) {
                findValues(n);
            }
            for (int n = i; n <= 8; n += 3) {
                changeField(n,getNumberOfO());
            }
            resetNumbers();
        }

        //check rows
        if (nrOfMoves == 1) {
            for (int y = 0; y <= 8; y += 3) {
                for (int z = 0; z <= 2; z++) {
                    findValues(z + y);
                }
                for (int z = 0; z <= 2; z++) {
                    changeField(z+y,getNumberOfO());
                }
                resetNumbers();
            }
        }

        //Check first diagonal
        if (nrOfMoves == 1) {
            for(int k=0; k<=8; k+=4){
                findValues(k);
            }
            for(int k=0; k<=8; k+=4){
                changeField(k,getNumberOfO());
            }
            resetNumbers();
        }

        //Check second diagonal
        if (nrOfMoves == 1) {
            for(int l=2; l<=6; l+=2){
                findValues(l);
            }
            for(int l=2; l<=6; l+=2){
                changeField(l,getNumberOfO());
            }
            resetNumbers();
        }


    }

    /**
     * Checks if the AI can block the player, and does it if possible
     */
    public void blockMove(){

        //Columns
        for (int i = 0; i < 3; i++) {
            for (int n = i; n <= 8; n += 3) {
                findValues(n);
            }
            for(int j = i; j <= 8; j+=3) {
                changeField(j, getNumberOfX());
            }
            resetNumbers();
        }

        //check rows
        if (nrOfMoves == 1) {
            for (int y = 0; y <= 8; y += 3) {
                for (int z = 0; z <= 2; z++) {
                    findValues(z+y);
                }
                for (int z = 0; z <= 2; z++) {
                    changeField(z+y, getNumberOfX());
                }
                resetNumbers();
            }
        }

        //Check first diagonal
        if (nrOfMoves == 1) {
            for(int k=0; k<=8; k+=4){
                findValues(k);
            }
            for(int k=0; k<=8; k+=4){
                changeField(k, getNumberOfX());
            }
            resetNumbers();
        }

        //Check second diagonal
        if (nrOfMoves == 1) {
            for(int l=2; l<=6; l+=2){
                findValues(l);
            }
            for(int l=2; l<=6; l+=2){
                changeField(l,getNumberOfX());
            }
            resetNumbers();
        }
    }

    public void forkMove(){};

    public void predictForkMove(int roundNo){
        //if opposite corners (from 0 - 8)
        if(nrOfMoves==1) {
            if (roundNo == 3) {
                if (fields.get(0).getText().equals("x") && fields.get(4).getText().equals("o") && fields.get(8).getText().equals("x")) {
                    //insert in an edge position
                    fields.get(7).setText("o");
                    fields.get(7).setEnabled(false);
                    nrOfMoves--;
                }
                //if it is the other diagonal
                else if (fields.get(2).getText().equals("x") && fields.get(4).getText().equals("o") && fields.get(6).getText().equals("x")) {
                    //insert in an edge position
                    fields.get(7).setText("o");
                    fields.get(7).setEnabled(false);
                    nrOfMoves--;
                }
            }
        }
//Check rows combined with diagonals
        if(nrOfMoves==1){
            for (int y = 0; y <= 8; y += 3) {
                for (int z = y; z <= y+2; z++) {
                    findValues(z);
                }
                if(numberOfNothing == 2 && numberOfX == 1){
                    for (int z = y; z <= y+2; z++) {
                        if(fields.get(z).getText().equals("")){
                            resetNumbers();
                            //Check diagonals
                            if(z==0 || z==8){
                                for(int k=0; k<=8; k+=4){
                                    findValues(k);
                                }
                                for(int k=0; k<=8; k+=4){
                                    if(numberOfNothing==2 && numberOfX == 1){
                                        fields.get(z).setText("o");
                                        fields.get(z).setEnabled(false);
                                        nrOfMoves--;
                                    }
                                }
                                resetNumbers();
                            }
                            else if(z==6 || z==2){
                                for(int l=2; l<=6; l+=2){
                                    findValues(l);
                                }
                                for(int l=2; l<=6; l+=2){
                                    if(numberOfNothing==2 && numberOfX == 1) {
                                        fields.get(z).setText("o");
                                        fields.get(z).setEnabled(false);
                                        nrOfMoves--;
                                    }
                                }
                                resetNumbers();
                            }
                        }
                    }
                }
                resetNumbers();
            }
        }

        //columns
        if(nrOfMoves==1) {
            for (int i = 0; i < 3; i++) {
                for (int n = i; n <= 8; n += 3) {
                    findValues(n);
                }
                if (numberOfNothing == 2 && numberOfX == 1) {
                    //Check if box is empty and check rows containing this element and
                    // potentially diagonals
                    for (int j = i; j <= 8; j += 3) {
                        if (fields.get(j).getText().equals("")) {
                            resetNumbers();
                            //Checks diagonals
                            if(nrOfMoves==1) {
                                if (j == 0 || j == 8) {
                                    for (int k = 0; k <= 8; k += 4) {
                                        findValues(k);
                                    }
                                    for (int k = 0; k <= 8; k += 4) {
                                        if (numberOfNothing == 2 && numberOfX == 1) {
                                            fields.get(j).setText("o");
                                            fields.get(j).setEnabled(false);
                                            nrOfMoves--;
                                        }
                                    }
                                    resetNumbers();
                                } else if (j == 6 || j == 2) {
                                    for (int l = 2; l <= 6; l += 2) {
                                        findValues(l);
                                    }
                                    for (int l = 2; l <= 6; l += 2) {
                                        if (numberOfNothing == 2 && numberOfX == 1) {
                                            fields.get(j).setText("o");
                                            fields.get(j).setEnabled(false);
                                            nrOfMoves--;
                                        }
                                    }
                                    resetNumbers();
                                }
                            }
                            //Check rows
                            if (nrOfMoves == 1) {
                                //a blank is in the upper row. Check if forkable and insert if it is.
                                if (j == 0 || j == 1 || j == 2) {
                                    for (int z = 0; z < 3; z++) {
                                        findValues(z);
                                    }
                                    if (numberOfNothing == 2 && numberOfX == 1) {
                                        fields.get(j).setText("o");
                                        fields.get(j).setEnabled(false);
                                        nrOfMoves--;
                                    }
                                    resetNumbers();
                                }
                                //a blank is in second row. Check if forkable and insert if it is.
                                else if (j == 3 || j == 4 || j == 5) {
                                    for (int z = 3; z < 6; z++) {
                                        findValues(z);
                                    }
                                    if (numberOfNothing == 2 && numberOfX == 1) {
                                        fields.get(j).setText("o");
                                        fields.get(j).setEnabled(false);
                                        nrOfMoves--;
                                    }
                                    resetNumbers();
                                }
                                //if a blank is in the third row
                                else {
                                    for (int z = 6; z < 9; z++) {
                                        findValues(z);
                                    }
                                    if (numberOfNothing == 2 && numberOfX == 1) {
                                        fields.get(j).setText("o");
                                        fields.get(j).setEnabled(false);
                                        nrOfMoves--;
                                    }
                                    resetNumbers();
                                }
                            }
                        }
                    }
                }
                resetNumbers();
            }
        }
    }

    /**
     * The AI makes a random move
     */
    public void randomMove(){
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i=0; i<9; i++){
            if(fields.get(i).getText().equals("")){
                indices.add(i);
            }
        }
        int index = indices.get(random.nextInt(indices.size()));
        fields.get(index).setText("o");
        fields.get(index).setEnabled(false);
        nrOfMoves--;
    }


    /**
     * resets the current number of X's, O's and empty strings stored
     */
    public void resetNumbers() {
        numberOfX = 0;
        numberOfO = 0;
        numberOfNothing = 0;
    }

    /**
     * Changes the state of a field. Inserts an O as the AI's move
     * @param n  the index of the button where the AI potentially wishes to insert an O (if it's empty)
     * @param numberOfXYZ  The current number of either X og O stored
     */
    public void changeField(int n, int numberOfXYZ){
        if (fields.get(n).getText().equals("")) {
            if (numberOfNothing == 1 && numberOfXYZ == 2 && nrOfMoves != 0) {
                fields.get(n).setText("o");
                fields.get(n).setEnabled(false);
                nrOfMoves--;
            }
        }
    }

    /**
     * Checks whether someone has won the game
     * @param buttons  A list of the buttons and their current state
     * @return  A string containing the winner or empty if no one has won
     */
    public String getWinner(ArrayList<JButton> buttons){
        fields = buttons;
        String winner = "";
        //Check if x has won
        //Columns
        for (int i = 0; i < 3; i++) {
            for (int n = i; n <= 8; n += 3) {
                findValues(n);
            }
            if(numberOfX ==3){
                winner = "x";
            }
            resetNumbers();
        }

        //check rows
        if (winner.equals("")) {
            for (int y = 0; y <= 8; y += 3) {
                for (int z = 0; z <= 2; z++) {
                    findValues(z + y);
                }
                if(numberOfX ==3){
                    winner = "x";
                }
                resetNumbers();
            }
        }

        //Check first diagonal
        if (winner.equals("")) {
            for(int k=0; k<=8; k+=4){
                findValues(k);
            }
            if(numberOfX ==3){
                winner = "x";
            }
            resetNumbers();
        }

        //Check second diagonal
        if (winner.equals("")) {
            for(int l=2; l<=6; l+=2){
                findValues(l);
            }
            if(numberOfX ==3){
                winner = "x";
            }
            resetNumbers();
        }

        //Check if o is the winner
        //Columns
        for (int i = 0; i < 3; i++) {
            for (int n = i; n <= 8; n += 3) {
                findValues(n);
            }
            if(numberOfO ==3){
                winner = "o";
            }
            resetNumbers();
        }

        //check rows
        if (winner.equals("")) {
            for (int y = 0; y <= 8; y += 3) {
                for (int z = 0; z <= 2; z++) {
                    findValues(z + y);
                }
                if(numberOfO ==3){
                    winner = "o";
                }
                resetNumbers();
            }
        }

        //Check first diagonal
        if (winner.equals("")) {
            for(int k=0; k<=8; k+=4){
                findValues(k);
            }
            if(numberOfO ==3){
                winner = "o";
            }
            resetNumbers();
        }

        //Check second diagonal
        if (winner.equals("")) {
            for(int l=2; l<=6; l+=2){
                findValues(l);
            }
            if(numberOfO ==3){
                winner = "o";
            }
            resetNumbers();
        }
        return winner;
    }

}
