# mini-aqa

Минимальный Maven-проект: Java 21 и JUnit Jupiter 5. Требуются JDK 21 и Maven 3.9.x; команды выполняются в каталоге mini-aqa. JAVA_HOME должен указывать на JDK 21, а каталог bin Maven должен быть в PATH.

Собрать jar (включая запуск тестов):

```shell
mvn package
```

Прогнать тесты — ожидается Tests run: 3, Failures: 0, Errors: 0:

```shell
mvn test
```

Запустить собранное приложение:

```shell
java -jar target/mini-aqa-1.0.jar
```

Ожидаемый вывод: Привет, Георгий!

Имя задаётся в src/main/resources/app.properties, кодировка UTF-8. Ресурс читается из classpath, поэтому доступен и внутри jar. После изменения ресурса пересоберите проект.

greet возвращает приветствие для переданного имени. Для пустой строки результат — «Привет, !», для null выбрасывается NullPointerException. Все три случая зафиксированы тестами.

Maven Shade Plugin на этапе package создаёт запускаемый jar с Main-Class: app.App и включает зависимости приложения со scope compile и runtime. Сейчас таких зависимостей нет. JUnit имеет scope test и в итоговый jar не включается. Ресурс app.properties сохраняется внутри jar и читается через getResourceAsStream(). Каталог target/ исключён из git.
