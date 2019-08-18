package laughing.my.service.impl;


import laughing.my.constant.SysEnum;
import laughing.my.dao.MenuDao;
import laughing.my.dao.RoleDao;
import laughing.my.dao.RoleMenuFuncRelDao;
import laughing.my.dao.UserInfoDao;
import laughing.my.entity.MenuEntity;
import laughing.my.entity.RoleEntity;
import laughing.my.entity.UserInfoEntity;
import laughing.my.service.UserInfoService;
import laughing.my.service.dto.MenuRouter;
import laughing.my.service.dto.Meta;
import laughing.my.service.dto.UserMenuDTO;
import laughing.my.utils.encrypt.RSAUtils;
import laughing.utils.exception.LaughingException;
import laughing.utils.global.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author laughing
 * @create 2018-04-07 16:12:41
 * @desc 用户信息服务实现类
 **/
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    public UserInfoDao userInfoDao;

    @Autowired
    public RoleDao roleDao;

    @Autowired
    public RoleMenuFuncRelDao roleMenuFuncRelDao;

    @Autowired
    public MenuDao menuDao;

    @Value("${user.login.time:400}")
    private int loginTime;

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


    @Override
    public List<MenuRouter> findMenuRouters(String userId) {
        List<MenuEntity> menuList = menuDao.findMenuByUserId(userId);
        if (menuList == null || menuList.isEmpty()) {
            return null;
        }
        Map<String, MenuRouter> routerMap = new HashMap<>(menuList.size() / 2);
        for (MenuEntity menu : menuList) {
            MenuRouter router = assemblyMenuRouter(menu);
            if (menu.getParentMenuId().equals(SysEnum.MENU_ROOT.getValue())) {
                routerMap.put(String.valueOf(menu.getId()), router);
            } else {
                MenuRouter parentRouter = routerMap.get(menu.getParentMenuId());
                parentRouter.getChildren().add(router);
            }
        }
        Collection<MenuRouter> valueCollection = routerMap.values();
        List<MenuRouter> routers = new ArrayList<>(valueCollection);
        return routers;
    }


    private MenuRouter assemblyMenuRouter(MenuEntity menu) {
        MenuRouter router = new MenuRouter();
        router.setName(menu.getMenuName());
        router.setComponent(menu.getComponent());
        router.setPath(menu.getPath());
        router.setSort(menu.getOrderNo());
        Meta meta = new Meta();
        meta.setIcon(menu.getIcon());
        meta.setTitle(menu.getMenuName());
        router.setMeta(meta);
        return router;
    }

    @Override
    public String updateUserToken(long userId) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        userInfoDao.updateUserToken(userId, token);
        log.info("user token update success : {} -> {}", userId, token);
        return token;
    }

    @Override
    public UserInfoEntity userLogin(String userAccount, String password) {
        UserInfoEntity userInfoEntity = findUserInfoByUserAccount(userAccount);
        if (userInfoEntity == null) {
            throw new LaughingException(ErrorEnum.LOGIN_PASSWORD_ERROR);
        }
        try {
            String psd = RSAUtils.privateDecrypt(password);
            if (psd.indexOf(userInfoEntity.getUserPassword()) == 0) {
                String time = StringUtils.substring(psd, userInfoEntity.getUserPassword().length());
                long ctime = System.currentTimeMillis() / 1000 - Long.parseLong(time) / 1000;
                if (ctime > loginTime) {
                    log.info("----超时---");
                }
                String token = updateUserToken(userInfoEntity.getId());
                userInfoEntity.setToken(token);

            } else {
                log.info("password error :{}", password);
                throw new LaughingException(ErrorEnum.LOGIN_PASSWORD_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("password error :{}", password);
            throw new LaughingException(ErrorEnum.LOGIN_PASSWORD_ERROR);
        }
        return userInfoEntity;
    }

    @Override
    public UserInfoEntity getUserInfoByToken(String token) {

        return userInfoDao.findUserInfoByToken(token);
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
