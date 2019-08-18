package laughing.my.dao.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laughing
 * @date 2016-3-2 下午08:33:16
 * @description 分页所需要参数
 */
public class PageParam {

    private int pageSize = 10;
    private long currentPage = 1;

    private boolean isAsc = true;
    private String orderBy = "id";

    private Map<String, Object> params = new HashMap<String, Object>();

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
