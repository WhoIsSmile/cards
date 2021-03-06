package laughing.my.dao;

import laughing.my.dao.bean.PageParam;
import laughing.my.dao.bean.ResultPage;
import laughing.my.dao.util.MyBeanPropertyRowMapper;
import laughing.my.dao.util.SqlHelper;
import laughing.my.entity.MenuEntity;
import laughing.my.entity.MenuFuncRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2019-08-01 18:00:20
 * @desc 菜单
 **/
@Repository
public class MenuDao extends BaseDao {
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
                .append(" where user_role.user_id = ? and user_role.role_id = role_menu.role_id and role_menu.menu_id=menu.id ")
                .append(" GROUP BY menu.id order by menu.parent_menu_id , menu.order_no ");
        List<Object> params = new ArrayList<>(1);
        params.add(userId);
        List<MenuEntity> result = jdbcTemplate.query(sql.toString(), params.toArray(), new MyBeanPropertyRowMapper<>(
                MenuEntity.class));
        return result;
    }


    /**
     * 根据menuId 查询6
     *
     * @param menuId
     * @return
     */
    public MenuEntity findMenuByMenuId(String menuId) {
        String sql = "select * from sys_menu where id=?";
        List<Object> params = new ArrayList<>(1);
        params.add(menuId);
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), new MyBeanPropertyRowMapper<>(
                MenuEntity.class));
    }

    /**
     * 根据父节点查询所有子节点
     *
     * @param parentMenuId
     * @return
     */
    public List<MenuEntity> findMenuByParentMenuId(String parentMenuId) {
        String sql = "select * from sys_menu where parent_menu_id=?";
        List<Object> params = new ArrayList<>(1);
        params.add(parentMenuId);
        return jdbcTemplate.query(sql.toString(), params.toArray(), new MyBeanPropertyRowMapper<>(
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
        resultPage = SqlHelper.findDataByPage(jdbcTemplate, pageParam, "sys_menu", Menu.class);
        return resultPage;
    }


    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<MenuEntity> findMenuList() {
        String sql = "select * from sys_menu  order by parent_menu_id , order_no";
        return jdbcTemplate.query(sql.toString(), new MyBeanPropertyRowMapper<>(
                MenuEntity.class));
    }

    /**
     * 通过Id删除
     *
     * @param id
     * @return
     */
    public int deleteById(long id) {
        String tableName = "sys_menu";
        StringBuffer sql = new StringBuffer("delete from ").append(tableName).append(" where id=?");
        return jdbcTemplate.update(sql.toString(), new Object[]{id});
    }


}
