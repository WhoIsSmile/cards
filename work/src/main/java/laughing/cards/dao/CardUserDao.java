package laughing.cards.dao;

import laughing.cards.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laughing
 * @date 2018-01-21 13:48:42
 */
public class CardUserDao {
    private static ConcurrentHashMap<String, WebSocketSession> cardSession = new ConcurrentHashMap<>(100);

    /**
     * 用户上线
     *
     * @param userName
     * @param webSocketSession
     */
    public static void addUser(String userName, WebSocketSession webSocketSession) {
        cardSession.put(userName, webSocketSession);
    }

    /**
     * 获取用户WebSession
     *
     * @param userName
     * @return
     */
    public static WebSocketSession getUserWebSession(String userName) {
        return cardSession.get(userName);
    }

    /**
     * 用户下线
     *
     * @param userName
     */
    public static void removeUser(String userName) {
        WebSocketSession session = cardSession.get(userName);
        Object roomNumInfo = session.getAttributes().get(CommonConstant.CHAT_ROOM);
        String roomNum = roomNumInfo == null ? "" : roomNumInfo.toString();
        cardSession.remove(userName);
//        if (StringUtils.isNotEmpty(roomNum)) {
//            ChatRoomDao.leaveRoom(roomNum, userName);
//        }
    }
}
