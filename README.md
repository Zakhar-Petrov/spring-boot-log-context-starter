# Spring AOP logger diagnostic context wrapper

This project uses [AspectJ](http://www.eclipse.org/aspectj/) to wrap logger diagnostic context managment into AOP layer.

It supports [SpEL](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions) for method arguments.

## Supported loggers:

- [x] log4j2
- [ ] slf4j

## Using with Spring Boot

### log4j2

Maven dependencies:

```xml
        <dependency>
            <groupId>ru.zpetrov</groupId>
            <artifactId>log-context-spring-boot-starter</artifactId>
            <version>${project.version}</version>
        </dependency>
```

Additional log4j2 dependencies:
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
```

```java
    @Log4j2Context
    public void methodWithContext(
            @ContextItem("id") long id,
            @ContextItem(value = "timestamp", expression = "#this.toInstant().toEpochMilli()") OffsetDateTime offsetDateTime) {
        log.info("Hello world!");
    }
```

Useful links:
- [Log4j 2 API Thread Context](https://logging.apache.org/log4j/2.x/manual/thread-context.html)
