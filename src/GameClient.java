import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * The client class where users can select a game and difficulty.
 */
public class GameClient {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel emptyPanel2;
    private JPanel emptyPanel1;
    private JLabel title;
    private JComboBox<String> gameType;
    private JComboBox<Difficulty> difficulty;

    public GameClient(){
        frame = new JFrame("Classic games");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(100, 20));
        frame.setMinimumSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title = new JLabel("Classic Games");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        contentPane.add(title, BorderLayout.NORTH);

        emptyPanel1 = new JPanel();
        emptyPanel1.setMinimumSize(new Dimension(100,500));
        contentPane.add(emptyPanel1, BorderLayout.WEST);

        emptyPanel2 = new JPanel();
        emptyPanel2.setMinimumSize(new Dimension(100,500));
        contentPane.add(emptyPanel2, BorderLayout.EAST);

        mainPanel= new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(new JLabel("Choose the game type:"));

        gameType = new JComboBox<>(addGames());
        mainPanel.add(gameType);

        mainPanel.add(new JLabel("Choose the difficulty:"));

        difficulty = new JComboBox<>(addDifficulties());
        mainPanel.add(difficulty);

        JButton start = new JButton("Start");
        mainPanel.add(start, BorderLayout.SOUTH);

        start.addActionListener(e ->{
                    new TicTacToeGUI(((Difficulty) difficulty.getSelectedItem()));
                });
        frame.setVisible(true);
    }

    public String[] addGames(){
        String[] games = {"Tic Tac Toe"};
        return games;
    }

    public Difficulty[] addDifficulties(){
        Difficulty[] difficulties = {Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD, Difficulty.INSANE};
        return difficulties;
    }



    public static void main(String[] args) {
        new GameClient();
    }
}
