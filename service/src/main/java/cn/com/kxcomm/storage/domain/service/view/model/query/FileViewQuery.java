/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.storage.domain.service.view.model.query;

import cn.com.kxcomm.common.base.OrderField;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @class FileViewQuery 
 * @author 徐琼
 * @create Date 2016年03月29日 14:24:44
 * @modified By 
 * @modified Date 
 * @why & what 
 * @since JDK1.7
 * @version 001.00.00
 * @description 
 */
public class FileViewQuery extends FileViewModel {

	// 序列化
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	// 每页数
	protected int pageSize;
	// 起始行
	protected int startRow;
	// 页码
	protected int pageNo = 1;
	
	// Sql查询字段
	protected String fields;
	
	// 排序列表 
	protected List<OrderField> orderFields = new ArrayList<OrderField>();
	
	/**
	 * @getter getPageSize 
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @setter setPageSize 
	 */
	public FileViewQuery setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.startRow = (pageNo-1)*this.pageSize;
		return this;
	}
	/**
	 * @getter getStartRow 
	 */
	public int getStartRow() {
		return startRow;
	}
	/**
	 * @setter setStartRow 
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/**
	 * @getter getPageNo 
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @setter setPageNo 
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		this.startRow = (pageNo-1)*this.pageSize;
	}
	/**
	 * @getter getFields 
	 */
	public String getFields() {
		return fields;
	}
	/**
	 * @setter setFields 
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}
	/**
	 * @getter getOrderFields 
	 */
	public List<OrderField> getOrderFields() {
		return orderFields;
	}
}
