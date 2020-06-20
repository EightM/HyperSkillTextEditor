package editor.listeners;

import editor.CommandSearch;
import editor.SearchHistory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextSearchListener implements ActionListener {

    private final JTextArea mainEditor;
    private final JCheckBox useRegex;
    private final JTextField searchField;
    private final SearchHistory searchHistory;;

    public NextSearchListener(JTextArea mainEditor, JTextField searchField, SearchHistory searchHistory, JCheckBox useRegex) {
        this.mainEditor = mainEditor;
        this.useRegex = useRegex;
        this.searchField = searchField;
        this.searchHistory = searchHistory;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int beginIndex = mainEditor.getSelectionEnd();
        (new CommandSearch(mainEditor, searchField.getText(), searchHistory, beginIndex, useRegex.isSelected())).execute();
    }
}
