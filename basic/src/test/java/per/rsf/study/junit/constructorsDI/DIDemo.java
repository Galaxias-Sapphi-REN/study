package per.rsf.study.junit.constructorsDI;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 作为JUnit Jupiter的主要更改之一，允许测试构造函数和方法都具有参数。
 * 这提供了更大的灵活性，并为构造函数和方法启用了依赖注入。
 *
 * ParameterResolver为希望在运行时动态解析参数的测试扩展定义API 。如果测试类的构造函数，测试方法或 生命周期方法（请参见测试类和方法）接受参数，则必须在运行时由register解析该参数ParameterResolver。
 *
 * 当前有三个自动注册的内置解析器。
 *
 *      TestInfoParameterResolver：如果构造函数或方法参数的类型为 TestInfo
 *          TestInfoParameterResolver 则将提供TestInfo 与当前容器或test对应的实例作为该参数的值。
 *          所述 TestInfo然后可以被用于检索有关当前容器或测试信息，如显示名称，测试类，测试方法，以及相关的标签。
 *          显示名称可以是技术名称（例如测试类或测试方法的名称），也可以是通过进行配置的自定义名称@DisplayName。
 *
 *      RepetitionInfoParameterResolver：
 *          如果在一个方法参数 @RepeatedTest， @BeforeEach或@AfterEach方法的类型是RepetitionInfo时，
 *          RepetitionInfoParameterResolver 将提供的一个实例RepetitionInfo。
 *          RepetitionInfo然后可以用于检索有关当前重复和相应重复总数的信息@RepeatedTest。
 *          但是请注意，它RepetitionInfoParameterResolver没有在的上下文之外注册@RepeatedTest。
 *
 *      TestReporterParameterResolver：
 *          如果构造函数或方法参数的类型为 TestReporter，
 *          TestReporterParameterResolver 则将提供的实例 TestReporter。
 *          该TestReporter可用于发布关于当前试运行的附加数据。
 *          数据可以通过中的reportingEntryPublished()方法使用TestExecutionListener，
 *          从而可以在IDE中查看或包含在报告中。
 */
@DisplayName("DIDemo")
public class DIDemo {

    DIDemo(TestInfo testInfo) {
        assertEquals("DIDemo", testInfo.getDisplayName());
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.println(displayName);
    }

    @Test
    @DisplayName("testInfo")
    @Tag("testInfo-tag")
    void test1(TestInfo testInfo) {
        assertEquals("testInfo", testInfo.getDisplayName());
        assertTrue(testInfo.getTags().contains("testInfo-tag"));
    }

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");
        testReporter.publishEntry(values);
    }
}
