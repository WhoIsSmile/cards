package laughing.utils.response;

import laughing.utils.globel.ErrorEnum;
import lombok.Data;

/**
 * 返回对象
 *
 * @author laughing
 * @date 2018-02-26 21:17:49
 */
@Data
public class RsResult<T> {
    private String resultCode = ErrorEnum.SUCCESS.getErrorCode();
    private String resultMsg = ErrorEnum.SUCCESS.getErrorMsg();
    private T resultBody = (T) new Object();

    public RsResult() {
    }

    public RsResult(ErrorEnum errorEnum) {
        this.resultCode = errorEnum.getErrorCode();
        this.resultMsg = errorEnum.getErrorMsg();
    }
}
