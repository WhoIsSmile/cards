package laughing.my.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author laughing
 * @create 2018-04-07 17:03:36
 * @desc 功能列表
 **/
@Repository
public class FunctionDao extends  BaseDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;


//    public List<FunctionEntity> findFunctionListByRoleId(String roleId) {
//        String sql = "select *  from sys_role_menu roleMenu, sys_role_menu_func ";
//        List<Object> params = new ArrayList<>(1);
//        params.add(roleId);
//        List<FunctionEntity> result = jdbcTemplate.query(sql, params.toArray(), new MyBeanPropertyRowMapper<>(
//                FunctionEntity.class));
//        return result;
//    }

}
