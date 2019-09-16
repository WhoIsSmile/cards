package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author laughing
 * @create 2018-04-01 19:01:05
 * @desc 功能表
 **/
@Data
@Table(name = "sys_func")
public class FunctionEntity extends BaseEntity {
    /**
     * 功能名称
     */
    @Column(name = "func_name")
    private String funcName;
    /**
     * 功能code
     */
    @Column(name = "func_code")
    private String funcCode;

}
