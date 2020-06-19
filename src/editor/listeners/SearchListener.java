package editor.listeners;

import editor.CommandSearch;
import editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {

    private final JTextArea mainEditor;
    private final JCheckBox useRegex;
    private final JTextField searchField;

    public SearchListener(JTextArea mainEditor, JTextField searchField, JCheckBox useRegex) {
        this.mainEditor = mainEditor;
        this.useRegex = useRegex;
        this.searchField = searchField;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        (new CommandSearch(mainEditor, searchField.getText(), useRegex.isSelected())).execute();
    }
}
