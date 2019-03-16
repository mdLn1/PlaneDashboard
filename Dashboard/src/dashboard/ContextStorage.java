package dashboard;

import java.util.ArrayList;
import java.util.List;

public class ContextStorage {
    
    private static ContextStorage instance = null;

    private List<String> messages;

    private ContextStorage() {
        messages = new ArrayList<>();
    }
    
    public static synchronized ContextStorage getInstance() {
        if (instance == null) {
            instance = new ContextStorage();
        }
        return instance;
    }

    public synchronized void addMessage(String s) {
        messages.add(s);
    }
    @Override
    public String toString() {
        return messages.toString();
    }
}
