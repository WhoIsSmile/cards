package laughing.utils.global;

/**
 * @author laughing
 * @date 2018-01-22 15:05:30
 */
public enum ErrorEnum {


    SUCCESS("0", "SUCCESS"),
    /**
     * 缺少必要参数
     */
    PARAM_IS_NULL("-0001", "参数为空"),
    EMPTY_USER_NAME("-0002", "用户名为空"),
    EMPTY_PASSWORD("-0003", "密码为空"),

    LOGIN_TOKEN_EXPIRE("-1000", "Token过期"),
    LOGIN_PASSWORD_ERROR("-1001", "密码错误"),
    LOGIN_USER_NOT_EXIST("-1002", "用户不存在"),
    LOGIN_USER_EXIST("-1003", "用户不存在"),

    CODE_ERROR("-0100", "加密错误"),
    NO_PAIR("-2000", "不是对子"),
    TOO_MANY_PEOPLE("-2100", "太多人了"),
    SEND_MESSAGE_FAIL("-3000", "发送信息失败"),

    SYSTEM_ERROR("-9999", "系统异常");

    private ErrorEnum() {
    }

    private ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private String errorCode;
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
