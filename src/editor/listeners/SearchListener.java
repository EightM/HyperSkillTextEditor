package editor.listeners;

import editor.CommandSearch;
import editor.SearchHistory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {

    private final JTextArea mainEditor;
    private final JCheckBox useRegex;
    private final JTextField searchField;
    private final SearchHistory searchHistory;

    public SearchListener(JTextArea mainEditor, JTextField searchField, SearchHistory searchHistory, JCheckBox useRegex) {
        this.mainEditor = mainEditor;
        this.useRegex = useRegex;
        this.searchField = searchField;
        this.searchHistory = searchHistory;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        (new CommandSearch(mainEditor, searchField.getText(), searchHistory, useRegex.isSelected())).execute();
    }
}
