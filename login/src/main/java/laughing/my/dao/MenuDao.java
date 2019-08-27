package laughing.my.dao;

import laughing.my.dao.bean.PageParam;
import laughing.my.dao.bean.ResultPage;
import laughing.my.dao.bean.SqlParams;
import laughing.my.dao.util.SqlHelper;
import laughing.my.entity.BaseEntity;
import laughing.my.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2018-04-01 18:00:20
 * @desc 菜单
 **/
@Repository
public class MenuDao extends BaseDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 获取 菜单根据userId
     *
     * @param userId
     * @return
     */
    public List<MenuEntity> findMenuByUserId(String userId) {
        StringBuilder sql = new StringBuilder("select menu.* ")
                .append(" from  sys_user_role user_role,sys_menu menu, sys_role_menu role_menu ")
                .append(" where user_role.userId = ? and user_role.roleId = role_menu.roleId and role_menu.menuId=menu.id ")
                .append(" GROUP BY menu.id order by menu.parentMenuId , menu.orderNo ");
        List<Object> params = new ArrayList<>(1);
        params.add(userId);
        List<MenuEntity> result = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(
                MenuEntity.class));
        return result;
    }


    /**
     * 根据menuId 查询
     *
     * @param menuId
     * @return
     */
    public MenuEntity findMenuByMenuId(String menuId) {
        String sql = "select * from sys_menu where id=?";
        List<Object> params = new ArrayList<>(1);
        params.add(menuId);
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(
                MenuEntity.class));
    }

    /**
     * 查询菜单(列表使用)
     *
     * @param pageParam
     * @return
     */
    public ResultPage findMenuListByPage(PageParam pageParam) {
        ResultPage<Menu> resultPage;
        String baseSql = "select *  from sys_menu where  1=1 ";
        resultPage = SqlHelper.findDataByPage(jdbcTemplate, pageParam, baseSql, Menu.class);
        return resultPage;
    }


    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<MenuEntity> findMenuList() {
        String sql = "select * from sys_menu  order by parentMenuId , orderNo";
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(
                MenuEntity.class));
    }

    public void edit(MenuEntity menuEntity) {
        SqlParams params;
        if (menuEntity.getId() == null) {
            params = SqlHelper.entityToInsertSql(menuEntity);
        } else {
            params = SqlHelper.entityToUpdateSqlById(menuEntity);
        }
        jdbcTemplate.update(params.getSql(), params.getParams());
    }
}
