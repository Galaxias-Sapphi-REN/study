package per.rsf.study.junit;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import per.rsf.study.junit.anno.Fast;
import per.rsf.study.junit.anno.FastTest;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("A special test case")
public class HelloTests {
    /**
     * https://junit.org/junit5/docs/current/user-guide/
     * <p>
     * JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
     * (jdk8+)
     * Platform :
     * 1. launching testing frameworks on the JVM.
     * 2. TestEngine API on the platform.
     * 3. Console Launcher(命令行启动平台入口).
     * 4. JUnit4 based Runner.
     * 5. support for IDE and build tools.
     * JUnit Jupiter: * JUnit Jupiter 聚合器依赖 api engine params 简化依赖管理
     * 1. programming model + extension model -> jUnit5
     * 2. TestEngine for Jupiter
     * JUnit Vintage: 老式JUnit测试引擎 junit-vintage-engine
     * 1. TestEngine
     * <p>
     * 注解
     *
     * @Test 表示方法是测试方法。
     * @ParameterizedTest 表示方法是参数化测试。
     * @RepeatedTest 表示方法是重复测试的测试模板。
     * @TestFactory 表示方法是动态测试的测试工厂。
     * @TestTemplate 表示方法是测试用例的模板。
     * @TestMethodOrder 执行顺序
     * @TestInstance 为测试类配置测试实例生命周期。继承。
     * @DisplayName 声明类、方法的自定义显示名称。
     * @DisplayNameGeneration 声明类的自定义显示名称生成器。继承。
     * <p>
     * 操作@Test，@RepeatedTest，@ParameterizedTest，或@TestFactory方法
     * @BeforeEach 每个执行前，static
     * @AfterEach 每个执行后，static
     * @BeforeAll 所有执行前
     * @AfterAll 所有执行后
     * @Nested 表示类是一个非静态的嵌套测试类。@BeforeAll和@AfterAll方法不能直接在使用@Nested测试类除非“每级” 测试实例的生命周期被使用。
     * @Tag 过滤测试的标签(类别)
     * @Disabled 禁用类、方法(类似@Ignore)
     * @Timeout 执行超间不处理
     * @ExtendWith 注册扩展
     * @RegisterExtension 通过字段以编程方式注册扩展
     * @TempDir 用于通过生命周期方法或测试方法中的字段注入或参数注入来提供临时目录
     */

    private static int PARAM = 0;

    @Fast
    @Test
    void myFastTest1() {
        Assert.assertTrue(true);
    }

    @FastTest
    void myFastTest2() {
        Assert.assertTrue(true);
    }

    @BeforeAll
    static void initAll() {
        Assert.assertTrue(PARAM == 0);
    }

    @BeforeEach
    void init() {
        PARAM ++;
        System.out.println("before each param is : " + PARAM);
    }

    @Test
    void succeedingTest() {
    }

    @Test
    @DisplayName("╯°□°）╯")
    void testWithDisplayNameContainingSpecialCharacters() {
    }

    @Test
    @DisplayName("😱")
    void testWithDisplayNameContainingEmoji() {
    }


    @Test
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @Test
    void abortedTest() {
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after each param is : " + PARAM);
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("after all param is : " + PARAM);
    }
}
