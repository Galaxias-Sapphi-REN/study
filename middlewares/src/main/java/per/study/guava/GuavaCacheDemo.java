package per.study.guava;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author sapphire
 * ConcurrentMap会一直保存所有添加的元素，直到显式地移除。（更好的内存效率）
 * Guava Cache为了限制内存占用，通常都设定为自动回收元素。
 * 在某些场景下，尽管LoadingCache 不回收元素，也会会自动加载缓存。
 * <p>
 * Guava Cache在写操作时，偶尔在读操作时做清理缓存工作。
 */
public class GuavaCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(GuavaCacheDemo.class);

    /**
     * 监听器
     */
    private static RemovalListener<String, List> removalListener = new RemovalListener<String, List>() {
        @Override
        public void onRemoval(RemovalNotification<String, List> removal) {
            logger.info("cache remove : " + removal.getKey() + "|" + removal.getValue() + " for cause : " + removal.getCause());
        }
    };

    private static LoadingCache<String, List> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES) //缓存1分钟内没有被写访问（创建或覆盖），回收
            .expireAfterAccess(1, TimeUnit.MINUTES) //缓存1分钟内没有被读/写访问，回收。
            .removalListener(removalListener)//缓存项被移除时做一些额外操作
            .maximumSize(10) //最多缓存10个对象
            .build(new CacheLoader<String, List>() {
                // 缓存未命中从哪加载，一般从持久层继续加载，加载后自动load进缓存
                @Override
                public List load(String s) throws Exception {
                    logger.info("cache key [" + s + "] miss! new Value from load! ");
                    return new ArrayList();
                }
            });


    private static List getByKey(String key) {
        List result = null;
        try {
            result = cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void cache(String key, List value) {
        cache.put(key, value);
    }

    public static void main(String[] args) {
        int times = 15;
        for (int i = 0; i < times; i++) {
            cache(String.valueOf(i), new ArrayList());
        }
        logger.info("find key [1] : " + getByKey("1"));
        logger.info("find key [14] : " + getByKey("14"));
    }
}
