package laughing.my.entity;

import lombok.Data;

import javax.persistence.Column;
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
    @Column(name="id")
    private Integer id;
 
    /**
     * 创建时间
     */
    @Column(name="createTime")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name="updateTime")
    private Date updateTime;
}
