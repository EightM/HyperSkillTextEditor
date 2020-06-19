package editor;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSearch implements Command {

    private final JTextArea mainEditor;
    private final String searchText;
    private final boolean useRegex;
    private final int beginIndex;

    public CommandSearch(JTextArea mainEditor, String searchText, boolean useRegex) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.useRegex = useRegex;
        this.beginIndex = 0;
    }

    public CommandSearch(JTextArea mainEditor, String searchText, int beginIndex, boolean useRegex) {
        this.mainEditor = mainEditor;
        this.searchText = searchText;
        this.useRegex = useRegex;
        this.beginIndex = beginIndex;
    }

    @Override
    public void execute() {
        class SearchWorker extends SwingWorker<Integer, Object> {

            @Override
            protected Integer doInBackground() throws Exception {
                if (useRegex) {
                    Pattern searchPattern = Pattern.compile(searchText);
                    Matcher matcher = searchPattern.matcher(mainEditor.getText());
                    boolean founded = matcher.find(beginIndex);
                    if (founded) {
                        return matcher.start();
                    }
                } else {
                    return mainEditor.getText().indexOf(searchText, beginIndex);
                }

                return 0;
            }

            @Override
            protected void done() {
                try {
                    int index = get();
                    mainEditor.setCaretPosition(index + searchText.length());
                    mainEditor.select(index, index + searchText.length());
                    mainEditor.grabFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        SearchWorker searchWorker = new SearchWorker();
        searchWorker.execute();
    }
}
