package per.rsf.study.junit.order;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 默认使用确定性但故意不明显的算法对测试方法进行排序，
 * 确保测试套件的后续运行以相同的顺序执行测试方法，从而允许可重复的构建。
 *
 * 编写集成测试或功能测试时，测试顺序是重要，
 * 尤其是与结合使用 @TestInstance(Lifecycle.PER_CLASS)
 *
 * 使用@TestInstance(Lifecycle.PER_CLASS)模式时，
 * 每个测试类将创建一个新的测试实例。
 *
 * 默认@TestInstance(Lifecycle.PER_METHOD)模式
 *
 * 配置方法：
 * 1. @TestInstance(Lifecycle.PER_CLASS)
 * 2. src/test/resources:junit-platform.properties:
 *      junit.jupiter.testinstance.lifecycle.default=per_class
 * 3. 启动jvm
 *      -Djunit.jupiter.testinstance.lifecycle.default=per_class
 */
@TestMethodOrder(OrderAnnotation.class)
class OrderedTestsDemo {

    @Test
    @Order(1)
    void nullValues() {
        // perform assertions against null values
    }

    @Test
    @Order(2)
    void emptyValues() {
        // perform assertions against empty values
    }

    @Test
    @Order(3)
    void validValues() {
        // perform assertions against valid values
    }

}