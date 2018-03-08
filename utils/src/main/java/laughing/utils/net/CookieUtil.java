package laughing.utils.net;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 *
 * @author laughing
 * @date 2018-03-08 14:20:18
 */
public class CookieUtil {

    /**
     * 添加cookie
     *
     * @param response
     * @param key
     * @param value
     * @param minute
     */
    public static void addCookie(HttpServletResponse response, String key, String value, int minute) {
        setCookie(response, key, value, minute);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param key
     * @param value
     */
    private static void setCookie(HttpServletResponse response, String key, String value, int minute) {
        Cookie cookie = new Cookie(key, value);
        //设置其生命周期
        cookie.setMaxAge(60 * minute);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
