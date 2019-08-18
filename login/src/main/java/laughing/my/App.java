package laughing.my;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author laughing
 * @create 2019-08-05 16:30:31
 * @desc
 **/
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        // 启动键面
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
