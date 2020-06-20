package editor;

import java.util.ArrayDeque;
import java.util.Optional;

public class SearchHistory {
    private final ArrayDeque<SearchResult> searchResults = new ArrayDeque<>();

    public Optional<SearchResult> getLastSearchResult() {
        if (searchResults.peekLast() != null) {
            return Optional.of(searchResults.pollLast());
        }

        return Optional.empty();
    }

    public void saveSearchResult(SearchResult searchResult) {
        searchResults.offerLast(searchResult);
    }
}
