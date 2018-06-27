package laughing.permission.controller;

import laughing.permission.controller.dto.UserInfoParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laughing
 * @create 2018-06-27 16:45:05
 * @desc 系统
 **/
@RestController
public class SysController {
    @RequestMapping(value = "/unAuth")
    public Object unAuth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }

    @RequestMapping(value = "/login")
    public Object ajaxLogin(UserInfoParams userInfoParams) {
        Map<String, String> map = new HashMap<>(2);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfoParams.getUserName(), userInfoParams.getPassword());
        subject.login(token);
        map.put("result", "success");
        return map;
    }
}
