package per.rsf.study.junit.syn;

import org.junit.jupiter.api.parallel.Execution;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * src/test/resources:junit-platform.properties:
 * junit.jupiter.execution.parallel.enabled = true
 * 同类方法
 * junit.jupiter.execution.parallel.mode.default = concurrent | same_thread
 * 顶级类
 * junit.jupiter.execution.parallel.mode.classes.default = concurrent | same_thread
 * 最大池最小池
 * junit.jupiter.execution.parallel.config.strategy = dynamic | fixed | custom
 *      dynamic(默认)
 *          根据可用处理器/核的数量乘以junit.jupiter.execution.parallel.config.dynamic.factor
 *          配置参数（默认为1）计算所需的并行度。
 *      fixed
 *          使用强制junit.jupiter.execution.parallel.config.fixed.parallelism
 *          配置参数作为所需的并行性。
 *      custom
 *          允许ParallelExecutionConfigurationStrategy
 *          通过强制junit.jupiter.execution.parallel.config.custom.class
 *          配置参数指定自定义实现，以确定所需的配置。
 */
@Execution(CONCURRENT)
public class SynDemo {

}
