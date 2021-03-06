package editor;

import javax.swing.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSearch implements Command {

    private final JTextArea mainEditor;
    private final String searchText;
    private final List<SearchResult> searchHistory;

    public CommandSearch(JTextArea mainEditor, String searchText, List<SearchResult> searchHistory) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.searchHistory = searchHistory;
    }

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
            searchHistory.clear();
            Pattern searchPattern = Pattern.compile(searchText);
            Matcher matcher = searchPattern.matcher(mainEditor.getText());
            while (matcher.find()) {
                searchHistory.add(new SearchResult(matcher.start(), matcher.group()));
            }

            return searchHistory;
        }

        @Override
        protected void done() {
            try {
                List<SearchResult> searchResultList = get();

                if (searchResultList == null) {
                    return;
                }

                SearchResult searchResult = searchResultList.get(0);

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
