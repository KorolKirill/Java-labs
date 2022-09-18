import java.util.ArrayList;
import java.util.List;

public class Journal {
    List<BookEntry> list;
    public Journal() {
        list = new ArrayList<>();
    }

    public void addEntry(BookEntry entry) {
        list.add(entry);
    }
    public Iterable<BookEntry> getEntries() {
        return list;
    }
}