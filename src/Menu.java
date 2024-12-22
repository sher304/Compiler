import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Modelling framework sample");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel selectLabel = new JLabel("Select model and data");
        leftPanel.add(selectLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        DefaultListModel<String> modelList = new DefaultListModel<>();
        modelList.addElement("Model1");
        modelList.addElement("Model2");
        modelList.addElement("Model3");
        modelList.addElement("MultiAgentSim");

        JList<String> listModel = new JList<>(modelList);
        JScrollPane modelScrollPane = new JScrollPane(listModel);
        listPanel.add(modelScrollPane);

        DefaultListModel<String> dataList = new DefaultListModel<>();
        dataList.addElement("HS6_EU_all.txt");
        dataList.addElement("data1.txt");
        dataList.addElement("data2.txt");
        dataList.addElement("data3.txt");

        JList<String> listData = new JList<>(dataList);
        JScrollPane dataScrollPane = new JScrollPane(listData);
        dataScrollPane.setVisible(false);
        listPanel.add(dataScrollPane);

        leftPanel.add(listPanel);

        JButton runModelButton = new JButton("Run model");
        leftPanel.add(runModelButton);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton runScriptButton = new JButton("Run script from file");

        runScriptButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("user.home");
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); // This is a selected script.

            }
        });

        runScriptButton.setPreferredSize(new Dimension(150, 50));
        JButton createScriptButton = new JButton("Create and run ad hoc script");

        createScriptButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> showScriptDialog());
        });
        createScriptButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(runScriptButton);
        buttonPanel.add(createScriptButton);

        String[] columnNames = {"", "2015", "2016", "2017", "2018", "2019"};
        Object[][] data = {
                {"twKI", "1,03", "1,03", "1,03", "1,03", "1,03"},
                {"twKS", "1,04", "1,04", "1,04", "1,04", "1,04"},
                {"twINW", "1,12", "1,12", "1,12", "1,12", "1,12"},
                {"twEKS", "1,13", "1,13", "1,13", "1,13", "1,13"},
                {"twIMP", "1,14", "1,14", "1,14", "1,14", "1,14"},
                {"KI", "1 023 752,2", "1 054 464,8", "1 086 098,7", "1 118 681,7", "1 152 242,1"},
                {"KS", "315 397", "328 012,9", "341 133,4", "354 778,7", "368 969,9"},
                {"INW", "348 358", "390 161", "436 980,3", "489 417,9", "548 148,1"},
                {"EKS", "811 108,6", "916 552,7", "1 035 704,6", "1 170 346,2", "1 322 491,2"},
                {"IMP", "784 342,4", "894 150,3", "1 019 331,4", "1 162 037,8", "1 324 723,1"},
                {"PKB", "1 714 273,4", "1 795 041", "1 880 585,6", "1 971 186,7", "2 067 128,2"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setVisible(false);
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(rightPanel);
    }

    public void showScriptDialog() {
        JFrame frame = new JFrame("Script");
        frame.setVisible(true);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        JTextArea scriptTextField = new JTextArea();
        JScrollPane scriptTextFieldScroll = new JScrollPane(scriptTextField);
        frame.add(scriptTextFieldScroll, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel(new GridLayout(1 , 2));
        JButton runButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> {
            frame.dispose();
        });
        buttonsPanel.add(runButton);
        buttonsPanel.add(cancelButton);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
