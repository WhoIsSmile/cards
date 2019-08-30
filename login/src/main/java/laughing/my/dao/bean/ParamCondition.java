package laughing.my.dao.bean;

import laughing.my.constant.ConditionEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author laughing
 * @create 2019-08-29 19:58:18
 * @desc
 **/
@Slf4j
@Data
public class ParamCondition {
    /**
     * 参数具体的值
     */
    private Object param;
    /**
     * 默认是equals
     */
    private ConditionEnum conditionEnum = ConditionEnum.EQUALS;

//    public String getCondition() {
//        if (conditionEnum.equals(ConditionEnum.EQUALS)) {
//            return "=?";
//        }
//    }

}
