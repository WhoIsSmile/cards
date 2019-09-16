package laughing.my.dao.util;

import laughing.my.exception.LaughingTableException;
import laughing.my.utils.ScanEntityPackage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laughing
 * @create 2019-08-26 09:14:42
 * @desc
 **/
@Slf4j
public class EntityMapperFactory {

    private static ConcurrentHashMap<String, EntityTableRowMapper> entityMap = new ConcurrentHashMap<String, EntityTableRowMapper>();


    /**
     * @param clazz
     * @return
     */
    public static EntityTableRowMapper getEntityTableRowMapper(Class clazz) {
        String className = clazz.getName();
        EntityTableRowMapper mapper = entityMap.get(clazz.getName());
        if (mapper == null) {
            long start = System.currentTimeMillis();
            mapper = EntityTableRowMapper.toEntityTableRowMapper(clazz);
            if (mapper == null) {
                throw new LaughingTableException(className + " : is convert error");
            }
            entityMap.put(clazz.getName(), mapper);
            log.debug("load {} endTime", clazz.getName(), System.currentTimeMillis() - start);
        } else {
            log.debug("load {} cache success", clazz.getName());
        }
        return mapper;
    }

    // 这里可以做初始化所有entity
    //"laughing.my.entity"
    public static void init(String packageName) {
        List<Class> clazzList = ScanEntityPackage.getClasses(packageName);
        if (clazzList == null) {
            return;
        }
        for (int i = 0; i < clazzList.size(); i++) {
            EntityTableRowMapper mapper = EntityTableRowMapper.toEntityTableRowMapper(clazzList.get(i));
            if (mapper != null) {
                log.info("--init EntityTableRowMapper -- {} success", clazzList.get(i).getName());
                entityMap.put(clazzList.get(i).getName(), mapper);
            }
        }

    }

    public static void main(String[] args) {
        init("laughing.my.entity");
        System.out.println(entityMap.size());
    }
//mainain
}
