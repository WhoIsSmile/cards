package laughing.my.dao;

import laughing.my.dao.bean.SqlParams;
import laughing.my.dao.util.SqlHelper;
import laughing.my.entity.BaseEntity;
import laughing.my.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author huang.xiaolong
 * @create 2019-08-27 13:49:45
 * @desc
 **/
@Repository
public class BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 编辑
     *
     * @param entity
     */
    public void edit(Object entity) {
        SqlParams params = SqlHelper.entityToEditSql(entity);
        jdbcTemplate.update(params.getSql(), params.getParams());
    }

    public void save(Object entity) {
        SqlParams params = SqlHelper.entityToInsertSql(entity);
        jdbcTemplate.update(params.getSql(), params.getParams());
    }

    public void updateById(Object entity) {
        SqlParams params = SqlHelper.entityToUpdateSqlById(entity);
        jdbcTemplate.update(params.getSql(), params.getParams());
    }
}
