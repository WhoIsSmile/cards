package laughing.permission.service.impl;

import laughing.permission.dao.RoleDao;
import laughing.permission.dao.RoleMenuFuncRelDao;
import laughing.permission.dao.UserInfoDao;
import laughing.permission.entity.RoleEntity;
import laughing.permission.entity.UserInfoEntity;
import laughing.permission.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserInfoEntity findUserInfoByUserAccount(String userAccount) {
        return userInfoDao.findUserInfoByUserAccount(userAccount);
    }

    @Override
    public List<RoleEntity> findRoleListByUserId(String userId) {
        return roleDao.findRoleListByUserId(userId);
    }

    @Override
    public List<String> findFuncActionByUserId(int userId) {
        return roleMenuFuncRelDao.findFuncActionByUserId(userId);
    }
}
