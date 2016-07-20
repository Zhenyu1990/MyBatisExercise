package com.imooc.entity;

public class Page {

	private int totalNumber; // ������
	private int currentPage; // ��ǰ�ڼ�ҳ
	private int totalPage; // ��ҳ��
	private int pageNumber = 5; // ÿҳ��ʾ����
	private int dbIndex; // ���ݿ���limit�Ĳ���, �ӵڼ�����ʼȡ
	private int dbNumber; // ���ݿ���limit�Ĳ���, һ������������
	
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
	 * ���ݵ�ǰ���������ֵ���㲢������ص�����ֵ
	 */
	public void count() {
		// ������ҳ��
		// ��������������ÿҳ����
		int totalPageTemp = this.totalNumber / this.pageNumber;
		// ���������Ƿ�Ϊ0, ��Ϊ������Ҫ���һҳ
		int plus = (this.totalNumber % this.pageNumber) == 0 ? 0 : 1;
		totalPageTemp = totalPageTemp + plus;
		// ��֤������1ҳ
		if(totalPageTemp <= 0) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		
		// ���õ�ǰҳ��
		// ��ǰҳ����ܳ�����ҳ��
		if(this.totalPage < this.currentPage) {
			this.currentPage = this.totalPage;
		}
		// ��ǰҳ��СΪ1
		if(this.currentPage < 1) {
			this.currentPage = 1;
		}
		
		// ����limit����
		this.dbIndex = (this.currentPage - 1) * this.pageNumber;
		this.dbNumber = this.pageNumber;
	}
	
}
