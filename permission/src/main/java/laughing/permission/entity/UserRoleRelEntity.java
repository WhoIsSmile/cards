package laughing.permission.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 16:04:53
 * @desc 用户角色关系表（1对多）
 **/
@Data
public class UserRoleRelEntity extends BaseEntity {
    /**
     * 用户
     */
    private String userId;
    /**
     * 角色ID
     */
    private String roleId;
}
