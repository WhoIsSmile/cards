package laughing.my.exception;

/**
 * @author huang.xiaolong
 * @create 2019-08-27 09:26:46
 * @desc
 **/
public class LaughingTableException extends RuntimeException {
    public LaughingTableException() {
    }

    public LaughingTableException(String message) {
        super(message);
    }

    public LaughingTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public LaughingTableException(Throwable cause) {
        super(cause);
    }

    public LaughingTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
