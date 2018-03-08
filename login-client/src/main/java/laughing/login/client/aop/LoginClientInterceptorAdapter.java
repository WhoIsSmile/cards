package laughing.login.client.aop;

import laughing.login.client.constant.LoginClientConstant;
import laughing.utils.net.HttpClientUtil;
import laughing.utils.net.response.util.RsResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public static final String CALL_BACK = "callBack";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //  获取session
        HttpSession session = request.getSession(true);
        //  获取token
        String token = request.getParameter("token");
        //  如果session不为空
        if (session.getAttribute(LoginClientConstant.LOGIN_CLIENT_USER_SESSION) != null) {
            return true;
        }
        // session 为空 token 为空
        if (StringUtils.isBlank(token)) {
            log.info("------:跳转到login页面！");
            String visitUrl = getVisitUrl(request, null);
            String url = new StringBuffer(ssoAuthUrl).append("?")
                    .append(CALL_BACK).append("=").append(URLEncoder.encode(visitUrl, "UTF-8")).toString();
            response.sendRedirect(url);
            return false;
        }
        // token不为空
        String userInfo = getUserInfo(token);
        if (StringUtils.isNotBlank(userInfo) && RsResultUtil.isSuccess(userInfo)) {
            String resultBody = RsResultUtil.getResultBody(userInfo);
            if (StringUtils.isBlank(resultBody)) {
                log.error("返回为空resultBody");
                throw new Exception();
            }
            session.setAttribute(LoginClientConstant.LOGIN_CLIENT_USER_SESSION, resultBody);
        } else {
            log.error("返回为空userInfo");
//            String visitUrl = getVisitUrl(request, "token");
//            String url = new StringBuffer(ssoAuthUrl).append("?")
//                    .append(CALL_BACK).append("=").append(URLEncoder.encode(visitUrl, "UTF-8")).toString();
//            response.sendRedirect(url);
            throw new Exception();
        }
        return true;
    }


    /**
     * 获取访问的地址
     *
     * @param request
     * @return
     */
    private String getVisitUrl(HttpServletRequest request, String excludeParam) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(request.getScheme())
                .append("://").append(request.getServerName()).append(":").append(request.getServerPort())
                .append(request.getContextPath())
                .append(request.getServletPath());
        if (StringUtils.isBlank(excludeParam)) {
            if (request.getQueryString() != null) {
                urlBuffer.append("?").append(request.getQueryString());
            }
        } else {
            if (request.getQueryString() != null) {
                String params = requetToStr(request, excludeParam);
                if (StringUtils.isNotBlank(params)) {
                    urlBuffer.append("?").append(params);
                }
            }
        }
        return urlBuffer.toString();
    }


    private String requetToStr(HttpServletRequest request, String excludeParam) {
        Map map = request.getParameterMap();
        Set key = map.keySet();
        int i = 0;
        StringBuffer params = new StringBuffer();
        for (Object aaa : key.toArray()) {
            String paraKey = aaa.toString();
            if (!excludeParam.equals(paraKey)) {
                String paraValue = (String) map.get(aaa);
                if (i > 0) {
                    params.append("&");
                }
                params.append(paraKey).append("=").append(paraValue);
                i = i + 1;
            }
        }
        return params.toString();
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
