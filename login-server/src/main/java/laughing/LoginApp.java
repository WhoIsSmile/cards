package laughing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 单独的登录服务器（最后的计划的是做单点登录服务）
 *
 * @author laughing
 * @date 2018-02-26 20:43:50
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class LoginApp {
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class, args);
    }
}
