package laughing.login.aop;

import laughing.utils.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截所有请求
 *
 * @author laughing
 * @date 2018-03-04 13:43:36
 */
@Slf4j
public class LoginInterceptorAdapter extends HandlerInterceptorAdapter {


    /**
     * 跳转地址
     */
    private String loginHtml = "";

    public void setLoginHtml(String loginHtml) {
        this.loginHtml = loginHtml;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取session
        HttpSession session = request.getSession(true);
        //判断用户ID是否存在，不存在就跳转到登录界面
        if (session.getAttribute(GlobalConstant.LOGIN_USER_TOKEN_SESSION) == null) {
            log.info("------:跳转到login页面！");
            response.sendRedirect(request.getContextPath() + loginHtml);
            return false;
        }
        return true;

    }
}
