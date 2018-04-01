package laughing.login.service;

import laughing.login.controller.param.LoginParam;
import laughing.login.controller.param.RegisterParam;
import laughing.login.dao.UserDao;
import laughing.login.exception.LoginException;

import laughing.utils.code.HashCode;
import laughing.utils.entity.UserEntity;
import laughing.utils.global.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new LoginException(ErrorEnum.LOGIN_PASSWORD_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("_", "");
        redisUserDaoImpl.cacheToken(token, userName);
        log.info("token : {}  | user : {}", token, userName);
        return token;
    }

    /**
     * 用户注册
     *
     * @param registerParam
     */
    public void userRegister(RegisterParam registerParam) {
        UserEntity userEntity = redisUserDaoImpl.getUserByUserName(registerParam.getUserName());
        if (userEntity != null) {
            throw new LoginException(ErrorEnum.LOGIN_USER_EXIST);
        }
        userEntity = new UserEntity();
        userEntity.setEmail(registerParam.getEmail());
        userEntity.setUserName(registerParam.getUserName());
        userEntity.setPassword(codePassword(registerParam.getPassword()));
        redisUserDaoImpl.saveUserEntity(userEntity);
    }

    public void logout() {

    }


    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    public UserEntity getUserByToken(String token) {
        UserEntity userEntity = redisUserDaoImpl.getUserByToken(token);
        if (userEntity == null) {
            log.error("用户不存在");
            throw new LoginException(ErrorEnum.LOGIN_USER_NOT_EXIST);
        }
        return userEntity;
    }

    private String codePassword(String password) {
        return HashCode.md5Hash(password);
    }

    public boolean checkTokenVaild(String token) {
        String userName = redisUserDaoImpl.getUserNameByToken(token);
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        return true;
    }

}
