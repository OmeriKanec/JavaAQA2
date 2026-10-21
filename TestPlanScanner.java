import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestPlanScanner {
    public static List<TestStep> buildPlan(Class<?> clazz) {
        List<TestStep> plan = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(TestId.class)) {
                TestId testId = method.getAnnotation(TestId.class);
                plan.add(new TestStep(testId.value(), method.getName(), testId.order()));
            }
        }

        plan.sort(Comparator.comparingInt(TestStep::order));

        System.out.println("План прогона для " + clazz.getSimpleName() + ":");
        if (plan.isEmpty()) {
            System.out.println("Кейсов нет");
        } else {
            for (int i = 0; i < plan.size(); i++) {
                TestStep step = plan.get(i);
                System.out.println("  " + (i + 1) + ". " + step.id() + " -> " + step.methodName());
            }
        }

        return plan;
    }
}
