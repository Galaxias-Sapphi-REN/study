package per.rsf.study.junit.assertions.support;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import org.junit.jupiter.api.Test;
import per.rsf.study.idea.junit.util.Calculator;

/**
 * JUnit Jupiter测试中使用Hamcrest 的支持
 * 鼓励开发人员使用对第三方断言库提供的匹配器的内置支持。
 * 例如AssertJ，Hamcrest，Truth等
 */
class HamcrestAssertionsDemo {

    private final Calculator calculator = new Calculator();

    @Test
    void assertWithHamcrestMatcher() {
        assertThat(calculator.sub(4, 1), is(equalTo(3)));
    }

}