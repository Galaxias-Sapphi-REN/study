java虚拟机规范
https://docs.oracle.com/javase/specs/jvms/se13/html/index.html
java语言规范
https://docs.oracle.com/javase/specs/jls/se13/html/index.html

jvm有自己的cpu指令集和内存，表现形式为java虚拟机指令集和运行时内存。

与二进制.class文件关联，

    .class = Java虚拟机指令集（字节码 Bytecodes) + 符号表 + 其他辅助信息。

1. Java虚拟机结构

    jvm规范是一种高度抽象行为的描述，而不是具体虚拟机的实现。

    数据类型
        原始类型 Primitive Types
            数值类型 Numeric
                整数类型 Integral : byte short int long char
                浮点数类型 Floating-Point : float double
            布尔类型 Boolean
            returnAddress 表示一条字节码指令的操作码(被java1.7抛弃)
        引用类型 Reference Types
            Class
            Array
            Interface

    在编译期间jvm进行类型检查，减少JVM运行时的工作量，提高效率。