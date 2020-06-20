package editor;

import editor.listeners.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TextEditor extends JFrame {

    private final List<SearchResult> searchHistory = new ArrayList<>();


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

        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setName("FileChooser");
        jFileChooser.setVisible(true);

        JPanel topPanel = new JPanel();
        JTextField fileName = new JTextField(10);
        fileName.setName("FilenameField");

        JButton saveButton = new JButton(new ImageIcon("./save.png"));
        saveButton.setName("SaveButton");
        saveButton.addActionListener(new SaveListener(mainEditor, fileName, jFileChooser));

        JButton loadButton = new JButton(new ImageIcon("./open.png"));
        loadButton.setName("OpenButton");
        loadButton.addActionListener(new LoadListener(mainEditor, fileName, jFileChooser));

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
        nextResultButton.addActionListener(new NextSearchListener(mainEditor, searchHistory));

        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(previousResultButton);
        topPanel.add(nextResultButton);
        topPanel.add(useRegex);
        topPanel.setVisible(true);
        add(topPanel, BorderLayout.NORTH);

        setJMenuBar(createMenuBar(mainEditor, fileName, searchField, useRegex, jFileChooser));

    }

    private JMenuBar createMenuBar(JTextArea mainEditor, JTextField fileName, JTextField searchField,
                                   JCheckBox useRegex, JFileChooser jFileChooser) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setName("MenuFile");
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem loadMenuItem = new JMenuItem("Open");
        loadMenuItem.setName("MenuOpen");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenuItem startSearch = new JMenuItem("Start search");
        startSearch.setName("MenuStartSearch");
        JMenuItem previousMatch = new JMenuItem("Previous search");
        previousMatch.setName("MenuPreviousMatch");
        JMenuItem nextMatch = new JMenuItem("Next match");
        nextMatch.setName("MenuNextMatch");
        JMenuItem useRegexMenuItem = new JMenuItem("Use regular expressions");
        useRegexMenuItem.setName("MenuUseRegExp");

        saveMenuItem.addActionListener(new SaveListener(mainEditor, fileName, jFileChooser));
        loadMenuItem.addActionListener(new LoadListener(mainEditor, fileName, jFileChooser));
        exitMenuItem.addActionListener(actionEvent -> dispose());

        startSearch.addActionListener(new SearchListener(mainEditor, searchField, searchHistory, useRegex));
        nextMatch.addActionListener(new NextSearchListener(mainEditor, searchHistory));
        previousMatch.addActionListener(new BackSearhListener(mainEditor, searchHistory));
        useRegexMenuItem.addActionListener(actionEvent -> useRegex.setSelected(!useRegex.isSelected()));

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearch);
        searchMenu.add(previousMatch);
        searchMenu.add(nextMatch);
        searchMenu.add(useRegexMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        return menuBar;
    }
}
