import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


enum Status { PASSED, FAILED, SKIPPED }

record TestResult(String name, Status status, long durationMs) { }

public class TestLogParser {
    public record ParseResult(List<TestResult> results, List<String> errors) {
        public ParseResult {
            results = List.copyOf(results);
            errors = List.copyOf(errors);
        }
    }

    
    private record TestName(String name) { }

    public static ParseResult parse(List<String> lines) {
        Map<TestName, TestResult> latest = new LinkedHashMap<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String errorPrefix = "Строка " + (i + 1) + ": " + line + " - ";
            if (line == null) {
                errors.add(errorPrefix + "строка отсутствует (null)");
                continue;
            }

            String[] fields = line.split("\\|", -1);
            if (fields.length != 3) {
                errors.add(errorPrefix + "ожидалось 3 поля через |, получено " + fields.length);
                continue;
            }

            String name = fields[0].trim();
            String statusText = fields[1].trim();
            String durationText = fields[2].trim();
            if (name.isEmpty()) {
                errors.add(errorPrefix + "пустое имя теста");
                continue;
            }

            Status status;
            try {
                status = Status.valueOf(statusText);
            } catch (IllegalArgumentException e) {
                errors.add(errorPrefix + "неизвестный статус: " + statusText);
                continue;
            }

            long durationMs;
            try {
                durationMs = Long.parseLong(durationText);
            } catch (NumberFormatException e) {
                errors.add(errorPrefix + "не число в длительности или значение вне диапазона long: "
                        + durationText);
                continue;
            }
            if (durationMs < 0) {
                errors.add(errorPrefix + "отрицательная длительность: " + durationMs);
                continue;
            }

            TestName key = new TestName(name);
            latest.remove(key);
            latest.put(key, new TestResult(name, status, durationMs));
        }

        return new ParseResult(new ArrayList<>(latest.values()), errors);
    }

    public static Optional<TestResult> firstFailed(List<TestResult> results) {
        return results.stream()
                .filter(result -> result.status() == Status.FAILED)
                .findFirst();
    }

    public static void main(String[] args) {
        Path logPath;
        if (args.length > 0) {
            logPath = Path.of(args[0]);
        } else {
            logPath = Path.of("run.log");
        }
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(logPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Не удалось прочитать " + logPath + ": " + e.getMessage());
            return;
        }

        ParseResult parsed = parse(lines);
        System.out.println("Результаты:");
        for (TestResult result : parsed.results()) {
            System.out.println("  " + result.name() + " - " + result.status()
                    + " (" + result.durationMs() + " ms)");
        }
        if (parsed.errors().isEmpty()) {
            System.out.println("Битые строки: нет");
        } else {
            System.out.println("Битые строки: " + String.join("; ", parsed.errors()));
        }
        System.out.println("Первый упавший: "
                + firstFailed(parsed.results()).map(TestResult::name).orElse("Упадений нет"));
    }
}
