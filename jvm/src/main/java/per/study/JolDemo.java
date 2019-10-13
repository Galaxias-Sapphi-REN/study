package per.study;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sapphire
 */
public class JolDemo {
    private static Object generate() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

    private static void print(String message) {
        System.out.println(message);
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        Object obj = generate();
        //查看对象内部信息
        print(ClassLayout.parseInstance(obj).toPrintable());
        /**
         * 对象头 Mark Word + Class Metadata Address = 12 bytes
         * OFFSET  SIZE  TYPE DESCRIPTION  VALUE
         * 0       4     (object header)   05 00 00 00 (00000101 00000000 00000000 00000000) (5)
         * 4       4     (object header)   00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         * 8       4     (object header)   ff 48 04 00 (11111111 01001000 00000100 00000000) (280831)
         * 实例数据
         * 20      4     int HashMap.size  13
         * ...
         * 对齐数据(按 8倍数 bytes 补齐)
         * 44      4     Space losses
         */
        //查看对象外部信息
        print(GraphLayout.parseInstance(obj).toPrintable());
        //获取对象总大小
        print("size : " + GraphLayout.parseInstance(obj).totalSize());
    }
}
