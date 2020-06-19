package editor;

public class SearchResult {
    private final int index;
    private final String foundText;

    public SearchResult(int index, String foundText) {
        this.index = index;
        this.foundText = foundText;
    }

    public int getIndex() {
        return index;
    }

    public String getFoundText() {
        return foundText;
    }
}
