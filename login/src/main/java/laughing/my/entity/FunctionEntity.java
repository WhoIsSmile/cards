package laughing.my.entity;

import lombok.Data;

/**
 * @author laughing
 * @create 2018-04-01 19:01:05
 * @desc 功能表
 **/
@Data
public class FunctionEntity extends BaseEntity {
    /**
     * 功能名称
     */
    private String funcName;
    /**
     * 功能code
     */
    private String funcCode;

}
