package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 19:04:36
 * @desc 菜单功能表映射（1对多）
 **/
@Data
@Table(name = "sys_menu_func")
public class MenuFuncRelEntity extends BaseEntity {
    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private String menuId;

    /**
     * 功能Code
     */
    @Column(name = "func_code")
    private String funcCode;

    /**
     * 权限
     */
    @Column(name = "func_action")
    private String funcAction;
}
