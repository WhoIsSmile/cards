package laughing.my.dao.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laughing
 * @create 2019-08-26 09:14:42
 * @desc
 **/
public class EntityMapperFactory {

    private static ConcurrentHashMap<String, EntityTableRowMapper> entityMap = new ConcurrentHashMap<String, EntityTableRowMapper>();


    public EntityTableRowMapper getEntityTableRowMapper(Class clazz) {
        EntityTableRowMapper mapper = entityMap.get(clazz.getName());
        if (mapper == null) {
            mapper = EntityTableRowMapper.toEntityTableRowMapper(clazz);
            entityMap.put(clazz.getName(), mapper);
        }
        return mapper;
    }

    // 这里可以做初始化所有entity
    void init() {

    }

}
