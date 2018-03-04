package laughing.login.config;

import laughing.utils.exception.LaughingMappingExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * @author laughing
 * @date 2018-03-04 13:28:28
 */
@Configuration
public class LoginConfig {
    @Bean
    public HandlerExceptionResolver errorHandlerr() {
        return new LaughingMappingExceptionResolver();
    }
}
