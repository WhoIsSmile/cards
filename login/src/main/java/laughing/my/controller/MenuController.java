package laughing.my.controller;

import laughing.my.dao.MenuDao;
import laughing.my.entity.MenuEntity;
import laughing.my.service.MenuService;
import laughing.my.service.dto.UserMenuDTO;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private MenuDao menuDao;

    /**
     * 菜单列表
     *
     * @param request
     * @return
     */
    @RequestMapping("list")
    public RsResult findMenuList(HttpServletRequest request) {
        List<UserMenuDTO> list = menuService.findMenuList();
        return new RsResult(ErrorEnum.SUCCESS, list);
    }

    /**
     * 删除
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("del")
    public RsResult del(HttpServletRequest request, @RequestParam("id") Long id) {
        menuService.delete(id);
        return new RsResult(ErrorEnum.SUCCESS);
    }

    /**
     * 编辑()
     *
     * @return
     */
    @RequestMapping("edit")
    public RsResult edit(@RequestBody MenuEntity menuEntity) {
        menuService.editMenu(menuEntity);
        return new RsResult(ErrorEnum.SUCCESS);
    }

    @RequestMapping("test")
    public RsResult editMenu() {
        List<MenuEntity> list = menuDao.findMenuList();
        return new RsResult(ErrorEnum.SUCCESS, list);
    }

}
