package laughing.my.constant;

/**
 * @author laughing
 * @create 2019-08-29 20:01:01
 * @desc
 **/
public enum ConditionEnum {
    //equals
    EQUALS("= ?"),
    /**
     * in
     */
    LESS_THAN("< ?"),
    LESS_THAN_EQUALS("<= ?"),
    GRENTER_THAN("> ?"),
    GRENTER_THAN_EQUALS(">= ?"),
    /**
     * like
     */
    LIKE("like concat('%',?,'%')"),
    /**
     * left like  "like %XXX"
     */
    LEFT_LIKE("like concat('%',?)"),
    /**
     * right like  "like XXX%"
     */
    RIGHT_LIKE("like concat('?,'%')");

    private String condition;

    ConditionEnum(String condition) {
        this.condition = condition;
    }
}
