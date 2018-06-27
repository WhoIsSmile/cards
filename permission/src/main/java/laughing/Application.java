package laughing;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author laughing
 * @create 2018-04-07 15:49:37
 * @desc 程序启动类
 **/
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        // 启动键面
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
