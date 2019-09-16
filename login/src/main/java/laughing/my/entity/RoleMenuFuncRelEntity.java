package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 19:14:33
 * @desc 角色 菜单 功能 表
 **/
@Data
@Table(name = "sys_role_menu_func")
public class RoleMenuFuncRelEntity extends BaseEntity {
    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private int roleId;
    /**
     * 菜单Id
     */
    @Column(name = "menu_id")
    private int menuId;
    /**
     * 功能code
     */

    @Column(name = "func_code")
    private String funcCode;
    /**
     * 功能的url
     */
    @Column(name = "func_action")
    private String funcAction;
}
