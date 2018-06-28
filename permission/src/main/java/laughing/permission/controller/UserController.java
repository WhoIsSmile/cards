package laughing.permission.controller;

import laughing.permission.controller.dto.UserInfoParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("admin:perms:add")
    public Object ccc() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "ccc");
        map.put("msg", "ccc");
        return map;
    }

    @RequestMapping(value = "user/bbb")
    @RequiresPermissions("admin:perms:ddd")
    public Object bbb() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "bb");
        map.put("msg", "bb");
        return map;
    }
    @RequestMapping(value = "user/ddd")
    public Object ddd() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "ddd");
        map.put("msg", "ddd");
        return map;
    }
}

