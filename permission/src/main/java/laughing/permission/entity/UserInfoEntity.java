package laughing.permission.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 14:51:34
 * @desc 用户
 **/
@Data
public class UserInfoEntity extends BaseEntity {

    /**
     * 帐号
     */
    private String userAccount;
    /**
     * 密码（md5 加密）
     */
    private String userPassword;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮件
     */
    private String email;

}
