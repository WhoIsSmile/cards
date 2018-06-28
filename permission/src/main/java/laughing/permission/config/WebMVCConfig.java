package laughing.permission.config;

import laughing.permission.exception.SysMappingExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author laughing
 * @create 2018-06-27 19:53:47
 * @desc
 **/
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }


    @Bean
    public SysMappingExceptionResolver sysMappingExceptionResolver(){
        SysMappingExceptionResolver sysMappingExceptionResolver = new SysMappingExceptionResolver();
        return sysMappingExceptionResolver;
    }

}
