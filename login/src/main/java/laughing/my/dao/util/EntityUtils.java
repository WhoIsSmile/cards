package laughing.my.dao.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author laughing
 * @create 2019-08-25 16:17:21
 * @desc
 **/
@Slf4j
public class EntityUtils {


    public static Map columnFieldMap(Class clazz) {
        Map<String, Field> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = clazz.getDeclaredField(fields[i].getName());
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    map.put(column.name(), field);
                }
            } catch (NoSuchFieldException e) {
                log.error("columnFieldMap error : {}", e);
            }
        }
        return map;
    }

    public static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        String name = annotation.name();
        return name;
    }

    public String getFieldVal(Object obj, Field field) {
        String value = null;
        try {
            // 通过属性获取对象的属性
//            Field field = obj.getClass().getDeclaredField(propName);
            // 对象的属性的访问权限设置为可访问
            field.setAccessible(true);
            // 获取属性的对应的值
            value = field.get(obj).toString();
        } catch (Exception e) {
            log.error("getFieldVal error : {}", e);
        }

        return value;
    }

}
