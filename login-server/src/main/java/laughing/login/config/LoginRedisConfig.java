package laughing.login.config;

import laughing.utils.config.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author laughing
 * @date 2018-01-22 13:45:01
 */
@Configuration
@EnableCaching
public class LoginRedisConfig extends RedisConfig {

}
