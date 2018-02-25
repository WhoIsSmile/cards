package laughing.cards.config;

import com.alibaba.fastjson.JSON;
import laughing.cards.constant.CommonConstant;
import laughing.cards.controller.bean.RoomMessage;
import laughing.cards.dao.CardUserDao;
import laughing.cards.dao.ChatRoomDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.CharBuffer;


/**
 * @author laughing
 * @date 2018-01-11 13:58:42
 */
@Slf4j
public class CardWebSocketHander implements WebSocketHandler {

    /**
     * 单机还可以，多机怎么办
     */
//    private static final ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<>();

    /**
     * client第一次连接服务端时执行
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("连接成功......");
        String userName = getAttributeVal(session, CommonConstant.USER_NAME);
        String roomNum = getAttributeVal(session, CommonConstant.CHAT_ROOM);

        if (StringUtils.isNotBlank(userName)) {
            log.info("userName= {}", userName);
            ChatRoomDao.joinRoom(roomNum, userName, session);
        }
    }

    private String getAttributeVal(WebSocketSession session, String key) {
        Object value = session.getAttributes().get(key);
        return value == null ? "" : value.toString();
    }

    /**
     * 接受客户端的请求处理，这里可以指定方法是给所以在线的client推送消息还是给某个特定的client推送(区别:方法的参数列表)
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        String roomNum = getAttributeVal(session, CommonConstant.CHAT_ROOM);
        String userName = getAttributeVal(session, CommonConstant.USER_NAME);
        if (StringUtils.isNotBlank(roomNum) && StringUtils.isNotBlank(userName)) {
            RoomMessage message = fillRoomMessage(userName, webSocketMessage.getPayload().toString());
            sendMessageToUsers(toTextMessage(message), roomNum);
        }
    }

    /**
     * 填充 message对象
     *
     * @param userName
     * @param userMessage
     * @return
     */
    private RoomMessage fillRoomMessage(String userName, String userMessage) {
        RoomMessage message = new RoomMessage();
        message.setDate(System.currentTimeMillis());
        message.setFromUserName(userName);
        message.setMessage(userMessage);
        return message;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.info("连接出错，关闭连接......");
        closeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("连接关闭......" + closeStatus.toString());
        closeSession(webSocketSession);
    }

    private void closeSession(WebSocketSession webSocketSession) {
        String userName = getAttributeVal(webSocketSession, CommonConstant.USER_NAME);
        String roomNum = getAttributeVal(webSocketSession, CommonConstant.CHAT_ROOM);
        ChatRoomDao.leaveRoom(roomNum, userName);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线client发送消息
     * 这里的message是client推送给服务端的请求信息，这里假设服务器推     送系统当前时间给client，忽略client传过来的消息，当然你也可以自己处理这个message
     *
     * @param message
     * @param roomNum
     */
    public void sendMessageToUsers(TextMessage message, String roomNum) {
        Object[] users = ChatRoomDao.getRoomUserNames(roomNum);
        for (Object user : users) {
            WebSocketSession userSession = CardUserDao.getUserWebSession(user.toString());
            try {
                if (userSession.isOpen()) {
                    userSession.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转化成TextMessage
     *
     * @return
     */
    public TextMessage toTextMessage(RoomMessage roomMessage) {
        CharBuffer temp = CharBuffer.wrap(JSON.toJSONString(roomMessage));
        TextMessage msg = new TextMessage(temp);
        return msg;
    }

    /**
     * 给某个client发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
//        WebSocketSession user = users.get(userName);
//        try {
//            if (user.isOpen()) {
//                user.sendMessage(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
