package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 15:49:11
 * @desc 角色表信息
 **/
@Data
public class RoleEntity extends BaseEntity {
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色介绍
     */
    private String roleInfo;
}
