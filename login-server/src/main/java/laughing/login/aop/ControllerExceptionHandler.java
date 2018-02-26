package laughing.login.aop;


import laughing.utils.exception.LaughingException;
import laughing.utils.globel.ErrorEnum;
import laughing.utils.response.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一错误处理
 *
 * @author laughing
 * @date 2018-02-26 21:30:56
 */
@ControllerAdvice()
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RsResult handler(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return new RsResult(ErrorEnum.SYSTEM_ERROR);
    }


    @ExceptionHandler(LaughingException.class)
    @ResponseBody
    public RsResult serviceHandler(LaughingException e, HttpServletRequest request) {
        log.error("{} ({})", e.getErrorEnum().getErrorMsg(), e.getErrorEnum().getErrorCode());
        return new RsResult(e.getErrorEnum());
    }


}
