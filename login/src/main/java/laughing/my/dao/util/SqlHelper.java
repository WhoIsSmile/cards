package laughing.my.dao.util;


import laughing.my.dao.bean.PageParam;
import laughing.my.dao.bean.ResultPage;
import laughing.my.dao.bean.SqlParams;
import laughing.my.entity.MenuEntity;
import laughing.my.exception.LaughingSqlException;
import laughing.my.utils.StringTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.*;


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

    public static final String SQL_UPDTATE_BYID_TEMPLATE = "UPDATE {table} set {conditions} where id=?";
    public static final String DATABASE_TABLE = "dataBaseTable";

    public static final String DATABASE_TABLE_ID = "id";

    /**
     * map 转成sql
     *
     * @param params
     * @return
     */
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
     * @param baseSql   example: select * from generailze_acticle where 1=1
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


    public static SqlParams mapToUpdateById(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        List<Object> paramList = new ArrayList<Object>();
        String table = params.get(DATABASE_TABLE).toString();
        if (table == null || table.equals("")) {
            throw new LaughingSqlException(
                    "map hasn't dataBaseTable key ;this is table name");
        }
        params.remove(DATABASE_TABLE);
        Object id = params.get(DATABASE_TABLE_ID).toString();
        if (id == null) {
            throw new LaughingSqlException(
                    "map hasn't dataBaseTable  key ;this is row id");
        }
        params.remove(DATABASE_TABLE_ID);
        Iterator iter = params.entrySet().iterator();
        StringBuilder condition = new StringBuilder();
//        StringBuilder paramMarkBuilder = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            Object val = entry.getValue();
            condition.append(key).append("=").append("?").append(",");
            paramList.add(val);
        }
        String column = condition.toString();
        if (column.length() > 1) {
            column = column.substring(0, column.length() - 1);
        } else {
            throw new LaughingSqlException("column is null");
        }

        String sql = StringTool.replace(SQL_INSERT_TEMPLATE, "{table}", table);
        sql = StringTool.replace(sql, "{conditions}", column);
        paramList.add(id);
        sqlParams.setSql(sql);
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

    /**
     * @param obj
     * @return
     */
    public static SqlParams entityToInsertSql(Object obj) {
        SqlParams sqlParams = new SqlParams();
        EntityTableRowMapper entityTableRowMapper = EntityMapperFactory.getEntityTableRowMapper(obj.getClass());
        Map<String, Field> columnFieldMapper = entityTableRowMapper.getColumnFieldMapper();
        List<Object> insertColumnValues = new ArrayList();
        List insertColumns = new ArrayList(columnFieldMapper.size());
        for (Map.Entry<String, Field> stringFieldEntry : columnFieldMapper.entrySet()) {
            Field field = stringFieldEntry.getValue();
            Object value = EntityUtils.getValue(obj, field);
            if (value == null) {
                continue;
            }
            insertColumns.add(stringFieldEntry.getKey());
            insertColumnValues.add(value);
        }
        StringBuilder builder = new StringBuilder();
        int size = insertColumns.size();
        builder.append("INSERT INTO ").append(entityTableRowMapper.getTableName()).append(StringUtils.SPACE);
        builder.append("( ").append(StringUtils.join(insertColumns, ", ")).append(" ) ");
        builder.append(" VALUES (");
        for (int i = 0; i < size; i++) {
            builder.append("? ");
            if (i != size - 1) {
                builder.append(",");
            } else {
                builder.append(")");
            }
        }
        builder.append(";");
        sqlParams.setSql(builder.toString());
        sqlParams.setParams(insertColumnValues.toArray());
        return sqlParams;
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATABASE_TABLE, "asa");
        params.put("sasa", "aaaa");
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setComponent("aaa");
        menuEntity.setIcon("bbb");
        menuEntity.setOrderNo(1);
        for (int i=0;i<100;i++){
            SqlParams params1 = entityToInsertSql(menuEntity);
            System.out.println(params1.getSql());
        }

//        System.out.println(SqlHelper.mapToSql4Insert(params).getSql());
    }
}
