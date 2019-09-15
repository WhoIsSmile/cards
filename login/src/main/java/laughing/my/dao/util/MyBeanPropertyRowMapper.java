package laughing.my.dao.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.*;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MyBeanPropertyRowMapper<T> implements RowMapper<T> {


    /**
     * The class we are mapping to
     */
    private Class<T> mappedClass;


    /**
     * column mapper  entity field
     */
    private EntityTableRowMapper entityTableRowMapper;


    public MyBeanPropertyRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        initialize(mappedClass);
    }


    /**
     * Initialize the mapping metadata for the given class.
     *
     * @param mappedClass the mapped class.
     */
    protected void initialize(Class<T> mappedClass) {
        entityTableRowMapper = EntityMapperFactory.getEntityTableRowMapper(mappedClass);
    }


    /**
     * Extract the values for all columns in the current row.
     * <p>Utilizes public setters and result set metadata.
     *
     * @see ResultSetMetaData
     */
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        // 创建实例
        T mappedObject = BeanUtils.instantiate(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(bw);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            // 字段名称
            String column = JdbcUtils.lookupColumnName(rsmd, index);
            // 映射entity 属性
            String entityField = this.entityTableRowMapper.getColumnFieldNameMapper().get(column);
            // 获取PropertyDescriptor
            // PropertyDescriptor类：(属性描述器)
            PropertyDescriptor pd = this.entityTableRowMapper.getMappedFields().get(entityField);
            if (pd != null) {
                try {
                    Object value = getColumnValue(rs, index, pd);
                    if (log.isDebugEnabled() && rowNumber == 0) {
                        log.debug("Mapping column '" + column + "' to property '" +
                                pd.getName() + "' of type " + pd.getPropertyType());
                    }
                    try {
                        bw.setPropertyValue(pd.getName(), value);
                    } catch (TypeMismatchException e) {
                        if (value == null) {
                            log.debug("Intercepted TypeMismatchException for row " + rowNumber +
                                    " and column '" + column + "' with value " + value +
                                    " when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() +
                                    " on object: " + mappedObject);
                        } else {
                            throw e;
                        }
                    }
                } catch (NotWritablePropertyException ex) {
                    throw new DataRetrievalFailureException("Unable to map column " + column + " to property " + pd.getName(), ex);
                }
            }
        }
        return mappedObject;
    }

    /**
     * Initialize the given BeanWrapper to be used for row mapping.
     * To be called for each row.
     * <p>The default implementation is empty. Can be overridden in subclasses.
     *
     * @param bw the BeanWrapper to initialize
     */
    protected void initBeanWrapper(BeanWrapper bw) {
    }

    /**
     * Retrieve a JDBC object value for the specified column.
     * <p>The default implementation calls
     * {@link JdbcUtils#getResultSetValue(ResultSet, int, Class)}.
     * Subclasses may override this to check specific value types upfront,
     * or to post-process values return from <code>getResultSetValue</code>.
     *
     * @param rs    is the ResultSet holding the data
     * @param index is the column index
     * @param pd    the bean property that each result object is expected to match
     *              (or <code>null</code> if none specified)
     * @return the Object value
     * @throws SQLException in case of extraction failure
     * @see JdbcUtils#getResultSetValue(ResultSet, int, Class)
     */
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
    }


}
