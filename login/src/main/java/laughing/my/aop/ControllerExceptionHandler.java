package laughing.my.aop;

import laughing.my.utils.exception.ExceptionUtil;
import laughing.utils.exception.LaughingException;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author huang.xiaolong
 * @create 2019-09-17 10:47:45
 * @desc 统一异常处理
 **/
@ControllerAdvice()
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RsResult handler(Exception ex, HttpServletRequest request) {
        ExceptionUtil.printExceptionInfo(ex);
        return new RsResult(ErrorEnum.SYSTEM_ERROR, new HashMap<>());
    }


    /**
     * 自定义异常处理
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(LaughingException.class)
    @ResponseBody
    public RsResult serviceHandler(LaughingException e, HttpServletRequest request) {
        log.error(new StringBuilder(e.getErrorEnum().getErrorMsg()).append("(").append(e.getErrorEnum().getErrorMsg()).append(")").toString());
        ExceptionUtil.printExceptionInfo(e);
        return new RsResult(e.getErrorEnum(), new HashMap<>());
    }
}
