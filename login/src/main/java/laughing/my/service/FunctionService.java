package laughing.my.service;

import laughing.my.entity.FunctionEntity;
import laughing.my.entity.MenuEntity;
import laughing.my.service.dto.UserMenuDTO;

import java.util.List;

/**
 * @author huang.xiaolong
 * @create 2019-10-09 17:00:15
 * @desc
 **/
public interface FunctionService {
    /**
     * 查询功能
     *
     * @return
     */
    public List<FunctionEntity> findFunctionList();

    /**
     * 编辑功能
     *
     * @param entity
     */
    public void editFunction(FunctionEntity entity);

    /**
     * 删除
     *
     * @param id
     */
    public void delete(long id);
}
