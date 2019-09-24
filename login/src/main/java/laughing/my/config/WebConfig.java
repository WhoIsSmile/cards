package laughing.my.config;

import laughing.my.aop.TraceIdInterceptorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    @Value("#{'${registry.paths:/**}'.split(',')}")
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




}
