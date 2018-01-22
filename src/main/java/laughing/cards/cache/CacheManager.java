package laughing.cards.cache;

import laughing.cards.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheManager {

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
                cacheKey.append(param).append(CommonConstant.ADAPSPLIT);
            }
            String key = cacheKey.toString();
            if (key.lastIndexOf(CommonConstant.ADAPSPLIT) == key.length() - 1) {
                key = key.substring(0, key.length() - 1);
            }
            key = String.format(cacheHead, key);
            return key;
        }
        return cacheHead;
    }
}
