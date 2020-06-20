package editor;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSearch implements Command {

    private final JTextArea mainEditor;
    private final String searchText;
    private int beginIndex;
    private List<SearchResult> searchHistory;

    public CommandSearch(JTextArea mainEditor, String searchText, List<SearchResult> searchHistory) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.beginIndex = 0;
        this.searchHistory = searchHistory;
    }

//    public CommandSearch(JTextArea mainEditor, String searchText, SearchHistory searchHistory, int beginIndex, boolean useRegex) {
//        this.mainEditor = mainEditor;
//        this.searchText = searchText;
//        this.useRegex = useRegex;
//        this.beginIndex = beginIndex;
//        this.searchHistory = searchHistory;
//    }

    @Override
    public void execute() {
        SearchWorker searchWorker = new SearchWorker();
        searchWorker.execute();
    }

    class SearchWorker extends SwingWorker<List<SearchResult>, Object> {

        @Override
        protected List<SearchResult> doInBackground() {
            return regexSearch();
        }

        private List<SearchResult> regexSearch() {

            Pattern searchPattern = Pattern.compile(searchText);
            Matcher matcher = searchPattern.matcher(mainEditor.getText());
            while (matcher.find()) {
                searchHistory.add(new SearchResult(matcher.start(), matcher.group()));
            }

            return searchHistory;
        }

//        private SearchResult simpleSearch() {
//            int index = mainEditor.getText().indexOf(searchText, beginIndex);
//
//            if (index == -1) {
//                return null;
//            }
//            if (beginIndex != 0) {
//                searchHistory.saveSearchResult(new SearchResult(mainEditor.getSelectionStart(), mainEditor.getSelectedText()));
//            }
//
//            return new SearchResult(index, searchText);
//        }

        @Override
        protected void done() {
            try {
                List<SearchResult> searchHistory = get();

                if (searchHistory == null) {
                    return;
                }

                SearchResult searchResult = searchHistory.get(0);

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
