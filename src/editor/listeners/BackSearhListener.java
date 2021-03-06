package editor.listeners;

import editor.SearchResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BackSearhListener implements ActionListener {

    private final JTextArea mainEditor;
    private final ArrayList<SearchResult> searchHistory;

    public BackSearhListener(JTextArea mainEditor, List<SearchResult> searchHistory) {
        this.mainEditor = mainEditor;
        this.searchHistory = (ArrayList<SearchResult>) searchHistory;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (searchHistory.isEmpty()) {
            return;
        }

        if (searchHistory.size() == 1) {
            setCaret(searchHistory.get(0));
            return;
        }

        int currentIndex = mainEditor.getSelectionStart();
        SearchResult nextSearchResult;

        for (int i = 0; i < searchHistory.size(); i++) {
            SearchResult currentElement = searchHistory.get(i);
            if (currentElement.getIndex() == currentIndex) {
                nextSearchResult = i == 0 ? searchHistory.get(searchHistory.size() - 1) : searchHistory.get(i - 1);
                setCaret(nextSearchResult);
                break;
            }
        }
    }

    private void setCaret(SearchResult searchResult) {
        mainEditor.setCaretPosition(searchResult.getIndex() + searchResult.getFoundText().length());
        mainEditor.select(searchResult.getIndex(),
                searchResult.getIndex() + searchResult.getFoundText().length());
        mainEditor.grabFocus();
    }
}
