import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> run1 = List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|FAILED",
                "CommentTest|FAILED",
                "LogoutTest|PASSED"
        );

        List<String> run2 = List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|PASSED",
                "CommentTest|FAILED",
                "StatsTest|PASSED"
        );

        System.out.println("Flaky-кандидаты: " + FlakyDetector.flaky(run1, run2));
        System.out.println("Прогон 2, сводка: " + FlakyDetector.statusSummary(run2));

        String[] words = {"up", "down", "up", "up", "left"};
        Integer[] numbers = {3, 1, 3, 3, 7, 1};

        System.out.println(FrequencyAnalyzer.frequency(words));
        System.out.println(FrequencyAnalyzer.frequency(numbers));
    }
}
