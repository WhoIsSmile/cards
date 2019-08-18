package laughing.my.service;


import laughing.my.entity.RoleEntity;
import laughing.my.entity.UserInfoEntity;
import laughing.my.service.dto.MenuRouter;
import laughing.my.service.dto.UserMenuDTO;

import java.util.List;

/**
 * @author laughing
 * @create 2018-04-07 16:10:56
 * @desc 用户信息服务
 **/
public interface UserInfoService {

    /**
     * 根据用户帐号获取用户信息
     *
     * @param userAccount
     * @return
     */
    public UserInfoEntity findUserInfoByUserAccount(String userAccount);

    /**
     * 根据UserId 获取 角色列表
     *
     * @param userId
     * @return
     */
    public List<RoleEntity> findRoleListByUserId(String userId);

    /**
     * 获取可访问列表通过userId
     *
     * @param userId
     * @return
     */
    public List<String> findFuncActionByUserId(String userId);

    /**
     * @param userId
     * @return
     */
    public List<UserMenuDTO> findUserMenuList(String userId);


    /**
     * 更新token
     *
     * @param userId
     * @return
     */
    public String updateUserToken(long userId);


    /**
     * 校验用户
     *
     * @param userAccount
     * @param password
     * @return
     */
    public UserInfoEntity userLogin(String userAccount, String password);


    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    public UserInfoEntity getUserInfoByToken(String token);


    public List<MenuRouter> findMenuRouters(String userId);

}
