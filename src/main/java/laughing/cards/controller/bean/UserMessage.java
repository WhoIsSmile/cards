package laughing.cards.controller.bean;

import lombok.Data;

/**
 * 消息类
 *
 * @author laughing
 */
@Data
public class UserMessage {
    private String fromUser;
    private String fromUserName;
    private String toUser;
    private String text;
    private long date;
}
