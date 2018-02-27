package laughing.login.dao.impl;

import com.alibaba.fastjson.JSON;
import laughing.login.dao.UserDao;
import laughing.utils.cache.CacheManager;
import laughing.utils.cache.GlobelCacheKey;
import laughing.utils.entity.UserEntity;
import laughing.utils.tool.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author laughing
 * @date 2018-02-27 21:12:29
 */
@Service
public class RedisUserDaoImpl implements UserDao {


    @Autowired
    private CacheManager cacheManager;

    IdWorker idWorker = new IdWorker(15, 15);

    /**
     * 根据用户名称获取帐号
     *
     * @param userName
     * @return
     */
    @Override
    public UserEntity getUserByUserName(String userName) {
        String cacheKey = cacheManager.getCacheKey(GlobelCacheKey.USER_NAME_KEY, userName);
        String value = cacheManager.getCacheValue2Str(cacheKey);
        UserEntity user = JSON.parseObject(value, UserEntity.class);
        return user;
    }

    @Override
    public UserEntity saveUserEntity(UserEntity userEntity) {
        String cacheKey = cacheManager.getCacheKey(GlobelCacheKey.USER_NAME_KEY, userEntity.getUserName());
        userEntity.setUserId(idWorker.nextId() + "");
        String json = JSON.toJSON(userEntity).toString();
        cacheManager.saveInfo(cacheKey, json);
        return userEntity;
    }

    @Override
    public boolean checkUserExist(String userName) {
        return getUserByUserName(userName) == null ? false : true;
    }


}
