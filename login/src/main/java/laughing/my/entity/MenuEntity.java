package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 17:49:13
 * @desc 菜单对象
 **/
@Data
public class MenuEntity extends BaseEntity {
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父级菜单ID 0：是顶级菜单
     */
    private String parentMenuId;

    private String component;
    /**
     * 地址
     */
    private String path;
    /**
     * 排序
     */
    private int orderNo;
    /**
     * 图标
     */
    private String icon;
}
