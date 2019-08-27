package laughing.my.dao.bean;

import java.util.List;

@SuppressWarnings("unchecked")
public class ResultPage<T> {
	private int pageSize = 10;
	private long currentPage = 1;

	private long totalNum;
	private long totalPage;

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
		setTotalPage(totalNum % pageSize == 0 ? totalNum / pageSize : (totalNum
				/ pageSize + 1));
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}


	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	

}
