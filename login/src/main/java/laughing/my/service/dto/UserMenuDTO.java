package laughing.my.service.dto;


import laughing.my.entity.MenuEntity;
import lombok.Data;

import java.util.List;

/**
 * @author laughing
 * @create 2018-07-16 10:58:05
 * @desc 父节点和子节点的关系的DTO
 **/
@Data
public class UserMenuDTO {

    /**
     * 父节点
     */
    private MenuEntity parentMenu;
    /**
     * 子节点
     */
    private List<MenuEntity> childrenMenu;
}
