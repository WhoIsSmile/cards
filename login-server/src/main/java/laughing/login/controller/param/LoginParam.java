package laughing.login.controller.param;

import lombok.Data;

/**
 * @author laughing
 * @date 2018-02-26 20:48:01
 */
@Data
public class LoginParam {

    private String userName;
    private String password;
    /**
     * 服务类型
     */
    private String serviceType;
    /**
     * 回调地址
     */
    private String callBack;

}
