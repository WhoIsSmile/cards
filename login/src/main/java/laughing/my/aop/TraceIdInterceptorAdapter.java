package laughing.my.aop;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author huang.xiaolong
 * @create 2019-09-17 11:14:32
 * @desc
 **/
public class TraceIdInterceptorAdapter extends HandlerInterceptorAdapter {
    /**
     * 跟踪ID
     */
    private final static String TRANCE_ID = "traceId";
    /**
     * 请求开始时间
     */
    private final static String START_TIME = "startTime";
    //2017-12-13 14:36:27,192
    private final static String FORMAT_DATE_STYLE = "yyyy-MM-dd HH:mm:ss,SSS";
    private final String SEPARATOR = "->";
}
