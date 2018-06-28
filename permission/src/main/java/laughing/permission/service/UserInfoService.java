package laughing.permission.service;

import laughing.permission.entity.RoleEntity;
import laughing.permission.entity.UserInfoEntity;

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
    public List<String> findFuncActionByUserId(int userId);
}
