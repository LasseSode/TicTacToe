import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class represents a yatzy sheet
 * Players can fill in points and press a button to eventually calculate the winner
 */
public class YatzySheet implements KeyListener {
    private ArrayList<YatzyPlayer> players;
    private JFrame frame;
    private int bonusLine;

    /**
     * Create a new yatzySheet object
     */
    public YatzySheet() {
        players = new ArrayList<>();
        makeFrame();
        //TEMPORARY IMPLEMENTATION - IMPLEMENT SO ONLY INTEGER VALUES ARE ALLOWED
        bonusLine=84;
        getBonusLine();
    }

    /**
     * Makes the panel containing the names of the categories
     */
    public void makeCategoryPanel(){
        //A function to make Jlabels for the categories of Yatzy
        Function<Integer,JComponent> function = (Integer i)->{
            Object[] values = YatzyCategories.values();
            String s= values[i].toString();
            JLabel l = new JLabel(s);
            l.setBorder(new LineBorder(Color.BLACK));
            return l;
        };
        frame.add(makePanel("",function));
    }

    /**
     * Let the user set the bonus line
     */
    public void getBonusLine(){
        String s = JOptionPane.showInputDialog("When does a player receive the bonus? (+84 by default)");
        if(s!=null){
            bonusLine=Integer.parseInt(s);
        };
    }

    /**
     * Creates a panel containing all the rows in the sheet
     * @param name Name of the player
     * @param visual A lambda that makes the JComponent
     * @return The panel
     */
    public JPanel makePanel(String name, Function visual){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(YatzyCategories.values().length+1,1));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setBorder(new LineBorder(Color.BLACK));

        panel.add(nameLabel);
        for(int i = 0; i<YatzyCategories.values().length;i++){
            JComponent item = (JComponent)visual.apply(i);
            if(i==6){
                item.setEnabled(false);
            }
            if(i==YatzyCategories.values().length-1){
                item.setEnabled(false);
            }
            panel.add(item);
        }
        return panel;
    }

    /**
     * Make the JFrame
     */
    public void makeFrame(){
        frame = new JFrame("Yatzy");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout();
        makeCategoryPanel();
        makeMenuBar();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        frame.setLocation(screenSize.width/2-frame.getWidth()/2, screenSize.height/2-frame.getHeight()/2);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the menuBar
     */
    public void makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenuItem addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(e->addPlayer());
        menuBar.add(addPlayer);

        JMenuItem reset = new JMenuItem("Reset Game");
        reset.addActionListener(e->reset());
        menuBar.add(reset);

        JMenuItem calculate = new JMenuItem("Calculate");
        calculate.addActionListener(e->calculateWinner());
        menuBar.add(calculate);
    }

    /**
     * Set layout of the frame to be a gridLayout with columns equal to number of players +1
     */
    public void setLayout(){
        frame.setLayout(new GridLayout(1, 1+players.size()));
        frame.setMinimumSize(new Dimension(250+players.size()*50,500));
    }

    /**
     * Adds a player
     */
    public void addPlayer(){
        String name = (String)JOptionPane.showInputDialog("Write your name");
        YatzyPlayer player = new YatzyPlayer(name);
        players.add(player);
        Function<Integer, JComponent> function = (Integer i) ->{
            JTextField tf = new JTextField("");
            player.addPoints(tf);
            tf.setBorder(new LineBorder(Color.BLACK));
            tf.addKeyListener(this);
            return tf;
        };
        setLayout();
        makePanel(name, function);
        updateGUI();
    }

    /**
     * Resets all fields to 0
     */
    public void reset(){
        for(YatzyPlayer p : players){
            for(int i=0;i<YatzyCategories.values().length;i++){
                p.getTextField(i).setText("");
            }
        }
        updateGUI();
    }

    /**
     * Calculates the winner
     */
    public void calculateWinner(){
        YatzyPlayer winner = null;
        int bestSum=0;
        for(YatzyPlayer p : players){
            List<Integer> pointList = p.getPoints();
            int sum = 0;
            for(int i=0;i<YatzyCategories.values().length-1;i++){
                if(i==6){
                    if(sum>=bonusLine) {
                        p.getTextField(i).setText("50");
                        pointList.set(6,50);
                    }
                    else{
                        p.getTextField(i).setText("0");
                        pointList.set(6,0);
                    }
                }
                sum+=pointList.get(i);
            }
            if(sum>bestSum){
                bestSum = sum;
                winner = p;
            }
            p.getTextField(YatzyCategories.values().length-1).setText(""+sum);
        }
    }

    /**
     * Updates the GUI
     */
    public void updateGUI(){
        frame.getContentPane().removeAll();
        makeCategoryPanel();
        for(YatzyPlayer p : players){
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(YatzyCategories.values().length+1,1));

            JLabel l = new JLabel(p.getName());
            l.setBorder(new LineBorder(Color.BLACK));
            panel.add(l);

            for(int i=0;i<YatzyCategories.values().length;i++){
                JTextField t = p.getTextField(i);
                panel.add(t);
            }
            frame.add(panel);
        }
        frame.revalidate();
        frame.repaint();
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            BiConsumer<Integer, Integer> func = (i,j)->{
                if(i>0){
                    moveFocusLeft(i,j);
                }
                else{
                    moveFocusLeft(i,j);
                    moveFocusUp(players.size()-1,j);
                }
            };
            moveFocus(func);
        }

        if (key == KeyEvent.VK_RIGHT) {
            BiConsumer<Integer, Integer> func = (i,j)->{
                if(i<players.size()-1){
                    moveFocusRight(i,j);
                }
                else{
                    moveFocusRight(i,j);
                    moveFocusDown(0,j);
                }
            };
            moveFocus(func);
        }

        if (key == KeyEvent.VK_UP) {
            BiConsumer<Integer, Integer> func = (i,j)->{
                moveFocusUp(i,j);
            };
            moveFocus(func);
        }

        if (key == KeyEvent.VK_DOWN) {
            BiConsumer<Integer, Integer> func = (i,j)->{
                moveFocusDown(i,j);
            };
            moveFocus(func);
        }
    }


    public void moveFocusLeft(int i, int j){
        if (i>0){
            basicFocusMove(i,j,0,-1);
        }
        else{
            basicFocusMove(i,j,0,players.size()-1);
        }
    }

    public void moveFocusRight(int i, int j){
        if (i<players.size()-1){
            basicFocusMove(i,j,0,1);
        }
        else{
            basicFocusMove(i,j,0,-(players.size()-1));
        }
    }

    public void moveFocusUp(int i, int j){
        if (j>0){
            basicFocusMove(i,j,-1,0);
        }
        else{
            basicFocusMove(i,j,0,0);
        }
    }

    public void moveFocusDown(int i, int j){
        if (j<YatzyCategories.values().length-1){
            basicFocusMove(i,j,1,0);
        }
        else{
            basicFocusMove(i,j,0,0);
        }
    }

    public void basicFocusMove(int i, int j, int vertical, int horizontal){
            YatzyPlayer p = players.get(i + horizontal);
            JTextField tf = p.getTextField(j + vertical);
            tf.requestFocus();
    }

    public void moveFocus(BiConsumer lambd){
        int i = 0;
        for(YatzyPlayer p : players){
            List<JTextField> list = p.getTextFields();
            int j =0;
            for(JTextField tf : list){
                if(tf.isFocusOwner()){
                    lambd.accept(i,j);
                    return;
                }
                j++;
            }
            i++;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e){

    }

    public static void main(String[] args) {
        YatzySheet y = new YatzySheet();
    }
}
