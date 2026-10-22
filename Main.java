import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Callable<TestResult>> tests = List.of(
                ParallelRunner.fakeTest("LoginTest",        300, Status.PASSED),
                ParallelRunner.fakeTest("CreateTaskTest",   400, Status.PASSED),
                ParallelRunner.fakeTest("UpdateStatusTest", 500, Status.FAILED),
                ParallelRunner.fakeTest("CommentTest",      350, Status.PASSED),
                ParallelRunner.fakeTest("DeleteTaskTest",   450, Status.FAILED),
                ParallelRunner.fakeTest("StatsTest",        400, Status.PASSED),
                ParallelRunner.fakeTest("ProfileTest",      500, Status.PASSED),
                ParallelRunner.fakeTest("LogoutTest",       300, Status.PASSED)
        );

        ParallelRunner.run(tests);
        System.out.println();
        System.out.println(new DataAggregator().aggregateProductInfo("Ноутбук"));
    }
}
