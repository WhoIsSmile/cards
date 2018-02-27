package laughing.utils.entity;

import lombok.Data;

/**
 * 用户信息表
 *
 * @author laughing
 * @date 2018-02-27 17:44:05
 */
@Data
public class UserEntity {

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮件
     */
    private String email;
    private int sex;
    /**
     * 创建时间
     */
    private long createdTime;
    /**
     * 更新时间
     */
    private long updateTime;

}
