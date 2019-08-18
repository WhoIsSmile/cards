package laughing.my.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author laughing
 * @create 2018-04-01 14:58:41
 * @desc 基本的表字段，id ，updateTime，createTime等
 **/
@Data
public class BaseEntity {
    /**
     * id 主键
     */
    private int id;
 
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
