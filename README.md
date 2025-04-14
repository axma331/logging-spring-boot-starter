# Logging Spring Boot Starter

📘 **Описание**  
`logging-spring-boot-starter` — это кастомный Spring Boot стартер, добавляющий функциональность логирования API-запросов и ответов на основе аннотаций и Spring AOP. Разработчик сам определяет, какие методы логировать, с помощью аннотаций `@Loggable` и `@MeasureExecutionTime`.

---

## 🔧 Возможности

-  Логирование вызовов методов, помеченных аннотацией `@Loggable`
-  Измерение времени выполнения методов с помощью `@MeasureExecutionTime`
-  Настройка включения/отключения логирования
-  Настройка уровня логирования: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`
-  Поддержка конфигурации через `application.properties` или `application.yml`
-  Валидация значений конфигурации при старте приложения
-  Анализ ошибок конфигурации через `FailureAnalyzer`

---

## 📥 Подключение

Добавьте зависимость в `pom.xml` вашего проекта:

```xml
<dependency>
  <groupId>ru.t1.ismailov</groupId>
  <artifactId>logging-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## ⚙️ Конфигурация

Пример настройки в `application.yml`:

```yaml
api:
  logging-starter:
    enabled: true
    level: debug
```

Поддерживаемые уровни логирования:
- `trace`
- `debug`
- `info` (по умолчанию)
- `warn`
- `error`

---

## 🏷 Использование аннотаций

### `@Loggable`

Применяется для логирования вызова метода и аргументов.

```java
@Loggable
public void processOrder(String orderId) { ... }
```

Можно использовать также на уровне класса.

### `@MeasureExecutionTime`

Измеряет и логирует время выполнения метода.

```java
@MeasureExecutionTime
public void calculateSomethingHeavy() { ... }
```

---

## 📚 Компоненты стартера

- `LoggingAspect` — аспект логирования
- `LoggingProperties` — бин с настройками логирования
- `LoggingAutoConfiguration` — автоматическая конфигурация
- `LoggingEnvPostProcessor` — загрузка default.yml и валидация конфигурации
- `LoggingFailureAnalyzer` — удобное описание ошибок конфигурации
- Аннотации: `@Loggable`, `@MeasureExecutionTime`
- Конфигурация по умолчанию: `src/main/resources/default.yml`

---

## 📄 Конфигурационные файлы

### `META-INF/spring.factories`

```properties
org.springframework.boot.env.EnvironmentPostProcessor=\
  ru.t1.ismailov.logging.init.LoggingEnvPostProcessor

org.springframework.boot.diagnostics.FailureAnalyzer=\
  ru.t1.ismailov.logging.analyzer.LoggingFailureAnalyzer
```

### `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

```properties
ru.t1.ismailov.logging.config.LoggingAutoConfiguration
```

---

## ❗ Примечания

- Логирование активируется только при `api.logging-starter.enabled=true`.
- При передаче неверных значений (например, `api.logging-starter.level=verbose`) стартер выбрасывает `LoggingStartupException`.

---
## Требования

- Java 17+
- Spring Boot 3.1.5+