package laughing.login.exception;

import laughing.utils.exception.LaughingException;
import laughing.utils.global.ErrorEnum;

/**
 * @author laughing
 * @date 2018-02-26 20:54:29
 */
public class LoginException extends LaughingException {
    public LoginException(ErrorEnum errorEnum) {
        super(errorEnum);
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
