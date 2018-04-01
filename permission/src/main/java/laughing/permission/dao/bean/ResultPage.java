package laughing.permission.dao.bean;

import java.util.List;

@SuppressWarnings("unchecked")
public class ResultPage<T> {
	private int pageSize = 10;
	private long currentPage = 1;

	private long totalNum;
	private long tatalPage;

	List<T> result;

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

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
		setTatalPage(totalNum % pageSize == 0 ? totalNum / pageSize : (totalNum
				/ pageSize + 1));
	}

	public long getTatalPage() {
		return tatalPage;
	}

	public void setTatalPage(long tatalPage) {
		this.tatalPage = tatalPage;
	}


	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	

}
