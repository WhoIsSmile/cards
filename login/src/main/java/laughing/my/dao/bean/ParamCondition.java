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
    private Object param;
    private ConditionEnum conditionEnum;

//    public String getCondition() {
//        if (conditionEnum.equals(ConditionEnum.EQUALS)) {
//            return "=?";
//        }
//    }

}
