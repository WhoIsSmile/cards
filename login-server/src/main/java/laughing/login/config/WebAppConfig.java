package laughing.login.config;

import laughing.login.aop.LoginInterceptorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author laughing
 * @date 2018-03-04 14:19:07
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${login.interceptor.login.page:/index.html}")
    private String loginPage;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
//        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
//        super.addResourceHandlers(registry);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptorAdapter loginInterceptorAdapter = new LoginInterceptorAdapter();
        loginInterceptorAdapter.setLoginHtml(loginPage);
        // 不拦截用户相关接口：登录（login）、注册（register）、获取用户数据(getUser)
        registry.addInterceptor(loginInterceptorAdapter).addPathPatterns("/**").excludePathPatterns("/u/*");
        super.addInterceptors(registry);
    }

}
