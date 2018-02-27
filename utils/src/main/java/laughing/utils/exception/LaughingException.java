package laughing.utils.exception;


import laughing.utils.global.ErrorEnum;

/**
 * 异常
 *
 * @author laughing
 * @date 2018-02-26 17:33:55
 */
public class LaughingException extends RuntimeException {
    private ErrorEnum errorEnum;


    public LaughingException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
    }

    public LaughingException(String message) {
        super(message);
    }

    public LaughingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LaughingException(Throwable cause) {
        super(cause);
    }

    public LaughingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

}
