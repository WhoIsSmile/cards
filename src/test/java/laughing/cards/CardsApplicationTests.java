package laughing.cards;

import laughing.cards.message.mail.tool.SpringBootMailTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardsApplicationTests {

    @Autowired
    SpringBootMailTool springBootMailTool;

    @Test
    public void contextLoads() {

        springBootMailTool.sendHtmlMail("563797440@qq.com","test" ,"test");
    }

}
