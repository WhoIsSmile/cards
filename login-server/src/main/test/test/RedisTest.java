package test;

import laughing.LoginApp;
import laughing.login.controller.param.LoginParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoginApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testRedis() {
        LoginParam loginParam = new LoginParam();
        loginParam.setUserName("aaaaaaa");
        loginParam.setPassword("bbbbbbb");
        ValueOperations<String, LoginParam> operations = redisTemplate.opsForValue();
        operations.set("com.neox", loginParam);
        LoginParam ppp = operations.get("com.neox");
        System.out.println(ppp.getUserName());

    }
}
