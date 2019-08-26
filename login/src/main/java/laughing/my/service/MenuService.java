package laughing.my.service;

import laughing.my.entity.MenuEntity;
import laughing.my.service.dto.UserMenuDTO;

import java.util.List;

/**
 * @author laughing
 * @create 2019-08-19 16:02:33
 * @desc
 **/
public interface MenuService {

    /**
     * 查询菜单
     *
     * @return
     */
    public List<UserMenuDTO> findMenuList();

    /**
     * 编辑菜单
     * @param menuEntity
     */
    public void editMenu(MenuEntity menuEntity);
}
