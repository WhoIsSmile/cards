package laughing.cards.config;

import laughing.cards.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author laughing
 * @date 2018-01-11 13:58:42
 */
@Slf4j
public class CardWebSocketHander implements WebSocketHandler {

    /**
     * 单机还可以，多机怎么办
     */
    private static final ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<>();

    /**
     * client第一次连接服务端时执行
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("连接成功......");
        String userName = (String) session.getAttributes().get(CommonConstant.USER_NAME);
        if (StringUtils.isNotBlank(userName)) {
            log.info("userName= {}", userName);
            users.put(userName, session);
        }
    }

    /**
     * 接受客户端的请求处理，这里可以指定方法是给所以在线的client推送消息还是给某个特定的client推送(区别:方法的参数列表)
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
//        sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
//        webSocketSession.sendMessage(webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        log.info("连接出错，关闭连接......");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("连接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
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
     * @param userName
     */
    public void sendMessageToUsers(TextMessage message, String userName) {
        WebSocketSession user = users.get(userName);
        try {
            if (user.isOpen()) {
                System.out.println("msg=" + getTime());
                user.sendMessage(getTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统当前时间并转换为TextMessage
     *
     * @return
     */
    public TextMessage getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        CharBuffer temp = CharBuffer.wrap("系统当前时间：" + time);
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
        WebSocketSession user = users.get(userName);
        try {
            if (user.isOpen()) {
                user.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
