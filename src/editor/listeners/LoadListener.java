package editor.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadListener implements ActionListener {
    private final JTextArea mainEditor;
    private final JTextField fileName;

    public LoadListener(JTextArea mainEditor, JTextField filename) {
        this.mainEditor = mainEditor;
        this.fileName = filename;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
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
    }
}
