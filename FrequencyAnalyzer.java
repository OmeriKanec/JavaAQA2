import java.util.LinkedHashMap;
import java.util.Map;

public class FrequencyAnalyzer {
    public static <T> Map<T, Long> frequency(T[] items) {
        Map<T, Long> counts = new LinkedHashMap<>();
        for (T item : items) {
            counts.merge(item, 1L, Long::sum);
        }
        return counts;
    }
}
