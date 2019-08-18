package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 19:14:33
 * @desc 角色 菜单 功能 表
 **/
@Data
public class RoleMenuFuncRelEntity extends BaseEntity {
    /**
     * 角色ID
     */
    private int roleId;
    /**
     * 菜单Id
     */
    private int menuId;
    /**
     * 功能code
     */
    private String funcCode;
    /**
     * 功能的url
     */
    private String funcAction;
}
