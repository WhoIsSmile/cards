package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 17:57:17
 * @desc 角色菜单关系对象 （1对多）
 **/
@Data
@Table(name = "sys_role_menu")
public class RoleMenuRelEntity extends BaseEntity {

    /**
     * 角色
     */
    @Column(name = "role_id")
    private int roleId;
    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private int menuId;
}
