package editor.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class SaveListener implements ActionListener {

    private final JTextArea mainEditor;
    private final JTextField fileName;
    private final JFileChooser jFileChooser;

    public SaveListener(JTextArea mainEditor, JTextField filename, JFileChooser jFileChooser) {
        this.mainEditor = mainEditor;
        this.fileName = filename;
        this.jFileChooser = jFileChooser;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int returnValue = jFileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Path newFile = Paths.get(jFileChooser.getSelectedFile().getAbsolutePath());
            byte[] data = mainEditor.getText().getBytes();
            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(newFile, CREATE, TRUNCATE_EXISTING)
            )) {
                out.write(data, 0, data.length);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
