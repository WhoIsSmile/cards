package laughing.my.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import laughing.my.aop.TraceIdInterceptorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Web配置
 *
 * @author:xiayingjie
 * @createTime:17/7/15
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    // 是否打印返回消息体
    @Value("${print.responseBody:false}")
    public boolean printResponseBody;

    @Value("#{'${registry.paths:/sys/**}'.split(',')}")
    public String[] registryPaths;


    /**
     * 增加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TraceIdInterceptorAdapter traceIdInterceptorAdapter = new TraceIdInterceptorAdapter();
        registry.addInterceptor(traceIdInterceptorAdapter).addPathPatterns(registryPaths);
        super.addInterceptors(registry);
    }
//
//    @Bean
//    public ServletRegistrationBean druidStatViewServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//        Map<String, String> initParams = new HashMap<>();
//        //　可配的属性都在 StatViewServlet 和其父类下
//        initParams.put("loginUsername", "root");
//        initParams.put("loginPassword", "1234");
//        servletRegistrationBean.setInitParameters(initParams);
//        return servletRegistrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean druidWebStatFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        Map<String, String> initParams = new HashMap<>();
//        initParams.put("exclusions", "*.js,*.css,/druid/*");
//        filterRegistrationBean.setInitParameters(initParams);
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
//        return filterRegistrationBean;
//    }


}
