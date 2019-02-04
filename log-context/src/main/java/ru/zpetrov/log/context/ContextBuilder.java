package ru.zpetrov.log.context;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.zpetrov.log.context.annotations.ContextItem;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

class ContextBuilder {

    private final Method method;
    private final Object[] arguments;

    ContextBuilder(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    Map<String, String> build() {
        Map<String, String> contextMap = new HashMap<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            Parameter parameter = method.getParameters()[i];
            if (!parameter.isAnnotationPresent(ContextItem.class)) {
                continue;
            }
            Object argumentValue = arguments[i];
            String contextFieldName = getContextFieldName(parameter);
            String contextFieldValue = getContextFieldValue(parameter, argumentValue);
            contextMap.put(contextFieldName, contextFieldValue);
        }
        return contextMap;
    }

    private String getContextFieldValue(Parameter parameter, Object argumentValue) {
        String expression = parameter.getAnnotation(ContextItem.class).expression();
        if (!expression.isEmpty()) {
            argumentValue = invokeExpression(argumentValue, expression);
        }
        return argumentValue == null ? "<null>" : argumentValue.toString();
    }

    private Object invokeExpression(Object argumentValue, String expression) {
        EvaluationContext context = new StandardEvaluationContext(argumentValue);
        Expression exp = new SpelExpressionParser().parseExpression(expression);
        return exp.getValue(context);
    }

    private String getContextFieldName(Parameter parameter) {
        String contextFieldName = parameter.getAnnotation(ContextItem.class).value();
        if (contextFieldName.isEmpty()) {
            contextFieldName = parameter.getName();
        }
        return contextFieldName;
    }

}
