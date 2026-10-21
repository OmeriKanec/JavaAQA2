import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// RUNTIME делает аннотацию доступной через reflection во время работы программы.
// При CLASS она останется в .class, но reflection её не увидит.
// При SOURCE она будет отброшена при компиляции.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestId {
    String value();
    int order();
}
