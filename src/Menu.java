
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class Menu extends JFrame {
    private DefaultListModel<String> modelList = new DefaultListModel<>();
    private DefaultListModel<String> dataList = new DefaultListModel<>();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JScrollPane dataScrollPane;
    private final String path = "/Users/esherow/Desktop/Java/Compiler/Compiler/src/";
    private String selectedData;
    Controller controller;
    public Menu(Controller controller) {
        this.controller = controller;
        setTitle("Modelling framework sample");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JLabel selectLabel = new JLabel("Select model and data");
        leftPanel.add(selectLabel);

        // Data Table
        JPanel childLeftPanel = new JPanel(new BorderLayout());
        childLeftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel listPanel = new JPanel(new GridLayout(1, 2));
        modelList.addElement("Model1");
        modelList.addElement("Model2");
        modelList.addElement("Model3");
        modelList.addElement("MultiAgentSim");

        JList<String> listModel = new JList<>(modelList);
        listModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dataScrollPane.setVisible(true);
            }
        });
        JScrollPane modelScrollPane = new JScrollPane(listModel);
        listPanel.add(modelScrollPane);

        JList<String> listData = new JList<>(dataList);
        listData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedData = listData.getSelectedValue();
            }
        });
        dataScrollPane = new JScrollPane(listData);
        dataScrollPane.setVisible(false);
        listPanel.add(dataScrollPane);
        childLeftPanel.add(listPanel, BorderLayout.CENTER);

        JButton runModelButton = new JButton("Run model");
        runModelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedData.isBlank()) return;
                controller.readDataFrom(path + selectedData);
                setColumnNames();
                setData();
            }
        });
        childLeftPanel.add(runModelButton,BorderLayout.SOUTH);

        leftPanel.add(childLeftPanel);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton runScriptButton = new JButton("Run script from file");

        runScriptButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("user.home");
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile(); // This is a selected script.
                if(selectedFile.toString().endsWith(".groovy")) controller.runScriptFromFile(selectedFile.toString());
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

        // Right Table
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
//        tableScrollPane.setVisible(false);
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
        runButton.addActionListener(e -> {
            setColumnNames();
            setData();
            String[] dataSc = controller.runScript(scriptTextField.getText());
            tableModel.addRow(dataSc);
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( e -> {
            frame.dispose();
        });
        buttonsPanel.add(runButton);
        buttonsPanel.add(cancelButton);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setDataModel(List<String> dataModel) {
        for(int i = 0; i < dataModel.size(); i++) dataList.addElement(dataModel.get(i));
    }

    public void setColumnNames() {
        tableModel.setColumnCount(0);
        String[] columnNames = controller.getLLvalues();
        for(int i = 0; i < columnNames.length; i++) tableModel.addColumn(columnNames[i]);
    }

    public void setData() {
        tableModel.setRowCount(0);
        String[][] data = controller.getBindFields();
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }
}
