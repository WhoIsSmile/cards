package laughing.cards.controller.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息类
 *
 * @author laughing
 */
@Data
public class UserMessage extends ParentMessage implements Serializable {
    private String toUser;
}
