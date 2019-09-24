package laughing.utils.net.response.bean;

import laughing.utils.global.ErrorEnum;
import lombok.Data;

/**
 * 返回对象
 *
 * @author laughing
 * @date 2018-02-26 21:17:49
 */
@Data
public class RsResult<T> {
    private String transNo;
    private String code = ErrorEnum.SUCCESS.getErrorCode();
    private String message = ErrorEnum.SUCCESS.getErrorMsg();
    private T body;

    public RsResult() {
    }

    public RsResult(ErrorEnum errorEnum) {
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getErrorMsg();
    }

    public RsResult(ErrorEnum errorEnum, T body) {
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getErrorMsg();
        this.body = body;
    }

}
