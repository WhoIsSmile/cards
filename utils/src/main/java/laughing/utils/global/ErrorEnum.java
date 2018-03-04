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
    CODE_ERROR("-0100", "加密错误"),

    NO_PAIR("-1000", "不是对子"),
    TOO_MANY_PEOPLE("-1100", "太多人了"),
    SEND_MESSAGE_FAIL("-2000", "发送信息失败"),
    PASSWORD_ERROR("-3000", "密码错误"),
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
