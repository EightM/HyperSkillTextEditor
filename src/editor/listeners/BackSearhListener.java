package editor.listeners;

import editor.SearchHistory;
import editor.SearchResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class BackSearhListener implements ActionListener {

    private final SearchHistory searchHistory;
    private final JTextArea mainEditor;

    public BackSearhListener(JTextArea mainEditor, SearchHistory searchHistory) {
        this.searchHistory = searchHistory;
        this.mainEditor = mainEditor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Optional<SearchResult> searchResult = searchHistory.getLastSearchResult();
        if (searchResult.isPresent()) {
            mainEditor.setCaretPosition(searchResult.get().getIndex() + searchResult.get().getFoundText().length());
            mainEditor.select(searchResult.get().getIndex(),
                    searchResult.get().getIndex() + searchResult.get().getFoundText().length());
            mainEditor.grabFocus();
        }
    }
}
