package laughing.my.service.impl;

import laughing.my.constant.SysEnum;
import laughing.my.dao.MenuDao;
import laughing.my.entity.MenuEntity;
import laughing.my.service.MenuService;
import laughing.my.service.dto.UserMenuDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author laughing
 * @create 2019-08-19 16:04:52
 * @desc
 **/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 查询菜单
     *
     * @return
     */
    @Override
    public List<UserMenuDTO> findMenuList() {
        List<MenuEntity> menuList = menuDao.findMenuList();
        Map<String, UserMenuDTO> routerMap = new HashMap<>(menuList.size() / 2);
        for (MenuEntity menu : menuList) {
            UserMenuDTO userMenuDTO;
            if (menu.getParentMenuId().equals(SysEnum.MENU_ROOT.getValue())) {
                userMenuDTO = new UserMenuDTO();
                BeanUtils.copyProperties(menu, userMenuDTO);
                userMenuDTO.setChildrenMenu(new ArrayList<>(8));
                routerMap.put(String.valueOf(menu.getId()), userMenuDTO);
            } else {
                userMenuDTO = routerMap.get(menu.getParentMenuId());
                userMenuDTO.getChildrenMenu().add(menu);
            }
        }
        Collection<UserMenuDTO> valueCollection = routerMap.values();
        List<UserMenuDTO> menuDTOList = new ArrayList<>(valueCollection);
        return menuDTOList;
    }

    @Override
    public void editMenu(MenuEntity menuEntity) {
        menuDao.edit(menuEntity);
    }

    @Override
    public void delete(long id) {
        menuDao.deleteById(id);
    }

}
