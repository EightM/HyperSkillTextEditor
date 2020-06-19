package editor.listeners;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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

    public LoadListener(JTextArea mainEditor, JTextField filename) {
        this.mainEditor = mainEditor;
        this.fileName = filename;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jFileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
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
