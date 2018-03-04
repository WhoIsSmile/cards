package laughing.cards.service;

import laughing.cards.cache.CacheManager;
import laughing.cards.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author laughing
 * @date 2018-02-24 13:34:35
 */
@Service
@Slf4j
public class RegisterService extends CacheManager {
//    @Autowired
//    private Message message;

    @Value("${verification.code.cache.time:10}")
    private int verificationCodeCacheTime;

    /**
     * 发送验证码
     *
     * @param toUser
     */
//    public boolean sendVerificationCode(String toUser) {
//        // 验证码
//        String verificationCode = StringTool.getVerificationCode();
//        log.info("User :{}  verification code :{} ", toUser, verificationCode);
//        boolean result = message.seanMessage(toUser, verificationCode);
//        if (result) {
//            String key = getCacheKey(RedisConstant.USER_VERIFICATION_CODE, toUser);
//            setCache(key, verificationCode, verificationCodeCacheTime);
//        }
//        return result;
//    }

    /**
     * 检查验证码
     *
     * @param toUser
     * @param verificationCode
     * @return
     */
    public boolean checkVerificationCode(String toUser, String verificationCode) {
        String key = getCacheKey(RedisConstant.USER_VERIFICATION_CODE, toUser);
        Object code = getCacheValue(key);
        String cacheValue = code == null ? "" : code.toString();
        if (cacheValue.equalsIgnoreCase(verificationCode)) {
            return true;
        }
        return false;
    }
}
