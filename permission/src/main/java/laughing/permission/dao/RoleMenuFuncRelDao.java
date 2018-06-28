package laughing.permission.dao;

import laughing.permission.entity.RoleMenuFuncRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class RoleMenuFuncRelDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据 角色 ID 获取相应菜单的功能
     *
     * @param roleId
     * @return
     */
    public List<RoleMenuFuncRelEntity> findFunctionListByRoleId(String roleId) {
        String sql = "select *  from  sys_role_menu_func  where roleId=? order by menuId";
        List<Object> params = new ArrayList<>(1);
        params.add(roleId);
        List<RoleMenuFuncRelEntity> result = jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper<>(
                RoleMenuFuncRelEntity.class));
        return result;
    }

    public List<String> findFuncActionByUserId(int userId){
        String sql = "select DISTINCT (menuFunc.funcAction) from sys_user_role userRole ,sys_role_menu_func menuFunc where userRole.userId=? and userRole.roleId=menuFunc.roleId";
        List<Object> params = new ArrayList<>(1);
        params.add(userId);
        List<String> result = jdbcTemplate.queryForList(sql, params.toArray(),String.class);
        return result;
    }


}
