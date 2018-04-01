package laughing.permission.dao.util;

import laughing.permission.dao.bean.PageParam;
import laughing.permission.dao.bean.ResultPage;
import laughing.permission.dao.bean.SqlParams;
import laughing.permission.entity.MenuEntity;
import laughing.permission.exception.LaughingSqlException;
import laughing.permission.util.StringTool;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author laughing
 * @date 2016-3-2 下午08:50:11
 * @description sqlHelp
 */
public class SqlHelper {

    /**
     * SQL 模版
     */
    public static final String SQL_INSERT_TEMPLATE = "INSERT INTO {table} ({columns}) values({params})";

    public static final String DATABASE_TABLE = "dataBaseTable";

    @SuppressWarnings("unchecked")
    public static SqlParams mapToSql4Insert(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        List<Object> paramList = new ArrayList<Object>();
        String table = params.get(DATABASE_TABLE).toString();
        if (table == null || table.equals("")) {
            throw new LaughingSqlException(
                    "map hasn't dataBaseTable key ;this is table name");
        }
        params.remove(DATABASE_TABLE);
        Iterator iter = params.entrySet().iterator();
        StringBuilder columnBuilder = new StringBuilder();
        StringBuilder paramMarkBuilder = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            Object val = entry.getValue();
            columnBuilder.append(key).append(",");
            paramMarkBuilder.append("?").append(",");
            paramList.add(val);
        }
        String column = columnBuilder.toString();
        if (column.length() > 1) {
            column = column.substring(0, column.length() - 1);
        } else {
            throw new LaughingSqlException("column is null");
        }
        String paramMark = paramMarkBuilder.toString();
        if (paramMark.length() > 1) {
            paramMark = paramMark.substring(0, paramMark.length() - 1);
        }
        String sql = StringTool.replace(SQL_INSERT_TEMPLATE, "{table}", table);
        sql = StringTool.replace(sql, "{columns}", column);
        sql = StringTool.replace(sql, "{params}", paramMark);
        sqlParams.setSql(sql);
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }

    /**
     * @param pageParam
     * @param baseSql   exception select * from generailze_acticle where 1=1
     * @return
     */
    public static SqlParams mapToSql4SelectPage(PageParam pageParam,
                                                String baseSql) {
        SqlParams sqlParams = new SqlParams();
        long currentPage = pageParam.getCurrentPage();
        int pageSize = pageParam.getPageSize();
        List<Object> paramList = new ArrayList<Object>();
        StringBuilder limitBuilder = new StringBuilder();
        StringBuilder orderByBuilder = new StringBuilder(" ORDER BY ").append(pageParam.getOrderBy()).append(" ");
        if (pageParam.isAsc()) {
            orderByBuilder.append("ASC ");
        } else {
            orderByBuilder.append("DESC ");
        }
        limitBuilder.append(" limit ?,? ");
        paramList.add((currentPage - 1) * pageSize);
        paramList.add(pageSize);
        StringBuilder sql = new StringBuilder().append(baseSql).append(orderByBuilder.toString()).append(limitBuilder.toString());
        sqlParams.setSql(sql.toString());
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }

    public static SqlParams mapToSql4SelectCount(PageParam pageParam,
                                                 String baseSql) {
        SqlParams sqlParams = new SqlParams();
        List<Object> paramList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder().append(baseSql);
        sqlParams.setSql(sql.toString());
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }

    /**
     * 带测试
     *
     * @param jdbcTemplate
     * @param pageParam
     * @param baseSql
     * @param clazz
     * @return
     */
    public static ResultPage findDataByPage(JdbcTemplate jdbcTemplate, PageParam pageParam, String baseSql, Class clazz) {
        ResultPage resultPage = new ResultPage();
        SqlParams sqlParams = SqlHelper.mapToSql4SelectPage(pageParam, baseSql);
        List result = jdbcTemplate.query(sqlParams.getSql(), sqlParams
                .getParams(), new BeanPropertyRowMapper<>(
                clazz));
        resultPage.setCurrentPage(pageParam.getCurrentPage());
        resultPage.setPageSize(pageParam.getPageSize());
        resultPage.setResult(result);
        return resultPage;

    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATABASE_TABLE, "asa");
        params.put("sasa", "aaaa");
        System.out.println(SqlHelper.mapToSql4Insert(params).getSql());
    }
}
