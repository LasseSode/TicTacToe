import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class YatzyPlayer {
    private String name;
    private List<JTextField> points;

    /**
     * Creates a new YatzyPlayer object
     *
     * @param name The name of the player
     */
    public YatzyPlayer(String name) {
        points = new ArrayList<>();
        this.name = name;
    }

    /**
     * Adds a textfield to the players list
     *
     * @param t The textfield to be added.
     */
    public void addPoints(JTextField t) {
        points.add(t);
    }

    /**
     * Returns the name of the player
     *
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a List of the points the player currently has
     *
     * @return a List of the points the player currently has
     */
    public List<Integer> getPoints() {
        List<Integer> intPoints = new ArrayList<>();
        for(JTextField t : points){
            int p =0;
            if(!t.getText().equals("")) {
                p = Integer.parseInt(t.getText());
            }
            intPoints.add(p);
        }
        return intPoints;
    }

    /**
     * Get the JTextField at given index
     * @param i The index
     * @return The JTextField at the given index
     */
    public JTextField getTextField(int i){
        return points.get(i);
    }

    public List<JTextField> getTextFields(){return points;}

}