package laughing.login.controller;

import laughing.login.controller.param.LoginParam;
import laughing.login.controller.param.RegisterParam;
import laughing.login.service.LoginService;
import laughing.utils.constant.GlobalConstant;
import laughing.utils.entity.UserEntity;
import laughing.utils.global.ErrorEnum;
import laughing.utils.response.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
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
     * cookie 的缓存时间
     */
    @Value("${login.cookie.time:2}")
    private int cookieTime;
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
            setCookie(response, GlobalConstant.LOGIN_USER_TOKEN_SESSION, token);
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
     * @return
     */
    @RequestMapping("getUserInfo")
    public RsResult getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        UserEntity userEntity = loginService.getUserByToken(token);
        return new RsResult(ErrorEnum.SUCCESS, userEntity);
    }

    @RequestMapping("auth")
    private String userAuth(HttpServletRequest request, HttpServletResponse response) {
        String callBack = request.getParameter("callBack");
        String token = getCookie(request, GlobalConstant.LOGIN_USER_TOKEN_SESSION);
        if (StringUtils.isBlank(token)) {
            log.info("重定向到登录:callBack {}", callBack);
            return "redirect:" + loginPage + "?callBack=" + callBack;
        }
        log.info("回调 : {} token :{}", callBack, token);
        return "redirect:" + callBack + "?token=" + token;
    }


    /**
     * 设置cookie
     *
     * @param response
     * @param key
     * @param value
     */
    private void setCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        //设置其生命周期
        cookie.setMaxAge(3600 * 24 * 3);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param key
     * @return
     */
    private String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
