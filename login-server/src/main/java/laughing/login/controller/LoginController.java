package laughing.login.controller;

import laughing.login.controller.param.LoginParam;
import laughing.login.controller.param.RegisterParam;
import laughing.login.service.LoginService;
import laughing.utils.constant.GlobalConstant;
import laughing.utils.response.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author laughing
 * @date 2018-02-26 17:54:37
 */
@Controller
@RequestMapping("u")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录页面
     */
    @Value("${login.interceptor.login.page:/index.html}")
    private String loginPage;
    /**
     * 注册页面
     */
    @Value("${login.interceptor.register.page:/register.html}")
    private String registerPage;

    /**
     * 登录
     *
     * @param request
     * @param response
     * @param loginParams
     * @return
     */
    @RequestMapping(value = "getToken")
    public String getToken(HttpServletRequest request, HttpServletResponse response,
                           LoginParam loginParams) throws Exception {
        try {
            String token = loginService.getToken(loginParams);
            HttpSession session = request.getSession(true);
            session.setAttribute(GlobalConstant.LOGIN_USER_TOKEN_SESSION, token);
            return "redirect:" + loginParams.getCallBack() + "?token=" + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:" + loginPage + "?error=yes";
        }
    }

    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @param registerParam
     * @return
     */
    @RequestMapping("register")
    public String register(HttpServletRequest request, HttpServletResponse response,
                           RegisterParam registerParam) {
        log.info(" 注册 ：{}", registerParam.toString());
        try {
            loginService.userRegister(registerParam);
            return "redirect:" + loginPage;
        } catch (Exception e) {
            return "redirect:" + registerPage + "?error=yes";
        }
//        return new RsResult();
    }

    /**
     * 获取用户信息通过Token
     *
     * @param request
     * @param response
     * @param registerParam
     * @return
     */
    @RequestMapping("getUserInfo")
    public RsResult getUserInfo(HttpServletRequest request, HttpServletResponse response,
                                RegisterParam registerParam) {

        return new RsResult();
    }

    @RequestMapping("login")
    private String userLogin(HttpServletRequest request, HttpServletResponse response) {
        String callBack = request.getParameter("callBack");
        HttpSession session = request.getSession(true);
        Object token = session.getAttribute(GlobalConstant.LOGIN_USER_TOKEN_SESSION);
        if (token == null) {
            return "redirect:" + loginPage + "?callBack=" + callBack;
        }
        return "redirect:" + callBack + "?token=" + token;
    }


}
