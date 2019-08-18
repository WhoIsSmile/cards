package laughing.my.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laughing
 * @create 2019-08-11 14:49:25
 * @desc
 **/
@Data
public class MenuRouter {

    private String path;
    private String component;
    private String name;
    private String permission;
    private int sort;
    private Meta meta;
    private List children = new ArrayList(8);
}
