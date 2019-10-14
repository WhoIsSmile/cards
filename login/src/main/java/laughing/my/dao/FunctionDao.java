package laughing.my.dao;

import laughing.my.dao.util.MyBeanPropertyRowMapper;
import laughing.my.entity.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author laughing
 * @create 2018-04-07 17:03:36
 * @desc 功能列表
 **/
@Repository
public class FunctionDao extends BaseDao {


    /**
     * 查询所有的功能
     *
     * @return
     */
    public List<FunctionEntity> findFunctionList() {
        String sql = "select * from sys_func";
        List<FunctionEntity> result = jdbcTemplate.query(sql, new MyBeanPropertyRowMapper<>(
                FunctionEntity.class));
        return result;
    }

    /**
     * 通过Id删除
     *
     * @param id
     * @return
     */
    public int deleteById(long id) {
        return super.deleteById(id, "sys_func");
    }
//    public List<FunctionEntity> findFunctionListByRoleId(String roleId) {
//        String sql = "select *  from sys_role_menu roleMenu, sys_role_menu_func ";
//        List<Object> params = new ArrayList<>(1);
//        params.add(roleId);
//        List<FunctionEntity> result = jdbcTemplate.query(sql, params.toArray(), new MyBeanPropertyRowMapper<>(
//                FunctionEntity.class));
//        return result;
//    }

}
