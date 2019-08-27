package laughing.my.dao.util;


import laughing.my.dao.bean.PageParam;
import laughing.my.dao.bean.ResultPage;
import laughing.my.dao.bean.SelectConditions;
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

    public static final String SQL_UPDTATE_BY_ID_TEMPLATE = "UPDATE {table} set {conditions} where id=?";

    public static final String SELECT_COUNT_TEMPLATE = "select count(*) from {table} where 1=1 ";
    public static final String SELECT_PAGE_TEMPLATE = "select * from {table} where 1=1 ";


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
        Object table = params.get(DATABASE_TABLE);
        if (table == null || table.toString().equals("")) {
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
        String sql = StringTool.replace(SQL_INSERT_TEMPLATE, "{table}", table.toString());
        sql = StringTool.replace(sql, "{columns}", column);
        sql = StringTool.replace(sql, "{params}", paramMark);
        sqlParams.setSql(sql);
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }

    /**
     * @param pageParam
     * @param tableName example: generailze_acticle
     * @return
     */
    public static SqlParams mapToSql4SelectPage(PageParam pageParam,
                                                String tableName) {
        SqlParams sqlParams = new SqlParams();
        SelectConditions selectConditions = mapToSelectConditions(pageParam.getParams());
        String baseSql = SELECT_PAGE_TEMPLATE.replace("{table}", tableName);
        long currentPage = pageParam.getCurrentPage();
        int pageSize = pageParam.getPageSize();
        List<Object> paramList = selectConditions.getParams();
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
        StringBuilder sql = new StringBuilder(baseSql);
        sql.append(selectConditions);
        sql.append(orderByBuilder.toString()).append(limitBuilder.toString());
        sqlParams.setSql(sql.toString());
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }


    private static SelectConditions mapToSelectConditions(Map<String, Object> params) {
        SelectConditions conditions = new SelectConditions();
        List<Object> paramVal = new ArrayList<>();
        StringBuffer conditionBuffer = new StringBuffer();
        if (params != null) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = entry.getKey().toString();
                Object val = entry.getValue();
                conditionBuffer.append(" and ").append(key).append("=").append("?");
                paramVal.add(val);
            }
        }
        conditions.setCondition(conditionBuffer.toString());
        conditions.setParams(paramVal);
        return conditions;
    }

    public static SqlParams mapToUpdateById(Map<String, Object> params) {
        SqlParams sqlParams = new SqlParams();
        List<Object> paramList = new ArrayList<Object>();
        Object table = params.get(DATABASE_TABLE);
        if (table == null || table.toString().equals("")) {
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

        String sql = StringTool.replace(SQL_UPDTATE_BY_ID_TEMPLATE, "{table}", table.toString());
        sql = StringTool.replace(sql, "{conditions}", column);
        paramList.add(id);
        sqlParams.setSql(sql);
        sqlParams.setParams(paramList.toArray());
        return sqlParams;
    }

    public static SqlParams mapToSql4SelectCount(PageParam pageParam,
                                                 String tabelName) {
        SqlParams sqlParams = new SqlParams();
        String baseSql = SELECT_COUNT_TEMPLATE.replace("{table}", tabelName);
        SelectConditions selectConditions = mapToSelectConditions(pageParam.getParams());
        List<Object> paramList = selectConditions.getParams();

        StringBuilder sql = new StringBuilder().append(baseSql).append(selectConditions);
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
     * entity 转化成sql
     *
     * @param obj
     * @return
     */
    public static SqlParams entityToInsertSql(Object obj) {
        EntityTableRowMapper entityTableRowMapper = EntityMapperFactory.getEntityTableRowMapper(obj.getClass());
        Map<String, Field> columnFieldMapper = entityTableRowMapper.getColumnFieldMapper();
        Map<String, Object> columnValues = getEntityColumnValues(columnFieldMapper, obj);
        columnValues.put(DATABASE_TABLE, entityTableRowMapper.getTableName());
        return mapToSql4Insert(columnValues);
    }

    public static SqlParams entityToEditSql(Object entity) {
        EntityTableRowMapper entityTableRowMapper = EntityMapperFactory.getEntityTableRowMapper(entity.getClass());
        Map<String, Field> columnFieldMapper = entityTableRowMapper.getColumnFieldMapper();
        Map<String, Object> columnValues = getEntityColumnValues(columnFieldMapper, entity);
        columnValues.put(DATABASE_TABLE, entityTableRowMapper.getTableName());
        Object id = columnValues.get(DATABASE_TABLE_ID);
        if (id == null) {
            return mapToSql4Insert(columnValues);
        } else {
            return mapToUpdateById(columnValues);
        }
    }

    /**
     * @param obj
     * @return
     */
    public static SqlParams entityToUpdateSqlById(Object obj) {
        EntityTableRowMapper entityTableRowMapper = EntityMapperFactory.getEntityTableRowMapper(obj.getClass());
        Map<String, Field> columnFieldMapper = entityTableRowMapper.getColumnFieldMapper();
        Map<String, Object> columnValues = getEntityColumnValues(columnFieldMapper, obj);
        columnValues.put(DATABASE_TABLE, entityTableRowMapper.getTableName());
        return mapToUpdateById(columnValues);
    }


    public static Map<String, Object> getEntityColumnValues(Map<String, Field> columnFieldMapper, Object obj) {
        Map<String, Object> columnMap = new HashMap<>();
        for (Map.Entry<String, Field> stringFieldEntry : columnFieldMapper.entrySet()) {
            Field field = stringFieldEntry.getValue();
            Object value = EntityUtils.getValue(obj, field);
            if (value == null) {
                continue;
            }
            columnMap.put(stringFieldEntry.getKey(), value);
        }
        return columnMap;
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATABASE_TABLE, "asa");
        params.put("sasa", "aaaa");
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setComponent("aaa");
        menuEntity.setIcon("bbb");
        menuEntity.setOrderNo(1);
        menuEntity.setId(1);
        for (int i = 0; i < 100; i++) {
            SqlParams params1 = entityToUpdateSqlById(menuEntity);
            System.out.println(params1.getSql());
        }

//        System.out.println(SqlHelper.mapToSql4Insert(params).getSql());
    }
}
