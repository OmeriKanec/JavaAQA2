import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ParallelRunner {
    public static Callable<TestResult> fakeTest(String name, long durationMs, Status status) {
        return () -> {
            Thread.sleep(durationMs);
            return new TestResult(name, status, durationMs);
        };
    }

    public static List<TestResult> run(List<Callable<TestResult>> tests)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<TestResult> results = new ArrayList<>();
        long elapsedMs;

        try {
            long startedAt = System.nanoTime();
            List<Future<TestResult>> futures = executor.invokeAll(tests);

            for (Future<TestResult> future : futures) {
                results.add(future.get());
            }

            elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAt);
        } finally {
            executor.shutdown();
        }

        long passed = results.stream()
                .filter(result -> result.status() == Status.PASSED)
                .count();
        long failed = results.stream()
                .filter(result -> result.status() == Status.FAILED)
                .count();
        long totalMs = results.stream()
                .mapToLong(TestResult::durationMs)
                .sum();

        System.out.println("Прошло: " + passed + ", упало: " + failed);
        System.out.println("Суммарное время тестов: " + totalMs + " ms");
        System.out.println("Реальное время прогона: " + elapsedMs + " ms");
        System.out.println("Параллельность сэкономила: " + (totalMs - elapsedMs) + " ms");

        return results;
    }
}
