package laughing.cards.message;


import lombok.Data;

/**
 * 抽象类
 *
 * @author laughing
 * @date 2018-01-22 14:32:02
 */
@Data
public abstract class AbstractMessage implements Message {
    private String subject = "XXXX注册验证码";

    /**
     * 填充内容利用模版
     *
     * @param toUser
     * @param content
     * @return
     */
    public abstract String fillContent(String toUser, String content);

}
