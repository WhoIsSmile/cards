package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 19:04:36
 * @desc 菜单功能表映射（1对多）
 **/
@Data
public class MenuFuncRelEntity extends BaseEntity {
    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 功能Code
     */
    private String funcCode;

    /**
     * 权限
     */
    private String funcAction;
}
