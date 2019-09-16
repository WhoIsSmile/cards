package laughing.my.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 14:51:34
 * @desc 用户
 **/
@Data
@Table(name = "sys_user_info")
public class UserInfoEntity extends BaseEntity {

    /**
     * 帐号
     */
    @Column(name = "user_account")
    private String userAccount;
    /**
     * 密码（md5 加密）
     */
    @JsonIgnore
    @Column(name = "user_password")
    private String userPassword;
    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 邮件
     */
    @JsonIgnore
    @Column(name = "email")
    private String email;

    /**
     * 用户token
     */
    @Column(name = "token")
    private String token;

    @Column(name = "image")
    private String image;

}
