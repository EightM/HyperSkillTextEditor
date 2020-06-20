package editor.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadListener implements ActionListener {
    private final JTextArea mainEditor;
    private final JTextField fileName;
    private final JFileChooser jFileChooser;

    public LoadListener(JTextArea mainEditor, JTextField filename, JFileChooser jFileChooser) {
        this.mainEditor = mainEditor;
        this.fileName = filename;
        this.jFileChooser = jFileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int returnValue = jFileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            if (!selectedFile.exists()) {
                mainEditor.setText("");
                return;
            }
            Path path = Paths.get(selectedFile.getAbsolutePath());
            try {
                String content = new String(Files.readAllBytes(path));
                mainEditor.setText(content);
                fileName.setText(selectedFile.getAbsolutePath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
