package laughing.utils.message.mail.bean;

import lombok.Data;

/**
 * 邮件内容模版
 *
 * @author laughing
 * @date 2018-01-22 13:48:10
 */
@Data
public class MailTemplate {
    private String mailTemplate = "<p>Hi,{user}:</p><p>&nbsp;&nbsp;&nbsp;&nbsp;您的验证码是:<span style='color: red'>{content}</span></p>";
    private String toUser;
    private String content;


    /**
     * 转换成HTML文件
     *
     * @return
     */
    public String toHtml() {
        String html = mailTemplate;
        html = html.replace("{user}", toUser.substring(0, toUser.indexOf("@")));
        html = html.replace("{content}", content);
        return html;
    }

    public static void main(String[] args) {
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setContent("55222");
        mailTemplate.setToUser("woshihuangsl@121.com");
        System.out.println(mailTemplate.toHtml());
    }
}
