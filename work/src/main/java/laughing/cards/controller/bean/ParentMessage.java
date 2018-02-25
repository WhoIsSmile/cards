package laughing.cards.controller.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author laughings
 * @date 2018-01-21 16:35:03
 */
@Data
public class ParentMessage implements Serializable {

    private long date;
    private String fromUser;
    private String fromUserName;
    private String message;
}
