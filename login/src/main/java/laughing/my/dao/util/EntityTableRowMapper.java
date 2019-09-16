package laughing.my.dao.util;

import laughing.my.entity.MenuEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author laughing
 * @create 2019-08-25 16:02:36
 * @desc
 **/
@Data
public class EntityTableRowMapper {
    /**
     * id的字段名称
     */
//    private String idName = null;

    /**
     * table对应的class
     */
    private Class tableClass = null;
    /**
     * 对应的数据库名称
     */
    private String tableName = null;

    /**
     * 表中所有的字段
     */
    private Set<String> columnNames = null;

    /**
     * 表中所有的字段对应的属性名称
     */
    private Set<String> fieldNames = null;

    /**
     * 属性名称和数据库字段名的映射
     * K: 属性名
     * V：表字段名称
     */
    private Map<String, String> fieldNameColumnMapper = null;

    /**
     * 数据库字段名和属性名称的映射
     * K：表字段名称
     * V: 属性名
     */
    private Map<String, String> columnFieldNameMapper = null;
    /**
     * 数据库字段名和class属性的映射
     * K：表字段名称
     * V：class属性
     */
    private Map<String, Field> columnFieldMapper = null;

    private Map<String, PropertyDescriptor> mappedFields = null;

    public static EntityTableRowMapper toEntityTableRowMapper(Class clazz) {
        Class clz = clazz;
        String tableName = EntityUtils.getTableName(clz);
        if (tableName == null) {
            return null;
        }
        EntityTableRowMapper entityTableRowMapper = new EntityTableRowMapper();
        Map<String, Field> columnFieldMap = EntityUtils.columnFieldMap(clz);
        Map<String, Field> superColumnFieldMap = EntityUtils.columnFieldMap(clz.getSuperclass());
        columnFieldMap.putAll(superColumnFieldMap);
        int size = columnFieldMap.size();
        Map<String, String> fieldNameColumnMapper = new HashMap<>(size);
        Map<String, String> columnFieldNameMapper = new HashMap<>(size);
        Set<String> columnNames = new HashSet<>(size);
        Set<String> fieldNames = new HashSet<>(size);
        entityTableRowMapper.setTableClass(clz);
        entityTableRowMapper.setTableName(EntityUtils.getTableName(clz));
        initPropertyDescriptor(clazz, entityTableRowMapper);
//        mapper.setIdName(EntityUtils.idColumnName(clz));
        entityTableRowMapper.setColumnFieldMapper(columnFieldMap);
        for (Map.Entry<String, Field> entry : columnFieldMap.entrySet()) {
            String columnName = entry.getKey();
            Field field = entry.getValue();
            String fieldName = field.getName();
            fieldNameColumnMapper.put(fieldName, columnName);
            columnFieldNameMapper.put(columnName,fieldName);
            fieldNames.add(fieldName);
            columnNames.add(columnName);
        }
        entityTableRowMapper.setColumnNames(columnNames);
        entityTableRowMapper.setFieldNameColumnMapper(fieldNameColumnMapper);
        entityTableRowMapper.setFieldNames(fieldNames);
        entityTableRowMapper.setColumnFieldNameMapper(columnFieldNameMapper);
        return entityTableRowMapper;
    }

    /**
     * PropertyDescriptor
     *
     * @param mappedClass
     * @param entityTableRowMapper
     */
    protected static void initPropertyDescriptor(Class mappedClass, EntityTableRowMapper entityTableRowMapper) {
        Map<String, PropertyDescriptor> mappedFields = new HashMap();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                mappedFields.put(pd.getName(), pd);
                String underscoredName = pd.getName();
                if (pd.getName().equals(underscoredName)) {
                    mappedFields.put(underscoredName, pd);
                }
            }
        }
        entityTableRowMapper.setMappedFields(mappedFields);
    }
//EntityTableRowMapper(){
//    Class clazz = getClass();
//
//    while (clazz != Object.class) {
//        Type t = clazz.getGenericSuperclass();
//        if (t instanceof ParameterizedType) {
//            Type[] args = ((ParameterizedType) t).getActualTypeArguments();
//            if (args[0] instanceof Class) {
//                clazz = (Class<T>) args[0];
//                break;
//            }
//        }
//        clazz = clazz.getSuperclass();
//    }
//}


    public static void main(String[] args) {
//        EntityTableRowMapper entityTableRowMapper = new EntityTableRowMapper();
        MenuEntity entity = new MenuEntity();
        EntityTableRowMapper entityTableRowMapper = EntityTableRowMapper.toEntityTableRowMapper(entity.getClass());
        System.out.println("a");
    }
}
