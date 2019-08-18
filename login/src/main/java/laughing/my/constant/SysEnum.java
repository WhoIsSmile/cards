package laughing.my.constant;

/**
 * @author laughing
 * @create 2019-08-11 15:55:37
 * @desc
 **/
public enum  SysEnum {
    /**
     * 顶级-菜单
     */
    MENU_ROOT("0");
    private String value;

    SysEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
