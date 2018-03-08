package laughing.utils.message.mail;

import laughing.utils.message.AbstractMessage;
import laughing.utils.message.mail.bean.MailTemplate;
import laughing.utils.message.mail.tool.SpringBootMailTool;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author laughing
 * @date 2018-01-22 14:10:06
 */

public class MailMessage extends AbstractMessage {

    @Autowired
    private SpringBootMailTool springBootMailTool;


    /**
     * 发送信息
     *
     * @param toUser
     * @param content
     * @return
     */
    @Override
    public boolean seanMessage(String toUser, String content) {
        String htmlContent = fillContent(toUser, content);
        springBootMailTool.sendHtmlMail(toUser, getSubject(), htmlContent);
        return true;
    }

    @Override
    public String fillContent(String toUser, String content) {
        MailTemplate template = new MailTemplate();
        template.setToUser(toUser);
        template.setContent(content);
        return template.toHtml();
    }
}
