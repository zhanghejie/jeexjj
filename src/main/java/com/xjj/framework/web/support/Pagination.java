package com.xjj.framework.web.support;

import java.util.Collection;
import java.util.Collections;

import com.xjj.framework.configuration.ConfigUtils;


public class Pagination {
		
	private static int[] pageSizeList = {5,10,20,30,40,50,100,200,500,1000};
	
	    private int pageSize = -1;
	    private int pageShow = -1;
	    
	    private int totalPage = 1;
	    private int totalRecord = 0;
	    private int currentPage = 1;
		@SuppressWarnings("rawtypes")
		private Collection items;

		public Pagination() { }
	    public Pagination(int totalRecord) {
	    	this(null,totalRecord,1);
	    }
	    public Pagination(int totalRecord, int currentPage) {
	    	this(null,totalRecord,currentPage);
	    }
	    public Pagination(Collection<Object> items,int totalRecord, int currentPage) {
			setItems(items);
			setTotalRecord(totalRecord);
			setCurrentPage(currentPage);
		}

    
	    /*
	     * ###########################################################
	     * 	设置
	     * ###########################################################
	     */
	    
		/**
		 * @param currentPage 当前页
		 */
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		/**
		 * @param totalRecord 总记录数
		 */
		public void setTotalRecord(int totalRecord) {
			this.totalRecord = totalRecord < 0 ? 0 : totalRecord;
			initPage();
		}
		
		/**
		 * @param pageSize 璁剧疆椤甸潰鍖呭惈鐨勮褰曟暟銆?
		 */
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize < 0 ? 0 : pageSize;
			initPage();
		}
		
		/**
		 * 设定分页列表中显示的数量
		 * @param pageShow
		 */
		public void setPageShow(int pageShow) {
			this.pageShow = pageShow;
		}
		
		private void initPage(){
			if(getPageSize() == 0){
				totalPage = 1;
			}else{
				if(getTotalRecord() == 0){
					totalPage = 1;
				}else{
					totalPage = getTotalRecord() / getPageSize();
				}
				if(getTotalRecord() > 0 && ((getTotalRecord() % getPageSize()) != 0)){
					totalPage = totalPage + 1;
				}
			}
		}


		public int[] getPageSizeList(){
			return pageSizeList;
		}
	    /*
	     * ###########################################################
	     * 	杩斿洖鍙傛暟
	     * ###########################################################
	     */


		/**
		 * @return 总页数
		 */
		public int getTotalPage() {
			return totalPage;
		}

		/**
		 * @return 总记录数
		 */
		public int getTotalRecord() {
			return totalRecord;
		}

		/**
		 * @return 当前页面
		 * 如果小于0则返回最后一页
		 */
		public int getCurrentPage() {
			if(currentPage < 0 || currentPage > getTotalPage()){
				return getTotalPage();
			}else if(currentPage == 0){
				return 1;
			}else{
				return currentPage;
			}
		}
		
		/**
		 * @return 每页的记录数
		 */
		public int getPageSize() {
			if(pageSize<0){
				pageSize = ConfigUtils.getInt("page.PAGE_SIZE",10);
			}
			return pageSize;
		}

		/**
		 * @return 分页列表显示的页面数量
		 */
		public int getPageShow() {
			if(pageShow<0){
				pageShow = ConfigUtils.getInt("page.PAGE_SHOW",10);
			}
			return pageShow;
		}
		
		/**
		 * @return 下一页
		 */
		public int getNextPage(){
			return isLast() ? getTotalPage() : getCurrentPage()+1;
		}

		/**
		 * @return 上一页
		 */
		public int getPrevPage(){
			return isFirst() ? 1 : getCurrentPage()-1;
		}
		
		/**
		 * @return 当前页面的最后一条记录
		 */
		public int getLastRecord(){
			return isLast() ? getTotalRecord() : getCurrentPage() * getPageSize();
		}
		
		/**
		 * @return 当前页面的第一条记录
		 */
		public int getStartRecord(){
			return isFirst() ? 1 : (getCurrentPage()-1) * getPageSize()+1;
		}
		
		/**
		 * @return 是否是首页
		 */
		public boolean isFirst() {
			return getCurrentPage() <= 1;
		}

		/**
		 * @return 是否是尾页
		 */
		public boolean isLast() {
			return getCurrentPage() >= getTotalPage();
		}

		/**
		 * @return 所有页面的索引值
		 */
		public int[] getPageIndexs(){
			int[] pageList = new int[getTotalPage()];
			for(int i=0;i<getTotalPage();i++){
				pageList[i] = i+1;
			}
			return pageList;
		}

		/**
		 * @return 当局部显示页面索引时，能够显示的页面索引
		 */
	    public int[] getShowPageIndexs() {
	    	//设定起始页
	    	int firstShowPage = getCurrentPage() - getPageShow() / 2;
	    	if(firstShowPage <= 0){
	    		firstShowPage = 1;
	    	}
	    	//设定终止页
        	int endShowPage = firstShowPage + getPageShow();
        	if(endShowPage > getTotalPage()){
        		endShowPage = getTotalPage();
        		if(endShowPage-pageShow>0 && endShowPage-getPageShow()<firstShowPage){
        			firstShowPage = endShowPage-getPageShow();
        		}
        	}

        	int showPageIndexs[] = new int[endShowPage-firstShowPage+1];
        	int index = 0;
        	for(int showPageIndex = firstShowPage; showPageIndex <= endShowPage; showPageIndex++){
        		showPageIndexs[index] = showPageIndex;
        		index++;
        	}
	        return showPageIndexs;
	    }
		
	    /**
	     * @return 当前显示的页面索引中是否显示了首页
	     */
	    public boolean isShowFirst(){
	    	if(getCurrentPage() - getPageShow()/2 > 0){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * @return 当前显示的页面索引中是否显示了尾页
	     */
	    public boolean isShowLast(){
	    	if(getCurrentPage() + getPageShow()/2 < getTotalPage()){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * @return 页面包含的数据列表
	     */
		@SuppressWarnings("rawtypes")
		public Collection getItems() {
			return items == null ? Collections.EMPTY_LIST : items;
		}
		
		/**
		 * 设置页面包含的数据列表
		 * @param items
		 */
		public void setItems(@SuppressWarnings("rawtypes") Collection items) {
			this.items = items;
		}

		
}
