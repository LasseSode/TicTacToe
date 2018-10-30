import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class AIKrydsOgBolleImpl extends AI{

    public AIKrydsOgBolleImpl(Difficulty diff) {
        super(diff);
    }

    @Override
    public void forkMove() {

    }

    
    public ArrayList<JButton> computeRemove(ArrayList<JButton> buttons, int roundNo) {
        return buttons;
    }
}
