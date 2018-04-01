package laughing.permission.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 19:14:33
 * @desc 角色 菜单 功能 表
 **/
@Data
public class RoleMenuFuncRelEntity extends BaseEntity {
    private int roleId;
    private int menuId;
    private String funcCode;
    /**
     * 功能的url
     */
    private String func_action;
}
