package laughing.my.dao;

import laughing.my.dao.util.MyBeanPropertyRowMapper;
import laughing.my.entity.MenuFuncRelEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author laughing
 * @create 2019-10-14 09:26:39
 * @desc
 **/
@Repository
public class MenuFuncRelDao extends BaseDao {

    /**
     * 查询菜单功能
     *
     * @param menuId
     * @return
     */
    public List<MenuFuncRelEntity> findMenuFunc(String menuId) {
        String sql = "select * from sys_menu_func where menu_id = ?";
        return jdbcTemplate.query(sql.toString(), new MyBeanPropertyRowMapper<>(
                MenuFuncRelEntity.class));
    }


}
