package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 17:49:13
 * @desc 菜单对象
 **/
@Data
@Table(name = "sys_menu")
public class MenuEntity extends BaseEntity {
    /**
     * 菜单名称
     */
    @Column(name = "menuName")
    private String menuName;
    /**
     * 父级菜单ID 0：是顶级菜单
     */
    @Column(name = "parentMenuId")
    private String parentMenuId;


    @Column(name = "component")
    private String component;
    /**
     * 地址
     */
    @Column(name = "path")
    private String path;
    /**
     * 排序
     */
    @Column(name = "orderNo")
    private int orderNo;
    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;
}
