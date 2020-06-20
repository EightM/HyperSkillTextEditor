package editor;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSearch implements Command {

    private final JTextArea mainEditor;
    private final String searchText;
    private final boolean useRegex;
    private final int beginIndex;
    private final SearchHistory searchHistory;

    public CommandSearch(JTextArea mainEditor, String searchText, SearchHistory searchHistory, boolean useRegex) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.useRegex = useRegex;
        this.beginIndex = 0;
        this.searchHistory = searchHistory;
    }

    public CommandSearch(JTextArea mainEditor, String searchText, SearchHistory searchHistory, int beginIndex, boolean useRegex) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.useRegex = useRegex;
        this.beginIndex = beginIndex;
        this.searchHistory = searchHistory;
    }

    @Override
    public void execute() {
        SearchWorker searchWorker = new SearchWorker();
        searchWorker.execute();
    }

    class SearchWorker extends SwingWorker<SearchResult, Object> {

        @Override
        protected SearchResult doInBackground() {
            if (useRegex) {
                return regexSearch();
            } else {
                return simpleSearch();
            }
        }

        private SearchResult regexSearch() {
            Pattern searchPattern = Pattern.compile(searchText);
            Matcher matcher = searchPattern.matcher(mainEditor.getText());
            boolean founded = matcher.find(beginIndex);
            if (founded) {
                if (beginIndex != 0) {
                    searchHistory.saveSearchResult(new SearchResult(mainEditor.getSelectionStart(), mainEditor.getSelectedText()));
                }
                SearchResult searchResult = new SearchResult(matcher.start(), matcher.group());

                return searchResult;
            }

            return null;
        }

        private SearchResult simpleSearch() {
            int index = mainEditor.getText().indexOf(searchText, beginIndex);

            if (index == -1) {
                return null;
            }
            if (beginIndex != 0) {
                searchHistory.saveSearchResult(new SearchResult(mainEditor.getSelectionStart(), mainEditor.getSelectedText()));
            }

            SearchResult searchResult = new SearchResult(index, searchText);
            return searchResult;
        }

        @Override
        protected void done() {
            try {
                SearchResult searchResult = get();

                if (searchResult == null) {
                    return;
                }

                mainEditor.setCaretPosition(searchResult.getIndex() + searchResult.getFoundText().length());
                mainEditor.select(searchResult.getIndex(),
                        searchResult.getIndex() + searchResult.getFoundText().length());
                mainEditor.grabFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
