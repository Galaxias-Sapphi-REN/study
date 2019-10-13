package per.rsf.study.junit.assumptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import per.rsf.study.idea.junit.util.Calculator;

/**
 * org.junit.jupiter.api.Assumptions
 * 当类中有多个测试方法时，其余假设失败，只要一个假设成功，测试类显示测试成功。
 * 假设方法适用于：在不影响测试是否成功的结果的情况下根据不同情况执行相关代码。
 *
 * 实现原理就是假设条件成立才会执行方法。
 * if(condition){() -> {//这里才会throw TestAbortedException 而不是 Error}}
 */
class AssumptionsDemo {

    private final Calculator calculator = new Calculator();

    @BeforeAll
    static void init(){
        System.out.println(System.getenv("ENV"));
    }
    @Test
    void testOnlyOnCiServer() {
        assumeTrue("CI".equals(System.getenv("ENV")));
        // remainder of test
    }

    @Test
    void testOnlyOnDeveloperWorkstation() {
        assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
        // remainder of test
    }

    @Test
    void testInAllEnvironments() {
        assumingThat("CI".equals(System.getenv("ENV")),
                () -> {
                    // perform these assertions only on the CI server
                    assertEquals(2, calculator.divide(4, 2));
                });

        // perform these assertions in all environments
        assertEquals(42, calculator.multiply(6, 7));
    }

}