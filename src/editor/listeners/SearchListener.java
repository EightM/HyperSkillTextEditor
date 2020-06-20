package editor.listeners;

import editor.CommandSearch;
import editor.SearchResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchListener implements ActionListener {

    private final JTextArea mainEditor;
    private final JCheckBox useRegex;
    private final JTextField searchField;
    private final List<SearchResult> searchHistory;

    public SearchListener(JTextArea mainEditor, JTextField searchField, List<SearchResult> searchHistory, JCheckBox useRegex) {
        this.mainEditor = mainEditor;
        this.useRegex = useRegex;
        this.searchField = searchField;
        this.searchHistory = searchHistory;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String searchText = useRegex.isSelected() ? searchField.getText() :
                String.format("(%s\\b|%s\\B)", searchField.getText(), searchField.getText());
        (new CommandSearch(mainEditor, searchText, searchHistory)).execute();
    }
}
