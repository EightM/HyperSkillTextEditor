package editor;

import editor.listeners.LoadListener;
import editor.listeners.SaveListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 400);
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
        JTextField fileName = new JTextField(15);
        fileName.setName("FilenameField");

        JButton saveButton = new JButton(new ImageIcon("./save.png"));
        saveButton.setName("SaveButton");
        saveButton.addActionListener(new SaveListener(mainEditor, fileName));

        JButton loadButton = new JButton(new ImageIcon("./open.png"));
        loadButton.setName("LoadButton");
        loadButton.addActionListener(new LoadListener(mainEditor, fileName));

        topPanel.add(saveButton);
        topPanel.add(loadButton);
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
