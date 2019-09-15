package laughing.my.dao.bean;

import laughing.my.constant.ConditionEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laughing
 * @date 2016-3-2 下午08:33:16
 * @description 分页所需要参数 怎么加强这个方法？不需要手动拼接参数去查询？
 */
@Data
public class PageParam {

    private int pageSize = 10;
    private long currentPage = 1;

    private boolean isAsc = true;
    private String orderBy = "id";
    //    private List<String>
    /**
     * 字段条件 暂不支持一个字段多个条件
     */
    private Map<String, ConditionEnum> conditionEnumMap = new HashMap<>();
    private Map<String, Object> params = new HashMap<String, Object>();


}
