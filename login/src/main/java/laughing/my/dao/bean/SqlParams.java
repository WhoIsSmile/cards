package laughing.my.dao.bean;

/**
 * 
 * @author laughing
 * @date 2016-3-2 下午09:55:50 
 * @description 描述
 */
public class SqlParams {

	private String sql;
	private Object[] params;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
