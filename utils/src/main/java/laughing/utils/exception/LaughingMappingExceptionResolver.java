package laughing.utils.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
@Slf4j
public abstract class LaughingMappingExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        HandlerMethod method = (HandlerMethod) handler;
        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
        RestController restController = method.getBeanType().getAnnotation(RestController.class);
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append(method.getMethod().getName()).append(" error cause : ").append(ex.getMessage());
        // 判断是否ajax
        if (body == null && restController == null) {
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
            result = doWithException(ex);
            if (result == null) {
                log.error("error:{}", ex.getCause());
                result = new RsResult(ErrorEnum.SYSTEM_ERROR);
            }
        }
        logger.error(errorMsg.toString());
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 判断是否是ajax
     *
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return !request.getHeader("accept").contains(MediaType.TEXT_HTML_VALUE);
    }


    /**
     * 处理特殊的异常
     *
     * @param e
     * @return
     */
    public abstract RsResult doWithException(Exception e);
}
