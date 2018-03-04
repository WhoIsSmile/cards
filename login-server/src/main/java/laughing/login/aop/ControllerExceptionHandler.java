//package laughing.login.aop;
//
//
//import laughing.login.exception.LoginException;
//import laughing.utils.exception.LaughingException;
//import laughing.utils.global.ErrorEnum;
//import laughing.utils.response.RsResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 统一错误处理 （不能满足  请求页面返回页面报错，请求json返回Json 的错误格式）
// *
// * @author laughing
// * @date 2018-02-26 21:30:56
// */
//@ControllerAdvice()
//
//@Slf4j
//public class ControllerExceptionHandler {
//
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public RsResult handler(Exception ex, HttpServletRequest request) {
//        log.error(ex.getMessage(), ex);
//        return new RsResult(ErrorEnum.SYSTEM_ERROR);
//    }
//
//
//    @ExceptionHandler(LoginException.class)
//    @ResponseBody
//    public RsResult serviceHandler(LoginException e, HttpServletRequest request) {
//        log.error("{} ({})", e.getErrorEnum().getErrorMsg(), e.getErrorEnum().getErrorCode());
//        return new RsResult(e.getErrorEnum());
//    }
//
//
//}
