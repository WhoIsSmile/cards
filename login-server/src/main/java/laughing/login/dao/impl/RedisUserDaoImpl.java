package laughing.login.dao.impl;

import com.alibaba.fastjson.JSON;
import laughing.login.dao.UserDao;
import laughing.login.exception.LoginException;
import laughing.utils.cache.MyCacheManager;
import laughing.utils.cache.GlobalCacheKey;
import laughing.utils.entity.UserEntity;
import laughing.utils.global.ErrorEnum;
import laughing.utils.tool.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author laughing
 * @date 2018-02-27 21:12:29
 */
@Service
@Slf4j
public class RedisUserDaoImpl implements UserDao {

    @Value("${login.cookie.cache.time:3}")
    private int cacheTime;

    @Autowired
    private MyCacheManager myCacheManager;

    IdWorker idWorker = new IdWorker(15, 15);

    /**
     * 根据用户名称获取帐号
     *
     * @param userName
     * @return
     */
    @Override
    public UserEntity getUserByUserName(String userName) {
        String cacheKey = myCacheManager.getCacheKey(GlobalCacheKey.USER_NAME_KEY, userName);
        String value = myCacheManager.getCacheValue2Str(cacheKey);
        UserEntity user = JSON.parseObject(value, UserEntity.class);
        return user;
    }

    @Override
    public UserEntity saveUserEntity(UserEntity userEntity) {
        String cacheKey = myCacheManager.getCacheKey(GlobalCacheKey.USER_NAME_KEY, userEntity.getUserName());
        userEntity.setUserId(idWorker.nextId() + "");
        String json = JSON.toJSON(userEntity).toString();
        myCacheManager.saveInfo(cacheKey, json);
        return userEntity;
    }

    @Override
    public boolean checkUserExist(String userName) {
        return getUserByUserName(userName) == null ? false : true;
    }

    @Override
    public void cacheToken(String token, String userName) {
        String cacheKey = myCacheManager.getCacheKey(GlobalCacheKey.USER_NAME_KEY, token);
        myCacheManager.setCache(cacheKey, userName, 60 * cacheTime);
    }

    @Override
    public UserEntity getUserByToken(String token) {

        String userName = this.getUserNameByToken(token);
        if (StringUtils.isBlank(userName)) {
            log.error("token 过期 : {}", token);
            throw new LoginException(ErrorEnum.LOGIN_TOKEN_EXPIRE);
        }
        return getUserByUserName(userName);
    }

    @Override
    public String getUserNameByToken(String token) {
        String cacheKey = myCacheManager.getCacheKey(GlobalCacheKey.USER_NAME_KEY, token);
        String userName = myCacheManager.getCacheValue2Str(cacheKey);
        return userName;
    }

}
