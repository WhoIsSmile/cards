package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 17:57:17
 * @desc 角色菜单关系对象 （1对多）
 **/
@Data
public class RoleMenuRelEntity extends BaseEntity {

    /**
     * 角色
     */
    private int roleId;
    /**
     * 菜单ID
     */
    private int menuId;
}
