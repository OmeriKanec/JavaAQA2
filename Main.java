import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TestResult> run = List.of(
                new TestResult("LoginTest",        Status.PASSED, 120),
                new TestResult("CreateTaskTest",   Status.PASSED, 940),
                new TestResult("UpdateStatusTest", Status.FAILED, 2100),
                new TestResult("CommentTest",      Status.PASSED, 380),
                new TestResult("DeleteTaskTest",   Status.FAILED, 760),
                new TestResult("StatsTest",        Status.SKIPPED, 0),
                new TestResult("LogoutTest",       Status.PASSED, 90)
        );

        RunReport.printSummary(run);
        System.out.println();
        ShiftSchedule.test();
    }
}
