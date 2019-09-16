package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 15:49:11
 * @desc 角色表信息
 **/
@Data
@Table(name = "sys_role")
public class RoleEntity extends BaseEntity {
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色介绍
     */
    @Column(name = "role_info")
    private String roleInfo;
}
