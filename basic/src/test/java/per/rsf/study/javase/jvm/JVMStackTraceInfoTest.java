package per.rsf.study.javase.jvm;

public class JVMStackTraceInfoTest {

    public static void main(String[] args) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            StackTraceElement stackTraceElement = elements[i];
            String className = stackTraceElement.getClassName();
            String moduleName = stackTraceElement.getModuleName();
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            int lineNumber = stackTraceElement.getLineNumber();
            System.out.println(stackTraceElement);
            System.out.println("StackTraceElement[" + i + "]["
                    + fileName + "][" + className + "]["+ moduleName + "]["  + methodName + "][" + lineNumber);
        }
    }
}
