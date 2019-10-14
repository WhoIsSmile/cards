package laughing.my.controller.sys;

import laughing.my.controller.dto.UserInfoParams;
import laughing.my.entity.UserInfoEntity;
import laughing.my.service.UserInfoService;
import laughing.my.service.dto.MenuRouter;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laughing
 * @create 2019-08-05 16:58:18
 * @desc
 **/
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    public RsResult userLogin(@RequestBody UserInfoParams params) throws Exception {
        UserInfoEntity userInfo = userInfoService.userLogin(params.getUsername(), params.getPassword());
        String token = userInfoService.updateUserToken(userInfo.getId());
        userInfo.setToken(token);
        Map<String, Object> result = new HashMap<>(3);
        result.put("userInfo", userInfo);
        return new RsResult(ErrorEnum.SUCCESS, result);
    }

    /**
     * 获取用户信息和权限
     *
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping("info")
    public RsResult getUserInfo(@RequestParam String token) throws Exception {
        UserInfoEntity userInfo = userInfoService.getUserInfoByToken(token);
        List<MenuRouter> menuList = userInfoService.findMenuRouters(String.valueOf(userInfo.getId()));
        List<String> funcList = userInfoService.findFuncActionByUserId(String.valueOf(userInfo.getId()));
        Map<String, Object> result = new HashMap<>(3);
        result.put("routers", menuList);
        result.put("func", funcList);
        result.put("userInfo", userInfo);
        return new RsResult(ErrorEnum.SUCCESS, result);
    }

    /**
     * 登出（带实现）
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public RsResult logout(HttpServletRequest request, HttpServletResponse response) {
        return new RsResult(ErrorEnum.SUCCESS);
    }
}
