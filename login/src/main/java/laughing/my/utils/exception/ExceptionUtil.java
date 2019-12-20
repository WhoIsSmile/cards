package laughing.my.utils.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huang.xiaolong
 * @create 2019-12-20 15:00:53
 * @desc 打印异常信息辅助类
 **/
@Slf4j
public class ExceptionUtil {

    /**
     * 打印异常信息
     *
     * @param e
     */
    public static void printExceptionInfo(Exception e) {
        StackTraceElement element = e.getStackTrace()[0];
        log.error("error cause : [{}] --> < exception at : {}.{}-[{}] >|< className : {} >", e, element.getFileName(), element.getMethodName(),
                element.getLineNumber(), element.getClassName());
    }


    public static void main(String[] args) {
        try {
            String[] aaa = "sadas,asdad".split(",");
            System.out.println("args = [" + aaa[3] + "]");
        } catch (Exception e) {
            printExceptionInfo(e);
        }

    }
}
