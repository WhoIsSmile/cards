package laughing.cards.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WSController {


//    @MessageMapping("/welcome")
//    @SendTo("/topic/getResponse")
//    public ResponseMessage say(RequestMessage message) {
//        log.info(message.getName());
//        return new ResponseMessage("welcome," + message.getName() + " !");
//    }


}
