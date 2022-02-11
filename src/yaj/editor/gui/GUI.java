package yaj.editor.gui;

import yaj.backend.Token;
import yaj.backend.ast.Node;
import yaj.editor.Interpreter;
import yaj.editor.InterpreterCreator;

import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUI {
    public static int tabSize = 2;

    private static String noFileFound = "No File Selected";

    private String fileName = null;

    private JFrame frame;

    private JTextArea editArea;
    private JButton runButton;
    private JButton printASTButton;
    private JButton openButton;
    private JTextArea outputArea;
    private JPanel panel;
    private JButton saveButton;
    private JButton closeButton;
    private JLabel fileSelectorName;
    private JButton quitButton;
    private JScrollPane scroll1;
    private JScrollPane scroll2;

    private InterpreterCreator creator;

    public GUI(InterpreterCreator interpreter) {
        this.creator = interpreter;
        initGUI();
    }

    private void initGUI() {
        // Setup user interface
        frame = new JFrame("Yaj Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.add(panel);
        frame.setVisible(true);

        editArea.setTabSize(tabSize);
        outputArea.setEditable(false);
        editArea.setFont(editArea.getFont().deriveFont(14f));
        outputArea.setFont(outputArea.getFont().deriveFont(14f));

        // Add event listeners for buttons
        runButton.addActionListener(this::runListener);
        printASTButton.addActionListener(this::printListener);
        openButton.addActionListener(this::fileListener);
        saveButton.addActionListener(this::saveListener);
        closeButton.addActionListener(this::closeListener);
        quitButton.addActionListener(this::quitListener);
    }

    /**
     * ActionListener to run the code
     *
     * @param event Action event
     */
    private void runListener(ActionEvent event) {
        String source = editArea.getText();
        execute(source);
    }

    /**
     * ActionListener to print the syntax tree
     *
     * @param event Action event
     */
    private void printListener(ActionEvent event) {
        String source = editArea.getText();
        debug(source);
    }

    /**
     * ActionListener to choose a file
     *
     * @param event Action event
     */
    private void fileListener(ActionEvent event) {
        FileDialog selector = new FileDialog(frame, "Source File", FileDialog.LOAD);
        selector.setDirectory(System.getProperty("user.home"));
        selector.setVisible(true);

        String source = selector.getDirectory() + "/" + selector.getFile();
        if (source.equals("/")) {
            return;
        }

        String text = "";

        try {
            text = new String(Files.readAllBytes(Paths.get(source)));
        } catch (IOException e) {
            outputArea.setText(e.toString());
            return;
        }

        editArea.setText(text);
        fileName = source;
        fileSelectorName.setText(source);
    }

    /**
     * ActionListener to save the file
     *
     * @param event Action event
     */
    private void saveListener(ActionEvent event) {
        save();
    }

    /**
     * ActionListener to close the file
     *
     * @param event Action event
     */
    private void closeListener(ActionEvent event) {
        close();
    }

    /**
     * ActionListener to close the editor
     *
     * @param event Action event
     */
    private void quitListener(ActionEvent event) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Save the current code
     */
    private void save() {
        // Don't try to save if no file opened
        if (fileName == null) {
            return;
        }

        try {
            // Write to file
            PrintWriter writer = new PrintWriter(fileName);
            writer.print(editArea.getText().toString());
            writer.close();
        } catch (FileNotFoundException e) {
            outputArea.setText(e.toString());
        }
    }

    private void close() {
        // Don't try to reset editor text if no file opened
        if (fileName != null) {
            editArea.setText("");
        }
        fileName = null;
        fileSelectorName.setText(noFileFound);
    }

    private void debug(String source) {
        // Tabs cause issues so replace them
        source = source.replace("\t", "    ");

        // Run the code and get the root node to be printed
        final Interpreter interpreter = creator.createInterpreter(source);

        List<Token> tokens = interpreter.lex();
        Node parseTree = interpreter.parse(tokens);

        outputArea.setText(parseTree.toString());
    }

    private void execute(String source) {
        // Tabs cause issues so replace them
        source = source.replace("\t", "    ");

        // Run the code and show the output
        final Interpreter interpreter = creator.createInterpreter(source);

        // Runtime error catching isn't perfect yet, so handle those with a try catch for now
        try {
            interpreter.run();
        } catch (Exception e) {
            outputArea.setText("Error encountered while executing your code: \n");
            outputArea.setText(e.toString());
            return;
        }

        String output = interpreter.getCombinedOutput();

        outputArea.setText(output);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 10, new Insets(0, 0, 0, 0), -1, -1));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(0, -1), null, new Dimension(0, -1), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scroll1 = new JScrollPane();
        panel.add(scroll1, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 5, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputArea = new JTextArea();
        scroll1.setViewportView(outputArea);
        runButton = new JButton();
        runButton.setText("Run");
        panel.add(runButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        printASTButton = new JButton();
        printASTButton.setText("Print AST");
        panel.add(printASTButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openButton = new JButton();
        openButton.setText("Open");
        panel.add(openButton, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fileSelectorName = new JLabel();
        fileSelectorName.setText("No File Selected");
        panel.add(fileSelectorName, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(9, 2, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(360, 1), null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        panel.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(8, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(10, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(1, 10), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(4, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(10, 1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(10, 1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(1, 10), null, null, 0, false));
        scroll2 = new JScrollPane();
        panel.add(scroll2, new com.intellij.uiDesigner.core.GridConstraints(2, 5, 7, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        editArea = new JTextArea();
        scroll2.setViewportView(editArea);
        closeButton = new JButton();
        closeButton.setText("Close");
        panel.add(closeButton, new com.intellij.uiDesigner.core.GridConstraints(8, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setText("Quit");
        panel.add(quitButton, new com.intellij.uiDesigner.core.GridConstraints(8, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
