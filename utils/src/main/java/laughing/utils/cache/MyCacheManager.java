package laughing.utils.cache;

import laughing.utils.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * cache管理
 *
 * @author laughing
 */
@Service
@Slf4j
public class MyCacheManager {

    @Value("${cache.switch:true}")
    private boolean cacheSwitch;
    @Value("${cache.time:10}")
    private int cacheTime;

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 获取cache key
     *
     * @param cacheHead
     * @param parmas    用于生成cache 的key
     * @return
     */
    public String getCacheKey(String cacheHead, String... parmas) {
        StringBuilder cacheKey = new StringBuilder();
        if (parmas.length > 0) {
            for (String param : parmas) {
                cacheKey.append(param).append(GlobalConstant.ADAPSPLIT);
            }
            String key = cacheKey.toString();
            if (key.lastIndexOf(GlobalConstant.ADAPSPLIT) == key.length() - 1) {
                key = key.substring(0, key.length() - 1);
            }
            key = String.format(cacheHead, key);
            return key;
        }
        return cacheHead;
    }

    /**
     * @param key
     * @return
     */
    public Object getCacheValue(String key) {
        if (cacheSwitch) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }


    /**
     * 根据Key 获取 String
     *
     * @param key
     * @return
     */
    public String getCacheValue2Str(String key) {
        Object value = getCacheValue(key);
        return value == null ? "" : value.toString();
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param times
     */
    public void setCache(String key, Object value, int times) {
        if (cacheSwitch && null != value) {
            redisTemplate.opsForValue().set(key, value, times, TimeUnit.MINUTES);
        }
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void setCache(String key, Object value) {
        if (cacheSwitch && null != value) {
            redisTemplate.opsForValue().set(key, value, cacheTime, TimeUnit.MINUTES);
        }
    }

    /**
     * 永久保存信息
     *
     * @param key
     * @param value
     */
    public void saveInfo(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
