package laughing.my.controller.sys;

import laughing.my.entity.FunctionEntity;
import laughing.my.service.FunctionService;
import laughing.utils.net.response.bean.RsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huang.xiaolong
 * @create 2019-10-09 16:53:02
 * @desc
 **/
@RestController
@RequestMapping("sys/func")
public class FunctionController {

    @Autowired
    public FunctionService functionService;

    @RequestMapping("list")
    public Object list() {
        List<FunctionEntity> result = functionService.findFunctionList();
        return result;
    }

    @RequestMapping("edit")
    public RsResult edit(@RequestBody FunctionEntity functionEntity) {
        functionService.editFunction(functionEntity);
        return new RsResult<>();
    }

    @RequestMapping("del")
    public RsResult delete(@RequestParam("id") Long id) {
        functionService.delete(id);
        return new RsResult<>();
    }

}
