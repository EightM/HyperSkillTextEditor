package editor;

import editor.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TextEditor extends JFrame {

    private final SearchHistory searchHistory = new SearchHistory();

    public TextEditor() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 650);
        setTitle("Text editor");
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JTextArea mainEditor = new JTextArea();
        mainEditor.setName("TextArea");
        JScrollPane jScrollPane = new JScrollPane(mainEditor);
        jScrollPane.setName("ScrollPane");
        jScrollPane.setVisible(true);
        add(jScrollPane);

        JPanel topPanel = new JPanel();
        JTextField fileName = new JTextField(10);
        fileName.setName("FilenameField");

        JButton saveButton = new JButton(new ImageIcon("./save.png"));
        saveButton.setName("SaveButton");
        saveButton.addActionListener(new SaveListener(mainEditor, fileName));

        JButton loadButton = new JButton(new ImageIcon("./open.png"));
        loadButton.setName("OpenButton");
        loadButton.addActionListener(new LoadListener(mainEditor, fileName));

        JTextField searchField = new JTextField(15);
        searchField.setName("SearchField");

        JCheckBox useRegex = new JCheckBox("Use regex");
        useRegex.setName("UseRegExCheckbox");
        JButton searchButton = new JButton(new ImageIcon("./search.png"));
        searchButton.setName("StartSearchButton");

        searchButton.addActionListener(new SearchListener(mainEditor, searchField, searchHistory, useRegex));

        JButton previousResultButton = new JButton(new ImageIcon("./arrow_left.png"));
        previousResultButton.addActionListener(new BackSearhListener(mainEditor, searchHistory));
        previousResultButton.setName("PreviousMatchButton");

        JButton nextResultButton = new JButton(new ImageIcon("./arrow_right.png"));
        nextResultButton.setName("NextMatchButton");
        nextResultButton.addActionListener(new NextSearchListener(mainEditor, searchField, searchHistory, useRegex));

        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(previousResultButton);
        topPanel.add(nextResultButton);
        topPanel.add(useRegex);
        topPanel.setVisible(true);
        add(topPanel, BorderLayout.NORTH);

        setJMenuBar(createMenuBar(mainEditor, fileName));

    }

    private JMenuBar createMenuBar(JTextArea mainEditor, JTextField fileName) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setName("MenuFile");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("MenuLoad");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        saveMenuItem.addActionListener(new SaveListener(mainEditor, fileName));
        loadMenuItem.addActionListener(new LoadListener(mainEditor, fileName));
        exitMenuItem.addActionListener(actionEvent -> dispose());

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        return menuBar;
    }
}
