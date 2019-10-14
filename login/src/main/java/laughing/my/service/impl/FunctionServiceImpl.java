package laughing.my.service.impl;

import laughing.my.dao.FunctionDao;
import laughing.my.entity.FunctionEntity;
import laughing.my.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 功能的增删改查
 *
 * @author huang.xiaolong
 * @create 2019-10-09 17:02:00
 * @desc
 **/
@Service
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionDao functionDao;

    @Override
    public List<FunctionEntity> findFunctionList() {
        return functionDao.findFunctionList();
    }

    @Override
    public void editFunction(FunctionEntity entity) {
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
        }
        functionDao.edit(entity);
    }

    @Override
    public void delete(long id) {
        functionDao.deleteById(id);
    }
}
