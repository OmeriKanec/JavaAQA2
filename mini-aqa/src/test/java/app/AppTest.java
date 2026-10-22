package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppTest {
    private final App app = new App();

    @Test
    void greetsRegularName() {
        assertEquals("Привет, Георгий!", app.greet("Георгий"));
    }

    @Test
    void greetsEmptyName() {
        assertEquals("Привет, !", app.greet(""));
    }

    @Test
    void rejectsNullName() {
        assertThrows(NullPointerException.class, () -> app.greet(null));
    }
}
