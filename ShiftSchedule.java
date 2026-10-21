import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShiftSchedule {
    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    record WorkSheet(String name, List<DayOfWeek> workDays) { }

    public static void printOverlappingDays(List<WorkSheet> sheets) {
        sheets.stream()
                .flatMap(sheet -> sheet.workDays().stream().distinct())
                .collect(Collectors.groupingBy(day -> day, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted()
                .forEach(System.out::println);
    }

    public static void test() {
        var denSheet = new WorkSheet("Денис", List.of(
                DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY));
        var benSheet = new WorkSheet("Бен", List.of(
                DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY));
        var lisSheet = new WorkSheet("Лиза", List.of(
                DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
        var sheets = List.of(denSheet, benSheet, lisSheet);

        printOverlappingDays(sheets);
    }

    public static void main(String[] args) {
        test();
    }
}
