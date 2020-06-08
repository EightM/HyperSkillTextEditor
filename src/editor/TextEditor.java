package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

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

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> {
            Path newFile = Paths.get(fileName.getText());
            byte[] data = mainEditor.getText().getBytes();
            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(newFile, CREATE, TRUNCATE_EXISTING)
            )){
                out.write(data, 0, data.length);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener(e -> {
            mainEditor.setText(null);
            String name = fileName.getText();
            if (!name.isEmpty()) {
                Path path = Paths.get(name);
                try {
                    String content = new String(Files.readAllBytes(path));
                    mainEditor.setText(content);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        topPanel.add(fileName);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.setVisible(true);
        add(topPanel, BorderLayout.NORTH);
    }
}
