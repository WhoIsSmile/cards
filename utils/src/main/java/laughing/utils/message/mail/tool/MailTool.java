package laughing.utils.message.mail.tool;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

/**
 * @author huang.xiaolong
 * @from http://www.yiibai.com/javamail_api/javamail_api_smtp_servers.html
 */
public class MailTool {
    /**
     * 定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
     **/
    private String displayName;
    private String to;
    private String from;
    private String smtpServer;
    private String username;
    private String password;
    private String subject;
    private String content;
    private boolean isExchange = false;
    private String domain;
    /**
     * 服务器是否要身份认证
     */
    private boolean ifAuth;
    private String filename = "";
    /**
     * 用于保存发送附件的文件名的集合
     */
    private Vector<String> file = new Vector<String>();

    /**
     * 设置SMTP服务器地址
     */
    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    /**
     * 设置发件人的地址
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 设置显示的名称
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 设置服务器是否需要身份认证
     */
    public void setIfAuth(boolean ifAuth) {
        this.ifAuth = ifAuth;
    }

    /**
     * 设置E-mail用户名
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * 设置E-mail密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置接收者
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 设置主题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 设置主体内容
     */
    public void setContent(String content) {
        this.content = content;
    }


    public boolean isExchange() {
        return isExchange;
    }

    public void setExchange(boolean isExchange) {
        this.isExchange = isExchange;
    }

    // 域设置
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 该方法用于收集附件名
     */
    public void addAttachfile(String fname) {
        file.addElement(fname);
    }

    public MailTool() {

    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容
     */
    public MailTool(String smtpServer, String from, String displayName,
                    String username, String password, String to, String subject,
                    String content) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = true;
        this.username = username;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、接收者、主题、内容
     */
    public MailTool(String smtpServer, String from, String displayName, String to,
                    String subject, String content, boolean isExchange, String domain) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = false;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.isExchange = isExchange;
        this.domain = domain;
    }

    /**
     * 发送邮件
     */
    public HashMap<String, String> send() {
        HashMap<String, String> map = new HashMap<String, String>(100);
        map.put("state", "success");
        String message = "邮件发送成功！";
        Session session = null;
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);

        if (isExchange) {
            if (domain == null || domain.equals("")) {
                throw new RuntimeException("domain is null");
            }
            props.setProperty("mail.smtp.auth.ntlm.domain", domain);
        }
        // 服务器需要身份认证
        if (ifAuth) {
            props.put("mail.smtp.auth", "true");
            MailAuthenticator smtpAuth = new MailAuthenticator(username, password);
            session = Session.getDefaultInstance(props, smtpAuth);
        } else {
            props.put("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props, null);
        }
        session.setDebug(true);
        Transport trans = null;
        try {
            Message msg = new MimeMessage(session);
            try {
                Address from_address = new InternetAddress(from, displayName);
                msg.setFrom(from_address);
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(content.toString(), "text/html;charset=gb2312");
            mp.addBodyPart(mbp);
            // 有附件
            if (!file.isEmpty()) {
                Enumeration<String> efile = file.elements();
                while (efile.hasMoreElements()) {
                    mbp = new MimeBodyPart();
                    // 选择出每一个附件名
                    filename = efile.nextElement().toString();
                    // 得到数据源
                    FileDataSource fds = new FileDataSource(filename);
                    // 得到附件本身并至入BodyPart
                    mbp.setDataHandler(new DataHandler(fds));
                    // 得到文件名同样至入BodyPart
                    mbp.setFileName(fds.getName());
                    mp.addBodyPart(mbp);
                }
                file.removeAllElements();
            }
            // Multipart加入到信件
            msg.setContent(mp);
            // 设置信件头的发送日期
            msg.setSentDate(new Date());
            // 发送信件
            msg.saveChanges();
            trans = session.getTransport("smtp");
            trans.connect(smtpServer, username, password);
            trans.sendMessage(msg, msg.getAllRecipients());
            trans.close();

        } catch (AuthenticationFailedException e) {
            map.put("state", "failed");
            message = "邮件发送失败！错误原因：\n" + "身份验证错误!";
            e.printStackTrace();
        } catch (MessagingException e) {
            message = "邮件发送失败！错误原因：\n" + e.getMessage();
            map.put("state", "failed");
            e.printStackTrace();
            Exception ex = null;
            if ((ex = e.getNextException()) != null) {
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        }
        map.put("message", message);
        return map;
    }

    public static void main(String[] args) {
        String smtpServer = "exchange.bestv.com.cn";
        String from = "huang.xiaolong@bestv.com.cn";
        String userName = "huang.xiaolong";
        String password = "************";
        String to = "563797440@qq.com";
        String subject = "你好";
        String content = "你好啊";
        MailTool mail = new MailTool(smtpServer, from, "BESTVYDJD", userName, password,
                to, subject, content);
        mail.send();
    }
}