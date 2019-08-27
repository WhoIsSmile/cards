package laughing.my.dao;


import laughing.my.entity.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2018-04-01 15:02:52
 * @desc 用户表dao
 **/
@Repository
@Slf4j
public class UserInfoDao extends BaseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询用户信息 通过  用户帐号
     *
     * @param userAccount 用户帐号
     * @return
     */
    public UserInfoEntity findUserInfoByUserAccount(String userAccount) {
        String sql = "select *  from sys_user_info where userAccount = ? limit 1";
        List<Object> params = new ArrayList<>(1);
        params.add(userAccount);
        UserInfoEntity result = jdbcTemplate.queryForObject(sql, params.toArray(), new BeanPropertyRowMapper<>(
                UserInfoEntity.class));
        return result;
    }


    public void updateUserToken(long id, String userToken) {
        String sql = "update  sys_user_info set token=? where id=?";
        List<Object> params = new ArrayList<>(1);
        params.add(userToken);
        params.add(id);
        int size = jdbcTemplate.update(sql, params.toArray());
        log.info("update user token {}, {} ,size :{}", id, userToken, size);
    }

    /**
     * @param token
     * @return
     */
    public UserInfoEntity findUserInfoByToken(String token) {
        String sql = "select * from sys_user_info where token=?";
        List<Object> params = new ArrayList<>(1);
        params.add(token);
        UserInfoEntity result = jdbcTemplate.queryForObject(sql, params.toArray(), new BeanPropertyRowMapper<>(
                UserInfoEntity.class));
        return result;
    }
    /**
     *

     public ResultPage getArticleList(PageParam pageParam) {
     ResultPage resultPage = new ResultPage();
     String baseSql = "SELECT id,title,acticleCover,acticleLink,status,webLink,createTime from generalize_article where 1= 1";
     SqlParams sqlParams = SqlHelper.mapToSql4SelectPage(pageParam, baseSql);
     // List<Map<String, Object>> result =
     // jdbcTemplate.queryForList(sqlParams.getSql(), sqlParams.getParams());
     List<GeneralizeArticle> result = jdbcTemplate.query(sqlParams.getSql(), sqlParams
     .getParams(), new BeanPropertyRowMapper<GeneralizeArticle>(
     GeneralizeArticle.class));

     resultPage.setCurrentPage(pageParam.getCurrentPage());
     resultPage.setPageSize(pageParam.getPageSize());
     resultPage.setResult(result);
     return resultPage;
     }
     */
}
