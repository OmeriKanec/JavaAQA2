package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class App {
    public String greet(String name) {
        Objects.requireNonNull(name, "Имя не должно быть null");
        return "Привет, " + name + "!";
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = App.class.getResourceAsStream("/app.properties")) {
            if (input == null) {
                throw new IllegalStateException("Ресурс app.properties не найден");
            }
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        }

        String name = properties.getProperty("student.name");
        if (name == null) {
            throw new IllegalStateException("В app.properties отсутствует student.name");
        }
        System.out.println(new App().greet(name));
    }
}
