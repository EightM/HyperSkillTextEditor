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

    public SaveListener(JTextArea mainEditor, JTextField filename) {
        this.mainEditor = mainEditor;
        this.fileName = filename;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
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
    }
}
