package laughing.cards.dao;

import javax.smartcardio.CardException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天室
 *
 * @author laughing
 * @date 2018-01-16 09:48:58
 */
public class ChatRoomDao {

    private static Map<String, Vector<String>> chatRooms;

    static {
        chatRooms = new HashMap<>(6);
        chatRooms.put("room1", new Vector<>(100));
        chatRooms.put("room2", new Vector<>(100));
    }
//    private ConcurrentHashMap<String, String> chatRooms = new ConcurrentHashMap<>();

    public static void joinRoom(String roomNum, String userName) {
        Vector<String> roomMember = chatRooms.get(roomNum);
        if (null == roomMember) {
//            throw new CardException();
        }
        roomMember.add(userName);
    }

    public static void leaveRoom(String roomNum, String userName) {
        Vector<String> roomMember = chatRooms.get(roomNum);
        if (null == roomMember) {
//            throw new CardException();
        }
        roomMember.remove(userName);
    }


    public static void main(String[] args) {
        Vector<String> strings = new Vector<>();
        strings.add("ssssss");
        strings.add("aaaa");
        strings.add("bbbbb");
        System.out.println(strings.size());
        strings.remove("bbbbb");
        System.out.println(strings.size());
//        chatRooms.put("room1","");

    }

}
