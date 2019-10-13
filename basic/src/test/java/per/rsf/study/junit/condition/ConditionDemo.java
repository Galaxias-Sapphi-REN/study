package per.rsf.study.junit.condition;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.condition.JRE.JAVA_13;
import static org.junit.jupiter.api.condition.OS.*;

/**
 * 测试方法评估ExecutionCondition执行条件，
 * 注册多个扩展名时，一旦条件之一返回disable，便会禁用容器或测试。
 *
 * @Enabled...
 * @Disabled... 操作系统、jre、系统属性、环境变量、脚本
 */
public class ConditionDemo {
    @Test
    @EnabledOnOs(MAC)
    void onlyOnMacOs() {
        System.out.println("mac");
    }

    @TestOnMac
    void testOnMac() {
        System.out.println("mac");
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(MAC)
    @interface TestOnMac {
    }

    @Test
    /**
     * System#getenv
     */
    @DisabledIfEnvironmentVariable(named = "1", matches = "1")
    /**
     * System#getProperty
     */
    @DisabledIfSystemProperty(named = "1", matches = "1")
    /**
     * Method versionMethod = Runtime.class.getMethod("version");
     * Object version = ReflectionUtils.invokeMethod(versionMethod, null);
     * Method majorMethod = version.getClass().getMethod("major");
     * int major = (int) ReflectionUtils.invokeMethod(majorMethod, version);
     */
    @DisabledOnJre(JAVA_13)
    /**
     * System.getProperty("os.name");
     */
    @DisabledOnOs({WINDOWS})
    @EnabledOnOs({LINUX, MAC})
    void onLinuxOrMac() {
        System.out.println("mac");
    }

    @Test // Static JavaScript expression.
    @EnabledIf("2 * 3 == 6")
    void willBeExecuted() {
        System.out.println("2 * 3 = 6");
    }

    @RepeatedTest(10) // Dynamic JavaScript expression.
    @DisabledIf("Math.random() < 0.314159")
    void mightNotBeExecuted() {
        System.out.println("pi!");
    }

    @Test // Regular expression testing bound system property.
    @DisabledIf("/32/.test(systemProperty.get('os.arch'))")
    void disabledOn32BitArchitectures() {
        assertFalse(System.getProperty("os.arch").contains("32"));
    }

    @Test
    @EnabledIf("'CI' == systemEnvironment.get('ENV')")
    void onlyOnCiServer() {
        assertTrue("CI".equals(System.getenv("ENV")));
    }

    @Test // Multi-line script, custom engine name and custom reason.
    @EnabledIf(value = {
            "load('nashorn:mozilla_compat.js')",
            "importPackage(java.time)",
            "",
            "var today = LocalDate.now()",
            "var tomorrow = today.plusDays(1)",
            "tomorrow.isAfter(today)"
    },
            engine = "nashorn",
            reason = "Self-fulfilling: {result}")
    void theDayAfterTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        assertTrue(tomorrow.isAfter(today));
    }
}
