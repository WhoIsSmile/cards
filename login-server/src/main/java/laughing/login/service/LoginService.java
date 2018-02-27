package laughing.login.service;

import laughing.login.controller.param.LoginParam;
import org.springframework.stereotype.Service;

/**
 * @author laughing
 * @date 2018-02-27 08:50:58
 */
@Service
public class LoginService {

    /**
     * 用户登录
     *
     * @param loginParam
     */
    public void login(LoginParam loginParam) {
        String userName = loginParam.getUserName();




    }

    public void register() {

    }

    public void logout() {

    }
}
