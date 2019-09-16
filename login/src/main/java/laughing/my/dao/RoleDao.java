package laughing.my.dao;


import laughing.my.dao.bean.PageParam;
import laughing.my.dao.bean.ResultPage;
import laughing.my.dao.bean.SqlParams;
import laughing.my.dao.util.MyBeanPropertyRowMapper;
import laughing.my.dao.util.SqlHelper;
import laughing.my.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2018-04-01 15:53:24
 * @desc 系统角色Dao
 **/
@Repository
public class RoleDao  extends  BaseDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询角色列表 分页展示
     *
     * @param pageParam
     * @return
     */
    public ResultPage findRoleList(PageParam pageParam) {
        ResultPage resultPage = new ResultPage();
        String baseSql = "select *  from sys_role where  1=1 ";
        SqlParams sqlParams = SqlHelper.mapToSql4SelectPage(pageParam, baseSql);
        List<RoleEntity> result = jdbcTemplate.query(sqlParams.getSql(), sqlParams
                .getParams(), new BeanPropertyRowMapper<RoleEntity>(
                RoleEntity.class));
        resultPage.setCurrentPage(pageParam.getCurrentPage());
        resultPage.setPageSize(pageParam.getPageSize());
        resultPage.setResult(result);
        return resultPage;
    }

    /**
     * 根据 活用Id 获取角色列表
     *
     * @param userId
     * @return
     */
    public List<RoleEntity> findRoleListByUserId(String userId) {
        String sql = "select sys_role.* from sys_role ,sys_user_role where sys_user_role.user_id=? and sys_user_role.role_id=sys_role.id";
        List<Object> params = new ArrayList<>(1);
        params.add(userId);
        List<RoleEntity> result = jdbcTemplate.query(sql, params.toArray(), new MyBeanPropertyRowMapper<>(
                RoleEntity.class));
        return result;
    }
}
