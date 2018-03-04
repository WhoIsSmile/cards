package laughing.utils.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import laughing.utils.global.ErrorEnum;
import laughing.utils.response.RsResult;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;

/**
 * 统一异常处理
 *
 * @author laughing
 * @date 2018-03-04 13:32:38
 */
public class LaughingMappingExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        HandlerMethod method = (HandlerMethod) handler;
//        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append(method.getMethod().getName()).append(" error cause : ").append(ex.getMessage());
        ex.printStackTrace();
        logger.error(errorMsg.toString());
        // 判断是否是页面请求（text/html）
        if (!isAjax(request)) {
            return super.doResolveException(request, response, handler, ex);
        }
        ModelAndView mv = new ModelAndView();
        // 设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 避免乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        RsResult result;
        if (LaughingException.class.isAssignableFrom(ex.getClass())) {
            LaughingException exception = (LaughingException) ex;
            result = new RsResult(exception.getErrorEnum());
        } else {
            result = new RsResult(ErrorEnum.SYSTEM_ERROR);
        }
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

    private boolean isAjax(HttpServletRequest request) {
        return !request.getHeader("accept").contains(MediaType.TEXT_HTML_VALUE);
    }
}
