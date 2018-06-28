package laughing.permission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import laughing.permission.controller.dto.UserInfoParams;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
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
@Api(value = "系统自带API", description = "系统自带API")
@RestController
@Slf4j
public class SysController {
    @RequestMapping(value = "/unAuth")
    public Object unAuth() {
        log.info("--登录过期--");
        return new RsResult(ErrorEnum.LOGIN_EXPIRE);
    }

    @RequestMapping(value = "/login")
    @ApiOperation(value = "login用户登录", notes = "用户名，密码")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "userName", value = "账户", required = true, dataType = "STRING", paramType = "query"),
             @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "STRING", paramType = "query")})
    public Object ajaxLogin(UserInfoParams userInfoParams) {
        Map<String, String> map = new HashMap<>(2);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfoParams.getUserName(), userInfoParams.getPassword());
        subject.login(token);
        return new RsResult(ErrorEnum.SUCCESS);
    }
    @RequestMapping(value = "/403")
    public Object forbit() {
        log.info("--登录过期--");
        return new RsResult(ErrorEnum.UNAUTH);
    }

}
