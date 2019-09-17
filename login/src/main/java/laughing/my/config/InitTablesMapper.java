package laughing.my.config;

import laughing.my.dao.util.EntityMapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author huang.xiaolong
 * @create 2019-08-30 13:49:27
 * @desc 初始化数据库和表映射关系
 **/
@Configuration
@Slf4j
public class InitTablesMapper implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 扫描包的路径
     */
    @Value("${table.mapper.package:laughing.my.entity}")
    private String tablePackages;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("----init table.mapper.package {} -----", tablePackages);
        EntityMapperFactory.init(tablePackages);
        log.info("----end init table.mapper.package {} -----", tablePackages);
    }

}
