package ru.zpetrov.log.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.zpetrov.log.context.annotations.ContextItem;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ContextBuilderTest {

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{
                TestParameter.forMethod(new Object() {
                    public void testDataMethod_stringContextArgument(@ContextItem("stringTestKey") String arg) {}
                })
                        .withArgument("test")
                        .withExpectedEntry("stringTestKey", "test"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_longContextArgument(@ContextItem("longTestKey") long arg) {}
                })
                        .withArgument(123L)
                        .withExpectedEntry("longTestKey", "123"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_twoContextArguments(
                            @ContextItem("stringTestKey") String arg0,
                            @ContextItem("longTestKey") long arg1) {}
                })
                        .withArgument("test")
                        .withArgument(123L)
                        .withExpectedEntry("stringTestKey", "test")
                        .withExpectedEntry("longTestKey", "123"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_onlySecondContextArgument(
                            String arg0,
                            @ContextItem("longTestKey") long arg1) {}
                })
                        .withArgument("test")
                        .withArgument(123L)
                        .withExpectedEntry("longTestKey", "123"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_onlyFirstContextArgument(
                            @ContextItem("stringTestKey") String arg0,
                            long arg1) {}
                })
                        .withArgument("test")
                        .withArgument(123L)
                        .withExpectedEntry("stringTestKey", "test"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_noAnyContextArguments(String arg0, long arg1) {}
                })
                        .withArgument("test")
                        .withArgument(123L)
                        .withNoExpectedEntry(),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_noAnyArguments() {}
                })
                        .withNoArgument()
                        .withNoExpectedEntry(),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_contextArgumentWithExpression(@ContextItem(value = "stringTestKey", expression = "#this.length()") String arg) {}
                })
                        .withArgument("test")
                        .withExpectedEntry("stringTestKey", "4"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_contextArgumentWithNullResultExpression(@ContextItem(value = "supplierTestKey", expression = "#this.get()") Supplier<Object> arg) {}
                })
                        .withArgument((Supplier) () -> null)
                        .withExpectedEntry("supplierTestKey", "<null>"),

                TestParameter.forMethod(new Object() {
                    public void testDataMethod_nullContextArgument(@ContextItem(value = "supplierTestKey") Object arg) {}
                })
                        .withArgument(null)
                        .withExpectedEntry("supplierTestKey", "<null>"),
                });
    }

    @Parameterized.Parameter
    public TestParameter testParameter;

    @Test
    public void givenContextBuilderWithMethodAndArguments_whenBuild_thenHasExpectedContext() {
        Map<String, String> context = testParameter.getContextBuilder().build();
        assertThat(context.size(), is(testParameter.getExpectedContextEntries().size()));
        for (SimpleEntry entry : testParameter.getExpectedContextEntries()) {
            assertThat(context, hasEntry(entry.getKey(), entry.getValue()));
        }
    }

    private static class TestParameter {
        private final Method method;
        private final List<Object> arguments = new LinkedList<>();
        private final List<SimpleEntry<String, String>> expectedContextEntries = new LinkedList<>();

        static TestParameter forMethod(Object o) {
            return new TestParameter(Arrays.stream(o.getClass().getMethods())
                    .filter(m -> m.getName().startsWith("testDataMethod"))
                    .findAny().orElseThrow(() -> new RuntimeException("No any testDataMethod")));
        }

        private TestParameter(Method method) {
            this.method = method;
        }

        TestParameter withArgument(Object argument) {
            arguments.add(argument);
            return this;
        }

        TestParameter withNoArgument() {
            return this;
        }

        TestParameter withExpectedEntry(String key, String value) {
            expectedContextEntries.add(new SimpleEntry<>(key, value));
            return this;
        }

        TestParameter withNoExpectedEntry() {
            return this;
        }

        ContextBuilder getContextBuilder() {
            return new ContextBuilder(method, arguments.toArray());
        }

        List<SimpleEntry<String, String>> getExpectedContextEntries() {
            return expectedContextEntries;
        }
    }

}