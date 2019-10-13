package per.rsf.study.javase.jdk.base.rmi;

/**
 * RMI是一种协议，它使一个JVM（Java虚拟机）上的对象能够调用不同JVM中另一个对象上的方法。
 * 可以用这种方法调用其方法的任何对象都必须实现java.rmi.Remote接口。
 *
 * 当调用这样的对象时，其参数将被编组（转换为比特流），并从本地JVM发送到远程组，
 * 在该远程JVM上将解组和使用参数。
 * 该方法终止后，结果将从远程计算机中整理并发送到调用者的虚拟机。
 */
public class RMIDemo {
}
