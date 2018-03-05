package laughing.cards.config;

import laughing.login.client.aop.LoginClientInterceptorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author laughing
 * @date 2018-03-05 14:19:07
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${login.sso.server:http://localhost:9001/u/login}")
    private String loginPage;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginClientInterceptorAdapter loginInterceptorAdapter = new LoginClientInterceptorAdapter();
        loginInterceptorAdapter.setSsoLoginUrl(loginPage);
        // 不拦截用户相关接口
        registry.addInterceptor(loginInterceptorAdapter).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
