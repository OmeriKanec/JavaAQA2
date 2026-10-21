import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunReport {
    public static void printSummary(List<TestResult> run) {
        Map<Status, List<String>> namesByStatus = run.stream()
                .collect(Collectors.groupingBy(
                        TestResult::status,
                        () -> new EnumMap<>(Status.class),
                        Collectors.mapping(TestResult::name, Collectors.toList())
                ));

        List<String> slowestTests = run.stream()
                .sorted(Comparator.comparingLong(TestResult::durationMs).reversed())
                .limit(3)
                .map(TestResult::name)
                .toList();

        double averagePassedMs = run.stream()
                .filter(result -> result.status() == Status.PASSED)
                .collect(Collectors.averagingLong(TestResult::durationMs));

        System.out.println("=== Сводка прогона ===");
        Arrays.stream(Status.values()).forEach(status -> {
            List<String> names = namesByStatus.getOrDefault(status, List.of());
            System.out.println(status + " (" + names.size() + "): "
                    + String.join(", ", names));
        });
        System.out.println("Топ-3 долгих: " + String.join(", ", slowestTests));
        System.out.println("Среднее время прошедших: " + averagePassedMs + " ms");
    }
}
