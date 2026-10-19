import java.util.ArrayDeque;
import java.util.Deque;

public class SimpleStringBuilder {
    private String text = "";
    private final Deque<String> history = new ArrayDeque<>();

    public void append(String s) {
        history.push(text);
        text = text + s;
    }

    public void undo() {
        if (!history.isEmpty()) {
            text = history.pop();
        }
    }

    @Override
    public String toString() {
        return text;
    }

    public static void main(String[] args) {
        SimpleStringBuilder b = new SimpleStringBuilder();
        b.append("Hello");
        System.out.println(b);
        b.append(", ");
        System.out.println(b);
        b.append("world");
        System.out.println(b);
        b.undo();
        System.out.println(b);
        b.append("AQA!");
        System.out.println(b);
    }
}
