package laughing.permission.service.impl;

import laughing.permission.dao.MenuDao;
import laughing.permission.dao.RoleDao;
import laughing.permission.dao.RoleMenuFuncRelDao;
import laughing.permission.dao.UserInfoDao;
import laughing.permission.dto.UserMenuDTO;
import laughing.permission.entity.MenuEntity;
import laughing.permission.entity.RoleEntity;
import laughing.permission.entity.UserInfoEntity;
import laughing.permission.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author laughing
 * @create 2018-04-07 16:12:41
 * @desc 用户信息服务实现类
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    public UserInfoDao userInfoDao;

    @Autowired
    public RoleDao roleDao;

    @Autowired
    public RoleMenuFuncRelDao roleMenuFuncRelDao;

    @Autowired
    public MenuDao menuDao;

    @Override
    public UserInfoEntity findUserInfoByUserAccount(String userAccount) {
        return userInfoDao.findUserInfoByUserAccount(userAccount);
    }

    @Override
    public List<RoleEntity> findRoleListByUserId(String userId) {
        return roleDao.findRoleListByUserId(userId);
    }

    @Override
    public List<String> findFuncActionByUserId(String userId) {
        return roleMenuFuncRelDao.findFuncActionByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserMenuDTO> findUserMenuList(String userId) {
        List<MenuEntity> menuEntityList = menuDao.findMenuByUserId(userId);
        if (menuEntityList == null) {
            return null;
        }
        Map<String, UserMenuDTO> menuMap = new HashMap<>(8);
        for (MenuEntity menuEntity : menuEntityList) {
            String parentMenuId = menuEntity.getParentMenuId();
            if (menuMap.get(parentMenuId) == null) {
                UserMenuDTO userMenuDTO = new UserMenuDTO();
                MenuEntity parentMenu = menuDao.findMenuByMenuId(parentMenuId);
                List<MenuEntity> childrenMenuList = new ArrayList<>(10);
                childrenMenuList.add(menuEntity);
                userMenuDTO.setParentMenu(parentMenu);
                userMenuDTO.setChildrenMenu(childrenMenuList);
                menuMap.put(parentMenuId, userMenuDTO);
            } else {
                List<MenuEntity> childrenMenuList = menuMap.get(parentMenuId).getChildrenMenu();
                childrenMenuList.add(menuEntity);
            }
        }
        Collection<UserMenuDTO> valueCollection = menuMap.values();
        List<UserMenuDTO> valueList = new ArrayList<>(valueCollection);
        sortMenu(valueList);
        return valueList;
    }


    /**
     * 菜单顺序排序
     *
     * @param list
     */
    public static void sortMenu(List<UserMenuDTO> list) {
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                UserMenuDTO dto1 = (UserMenuDTO) o1;
                UserMenuDTO dto2 = (UserMenuDTO) o2;
                if (dto1.getParentMenu().getOrderNo() > dto2.getParentMenu().getOrderNo()) {
                    return 1;
                } else if (dto1.getParentMenu().getOrderNo() == dto2.getParentMenu().getOrderNo()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}
