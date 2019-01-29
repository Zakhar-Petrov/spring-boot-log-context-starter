package ru.zpetrov.log.context.autoconfigure;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.zpetrov.log.context.Log4j2ContextWrapper;
import ru.zpetrov.log.context.LogContextWrapper;

@Configuration
@ConditionalOnClass({LogContextWrapper.class})
public class LogContextAutoConfiguration {

    @Bean
    @Order(1)
    @ConditionalOnMissingBean
    @ConditionalOnClass(ThreadContext.class)
    public Log4j2ContextInitializer log4j2ContextInitializer() {
        return new Log4j2ContextInitializer(new Log4j2ContextWrapper());
    }

}
