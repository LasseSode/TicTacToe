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
    private JPanel emptyPanel;
    private JLabel title;
    private JComboBox<String> gameType;
    private JComboBox<String> difficulty;

    public GameClient(){
        frame = new JFrame("Classic games");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(500,500));

        title = new JLabel("Classic Games");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        contentPane.add(title, BorderLayout.NORTH);

        emptyPanel = new JPanel();
        emptyPanel.setMinimumSize(new Dimension(100,500));
        contentPane.add(emptyPanel, BorderLayout.WEST);
        contentPane.add(emptyPanel, BorderLayout.EAST);

        mainPanel= new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        gameType = new JComboBox<>(addGames());
        mainPanel.add(gameType);

        difficulty = new JComboBox<>(addDifficulties());
        mainPanel.add(difficulty);

        frame.setVisible(true);
    }

    public String[] addGames(){
        String[] games = {"Tic Tac Toe"};
        return games;
    }

    public String[] addDifficulties(){
        String[] difficulties = {"Easy","Medium","Hard","Insane"};
        return difficulties;
    }

    public static void main(String[] args) {
        new GameClient();
    }
}
