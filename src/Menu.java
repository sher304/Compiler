import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Modelling framework sample");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new GridLayout(2, 2, 10, 20));
        leftPanel.add(new Button("Run model"));
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        JPanel rchildBottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        rchildBottomPanel.add(new Button("Run script from file"));
        rchildBottomPanel.add(new Button("Create and run ad hoc script"));
        rightPanel.add(rchildBottomPanel);
        add(leftPanel);
        add(rightPanel);
    }
}
