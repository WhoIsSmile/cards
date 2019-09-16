package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 16:04:53
 * @desc 用户角色关系表（1对多）
 **/
@Data
@Table(name = "sys_user_role")
public class UserRoleRelEntity extends BaseEntity {
    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;
}
