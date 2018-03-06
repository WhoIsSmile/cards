package laughing.login.client.aop;

import laughing.login.client.constant.LoginClientConstant;
import laughing.utils.constant.GlobalConstant;
import laughing.utils.http.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截所有请求
 *
 * @author laughing
 * @date 2018-03-04 13:43:36
 */
@Slf4j
public class LoginClientInterceptorAdapter extends HandlerInterceptorAdapter {


    /**
     * 单点登录跳转地址
     */
    private String ssoAuthUrl;

    private String ssoUserInfoUrl;

    public void setSsoAuthUrl(String ssoAuthUrl) {
        this.ssoAuthUrl = ssoAuthUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取session
        HttpSession session = request.getSession(true);
        String token = request.getParameter("token");
        if (session.getAttribute(LoginClientConstant.LOGIN_CLIENT_USER_SESSION) != null) {
            return true;
        }
        if (StringUtils.isBlank(token)) {
            log.info("------:跳转到login页面！");
            String visitUrl = getVisitUrl(request);
            String url = new StringBuffer(ssoAuthUrl).append("?callBack=")
                    .append(URLEncoder.encode(visitUrl, "UTF-8")).toString();
            response.sendRedirect(url);
            return false;
        }
        //判断用户ID是否存在，不存在就跳转到登录界面
        if (session.getAttribute(LoginClientConstant.LOGIN_CLIENT_USER_SESSION) == null) {
            String userInfo = getUserInfo(token);
            if (StringUtils.isNotBlank(userInfo)) {
                session.setAttribute(LoginClientConstant.LOGIN_CLIENT_USER_SESSION, userInfo);
            } else {

            }
            return false;
        }
        return true;
    }


    /**
     * 获取访问的地址
     *
     * @param request
     * @return
     */
    private String getVisitUrl(HttpServletRequest request) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(request.getScheme())
                .append("://").append(request.getServerName()).append(":").append(request.getServerPort())
                .append(request.getContextPath())
                .append(request.getServletPath());
        if (request.getQueryString() != null) {
            urlBuffer.append("?").append(request.getQueryString());
        }
        return urlBuffer.toString();
    }

    public void setSsoUserInfoUrl(String ssoUserInfoUrl) {
        this.ssoUserInfoUrl = ssoUserInfoUrl;
    }

    private String getUserInfo(String token) throws Exception {
        Map<String, String> params = new HashMap<>(1);
        params.put("token", token);
        return HttpClientUtil.doPost(ssoUserInfoUrl, params, "UTF-8");
    }

}
