package laughing.cards.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static laughing.cards.constant.CommonConstant.USER_NAME;

@RestController
@RequestMapping("u")
public class UserController {

    @RequestMapping("login")
    public Object login(HttpServletRequest request, HttpSession session){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        session.setAttribute(USER_NAME,userName);
        Map<String ,String> result = new HashMap<>();
        result.put("result","true");
        return result;
    }

}
