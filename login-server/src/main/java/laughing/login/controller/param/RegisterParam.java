package laughing.login.controller.param;

import lombok.Data;

/**
 * @author laughing
 * @date 2018-02-28 08:53:00
 */
@Data
public class RegisterParam {
    private String userName;
    private String password;
    private String email;
    private String phoneNum;
}
