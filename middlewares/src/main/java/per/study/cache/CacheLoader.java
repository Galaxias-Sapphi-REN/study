package per.study.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

public interface CacheLoader {
    /**
     * 获取cache的基本配置
     * @return
     */
    CacheConfiguration config();

    /**
     * 添加缓存
     * @param cache
     */
    void cache(Cache cache);

    /**
     * 从缓存中获取对象
     * @param key
     * @return
     */
    <T> T get(String key);
}
