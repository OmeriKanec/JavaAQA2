import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FlakyDetector {
    public static Set<String> failed(List<String> run) {
        return testsWithStatus(run, "FAILED");
    }

    public static Set<String> passed(List<String> run) {
        return testsWithStatus(run, "PASSED");
    }

    public static List<String> flaky(List<String> run1, List<String> run2) {
        Set<String> candidates = failed(run1);
        candidates.retainAll(passed(run2));
        return new ArrayList<>(candidates);
    }
// Так было бы правильнее определять flaky тесты, незакомменченный метод соответствует требованиям: 
// "T3	List<String> flaky(List<String> run1, List<String> run2)	тесты, которые упали в первом прогоне и прошли во втором"
//     public static List<String> flaky(List<String> run1, List<String> run2) {
//     Set<String> recovered = failed(run1);
//     recovered.retainAll(passed(run2));

//     Set<String> regressed = passed(run1);
//     regressed.retainAll(failed(run2));

//     recovered.addAll(regressed);
//     return new ArrayList<>(recovered);
// }

    public static Map<String, Long> statusSummary(List<String> run) {
        Map<String, Long> summary = new LinkedHashMap<>();
        for (String entry : run) {
            String status = entry.split("\\|", 2)[1];
            summary.merge(status, 1L, Long::sum);
        }
        return summary;
    }

    private static Set<String> testsWithStatus(List<String> run, String status) {
        return run.stream()
                .map(entry -> entry.split("\\|", 2))
                .filter(parts -> status.equals(parts[1]))
                .map(parts -> parts[0])
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
