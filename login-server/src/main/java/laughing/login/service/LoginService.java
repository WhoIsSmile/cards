package laughing.login.service;

import laughing.login.controller.param.LoginParam;
import laughing.login.controller.param.RegisterParam;
import laughing.login.dao.UserDao;
import laughing.login.exception.LoginException;

import laughing.utils.cache.MyCacheManager;
import laughing.utils.code.HashCode;
import laughing.utils.entity.UserEntity;
import laughing.utils.global.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author laughing
 * @date 2018-02-27 08:50:58
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserDao redisUserDaoImpl;
    /**
     *
     */
    @Autowired
    private MyCacheManager myCacheManager;
    @Value("${login.token.cache:12}")
    private  int time;

    /**
     * 用户登录 获取token
     *
     * @param loginParam
     */
    public String getToken(LoginParam loginParam) {
        String userName = loginParam.getUserName();
        UserEntity userEntity = redisUserDaoImpl.getUserByUserName(userName);
        String password = userEntity.getPassword();
        String hashPassword = HashCode.md5Hash(loginParam.getPassword());
        if (!hashPassword.equals(password)) {
            log.error("密码错误 {}", loginParam.toString());
            throw new LoginException(ErrorEnum.PASSWORD_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("_", "");
        myCacheManager.setCache(token, userName, 60 * time);
        log.info("token : {}  | user : {}", token, userName);
        return token;
    }

    /**
     * 用户注册
     *
     * @param registerParam
     */
    public void userRegister(RegisterParam registerParam) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerParam.getEmail());
        userEntity.setUserName(registerParam.getUserName());
        userEntity.setPassword(codePassword(registerParam.getPassword()));
        redisUserDaoImpl.saveUserEntity(userEntity);
    }

    public void logout() {

    }


    private String codePassword(String password) {
        return HashCode.md5Hash(password);
    }
}
