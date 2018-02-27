package laughing.login.dao;

import laughing.utils.entity.UserEntity;

/**
 * @author laughing
 * @date 2018-02-27 17:56:25
 */
public interface UserDao {
    /**
     * 根据用户名字查询
     *
     * @param userName
     * @return
     */
    public UserEntity getUserByUserName(String userName);

    /**
     * 保存用户数据
     *
     * @param userEntity
     * @return
     */
    public UserEntity saveUserEntity(UserEntity userEntity);

    /**
     * 检查用户是否存在
     *
     * @param userName
     * @return
     */
    public boolean checkUserExist(String userName);
}
