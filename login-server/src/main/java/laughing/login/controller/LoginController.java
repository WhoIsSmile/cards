package laughing.login.controller;

import laughing.login.controller.param.LoginParam;
import laughing.utils.response.RsResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author laughing
 * @date 2018-02-26 17:54:37
 */
@RestController
@RequestMapping("u")
public class LoginController {

    @RequestMapping("login")
    public RsResult login(HttpServletRequest request, HttpServletResponse response,
                          LoginParam loginParams) {
        return new RsResult();
    }

    private void validateParam(LoginParam param) {

    }
}
