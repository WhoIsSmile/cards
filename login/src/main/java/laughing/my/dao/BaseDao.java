package laughing.my.dao;

import laughing.my.dao.bean.SqlParams;
import laughing.my.dao.util.SqlHelper;

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
     * <p>如果字段想置空 ，字段必须是“”字符串，而不是null，不然不介意使用这个方法<p/>
     *
     * @param entity
     */
    public int edit(Object entity) {
        SqlParams params = SqlHelper.entityToSql4Edit(entity);
        return jdbcTemplate.update(params.getSql(), params.getParams());
    }

    /**
     * 保存数据
     * <p>如果字段想置空 ，字段必须是“”字符串，而不是null，不然不介意使用这个方法<p/>
     *
     * @param entity
     * @return
     */
    public int save(Object entity) {
        SqlParams params = SqlHelper.entityToSql4Insert(entity);
        return jdbcTemplate.update(params.getSql(), params.getParams());
    }

    /**
     * 通过Id 更新
     * <p>如果字段想置空 ，字段必须是“”字符串，而不是null，不然不介意使用这个方法<p/>
     *
     * @param entity
     * @return
     * @description 如果字段想置空 ，字段必须是“”字符串，而不是null，不然不介意使用这个方法
     */
    public int updateById(Object entity) {
        SqlParams params = SqlHelper.entityToSql4UpdateById(entity);
        return jdbcTemplate.update(params.getSql(), params.getParams());
    }

    /**
     * 通过Id删除
     *
     * @param id
     * @param tableName
     * @return
     */
    public int deleteById(long id, String tableName) {
        StringBuffer sql = new StringBuffer("delete from ").append(tableName).append(" where id=?");
        return jdbcTemplate.update(sql.toString(), new Object[]{id});
    }
}
