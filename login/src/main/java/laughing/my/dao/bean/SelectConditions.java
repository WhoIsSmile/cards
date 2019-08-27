package laughing.my.dao.bean;

import lombok.Data;

import java.util.List;

/**
 * @author huang.xiaolong
 * @create 2019-08-27 19:20:30
 * @desc
 **/
@Data
public class SelectConditions {
    String condition;
    List<Object> params;
}
