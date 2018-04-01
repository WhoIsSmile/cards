package laughing.login.controller;

import laughing.login.controller.param.LoginParam;
import laughing.login.controller.param.RegisterParam;
import laughing.login.service.LoginService;
import laughing.utils.constant.GlobalConstant;
import laughing.utils.entity.UserEntity;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.CookieUtil;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
                           LoginParam loginParams) {
        try {
            String token = loginService.getToken(loginParams);
            CookieUtil.addCookie(response, GlobalConstant.LOGIN_USER_TOKEN_SESSION, token, cookieTime);
            return "redirect:" + loginParams.getCallBack() + "?token=" + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:" + loginPage + "?error=yes";
        }
    }

    /**
     * 用户注册(次逻辑有待优化)
     *
     * @param request
     * @param response
     * @param registerParam
     * @return
     */
    @RequestMapping("register")
    public String register(HttpServletRequest request, HttpServletResponse response,
                           RegisterParam registerParam) {
        try {
            loginService.userRegister(registerParam);
            return "redirect:" + loginPage;
        } catch (Exception e) {
            return "redirect:" + registerPage + "?error=yes";
        }
    }

    /**
     * 获取用户信息通过Token
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    public RsResult getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        UserEntity userEntity = loginService.getUserByToken(token);
        return new RsResult(ErrorEnum.SUCCESS, userEntity);
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("auth")
    public String userAuth(HttpServletRequest request, HttpServletResponse response) {
        String callBack = request.getParameter(GlobalConstant.LOGIN_CALL_BACK);
        String token = CookieUtil.getCookie(request, GlobalConstant.LOGIN_USER_TOKEN_SESSION);
        if (StringUtils.isBlank(token) || !loginService.checkTokenVaild(token)) {
            if (StringUtils.isNotBlank(token)) {
                CookieUtil.removeCookie(response, GlobalConstant.LOGIN_USER_TOKEN_SESSION);
            }
            log.info("重定向到登录 {} : callBack {}", loginPage, callBack);
            return "redirect:" + loginPage + "?callBack=" + callBack;
        }
        log.info("回调 : {} token :{}", callBack, token);
        return "redirect:" + callBack + "?token=" + token;
    }

    /**
     * 登出（带实现）
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public RsResult logout(HttpServletRequest request, HttpServletResponse response) {
        return new RsResult();
    }
}
