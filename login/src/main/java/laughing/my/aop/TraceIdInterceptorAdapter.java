package laughing.my.aop;

import laughing.my.constant.CommonConstants;
import laughing.my.utils.sys.IPUtil;
import laughing.my.utils.sys.SystemTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author huang.xiaolong
 * @create 2019-09-17 11:14:32
 * @desc 全局Id生成
 **/
@Slf4j
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
    /**
     * 分隔符
     */
    private final String SEPARATOR = "-";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        // 使用UUID 生成全局日志追踪ID
        String transID = RandomStringUtils.randomAlphanumeric(10);
        MDC.put(CommonConstants.TRANS_ID, transID);
        MDC.put(CommonConstants.TRACE_ID, getTraceId(request, transID));

        log.info("request:[{}]", getAllRequestParamsInfo(request));
        request.setAttribute(CommonConstants.REQUEST_START_TIME_KEY, startTime);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
// 打印时间
        //        try {
//            StatisticsLog.responseTimeLog(request, response, printResponseBody);
//        } catch (Exception e) {
//            log.error("error print response body ：{}", e.getMessage());
//        }
        // 清除MDC
        MDC.remove(CommonConstants.TRACE_ID);
//        MDC.remove(START_TIME);
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 获取所有的参数
     *
     * @param request
     * @return
     */
    private String getAllRequestParamsInfo(HttpServletRequest request) {
        StringBuffer macParams = new StringBuffer("");
        Enumeration names = request.getParameterNames();
        // <paramName=paramVal><paramName=paramVal>
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = request.getParameter(name);
            macParams.append("<").append(name).append("=");
            macParams.append(value).append(">");
        }
        return macParams.toString();
    }

    /**
     * @param request
     * @return
     */
    private String getTraceId(HttpServletRequest request, String transId) {
        String ip = IPUtil.getIp(request);
        String hostName = SystemTool.getHostName();
        StringBuffer traceId = new StringBuffer("");
        //userIp-uuid-hostName-requestURI
        traceId.append(ip).append(SEPARATOR).append(transId).append(SEPARATOR).append(hostName)
                .append(SEPARATOR).append(request.getRequestURI());
        return traceId.toString();
    }
}
