package com.imooc.entity;

public class Page {

	private int totalNumber; // 总条数
	private int currentPage; // 当前第几页
	private int totalPage; // 总页数
	private int pageNumber = 5; // 每页显示条数
	private int dbIndex; // 数据库中limit的参数, 从第几条开始取
	private int dbNumber; // 数据库中limit的参数, 一共多少条数据
	
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
		this.count();
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		this.count();
	}
	public int getDbIndex() {
		return dbIndex;
	}
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	public int getDbNumber() {
		return dbNumber;
	}
	public void setDbNumber(int dbNumber) {
		this.dbNumber = dbNumber;
	}
	
	/**
	 * 根据当前对象的属性值计算并设置相关的属性值
	 */
	public void count() {
		// 计算总页数
		// 先用总条数除以每页条数
		int totalPageTemp = this.totalNumber / this.pageNumber;
		// 计算余数是否为0, 不为零则需要多加一页
		int plus = (this.totalNumber % this.pageNumber) == 0 ? 0 : 1;
		totalPageTemp = totalPageTemp + plus;
		// 保证至少有1页
		if(totalPageTemp <= 0) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		
		// 设置当前页数
		// 当前页最大不能超过总页数
		if(this.totalPage < this.currentPage) {
			this.currentPage = this.totalPage;
		}
		// 当前页最小为1
		if(this.currentPage < 1) {
			this.currentPage = 1;
		}
		
		// 设置limit参数
		this.dbIndex = (this.currentPage - 1) * this.pageNumber;
		this.dbNumber = this.pageNumber;
	}
	
}
