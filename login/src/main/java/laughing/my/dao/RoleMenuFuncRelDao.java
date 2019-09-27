package laughing.my.dao;


import laughing.my.dao.util.MyBeanPropertyRowMapper;
import laughing.my.entity.RoleMenuFuncRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2018-04-07 17:12:49
 * @desc
 **/
@Repository
public class RoleMenuFuncRelDao  extends  BaseDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据 角色 ID 获取相应菜单的功能
     *
     * @param roleId
     * @return
     */
    public List<RoleMenuFuncRelEntity> findFunctionListByRoleId(String roleId) {
        String sql = "select *  from  sys_role_menu_func  where role_id=? order by menu_id";
        List<Object> params = new ArrayList<>(1);
        params.add(roleId);
        List<RoleMenuFuncRelEntity> result = jdbcTemplate.query(sql, params.toArray(), new MyBeanPropertyRowMapper<>(
                RoleMenuFuncRelEntity.class));
        return result;
    }

    /**
     * 根据用户Id 查询 所有功能
     * userId——>roleId——>menuId——>fun
     *
     * @param userId
     * @return
     */
    public List<String> findFuncActionByUserId(String userId) {
        String sql = "select DISTINCT (menuFunc.func_action) from sys_user_role userRole ,sys_role_menu_func menuFunc where userRole.user_id=? and userRole.role_id=menuFunc.role_id";
        List<Object> params = new ArrayList<>(1);
        params.add(userId);
        List<String> result = jdbcTemplate.queryForList(sql, params.toArray(), String.class);
        return result;
    }


}
