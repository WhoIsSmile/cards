package laughing.cards.message.exception;

import laughing.cards.constant.ErrorEnum;
import laughing.cards.exception.CardException;

/**
 * 发送信息报错
 *
 * @author laughing
 * @date 2018-01-22 14:39:11
 */
public class MessageException extends CardException {
    public MessageException() {
    }

    public MessageException(ErrorEnum errorEnum) {
        super(errorEnum);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
