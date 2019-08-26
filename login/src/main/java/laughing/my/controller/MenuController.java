package laughing.my.controller;

import laughing.my.service.MenuService;
import laughing.my.service.dto.UserMenuDTO;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laughing
 * @create 2019-08-19 16:23:44
 * @desc 菜单相关借口
 **/
@RestController
@RequestMapping("sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("list")
    public RsResult findMenuList(HttpServletRequest request) {
        List<UserMenuDTO> list = menuService.findMenuList();
        return new RsResult(ErrorEnum.SUCCESS, list);
    }

//    public RsResult editMenu(){
//
//    }

}
