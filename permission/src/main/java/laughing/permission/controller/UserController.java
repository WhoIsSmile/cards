package laughing.permission.controller;

import laughing.permission.controller.dto.UserInfoParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laughing
 * @create 2018-06-27 09:05:09
 * @desc 用户相关控制器
 **/
@RestController
public class UserController {


    @RequestMapping(value = "user/ccc")
    public Object ccc() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "ccc");
        map.put("msg", "ccc");
        return map;
    }
}

