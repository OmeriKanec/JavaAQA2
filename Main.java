public class Main {
    static class EmptyChecklist {
        void prepareData() { }
    }

    public static void main(String[] args) {
        TestPlanScanner.buildPlan(Checklist.class);
        System.out.println();
        TestPlanScanner.buildPlan(EmptyChecklist.class);
    }
}
