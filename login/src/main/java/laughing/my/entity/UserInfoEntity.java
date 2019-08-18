package laughing.my.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private String userPassword;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮件
     */
    @JsonIgnore
    private String email;

    /**
     * 用户token
     */
    private String token;

    private String image;

}
