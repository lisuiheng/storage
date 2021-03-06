/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.storage.domain.service.addr.model.query;

import java.util.ArrayList;
import java.util.List;

import cn.com.kxcomm.common.base.OrderField;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;

/**
 * @class FileAddrQuery 
 * @author 徐琼
 * @create Date 2016年03月29日 14:20:30
 * @modified By 
 * @modified Date 
 * @why & what 
 * @since JDK1.7
 * @version 001.00.00
 * @description 条件查询
 */
public class FileAddrQuery extends FileAddrModel {

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
	
	// 文件ID集合
	private List<Long> fileIdList;
	
	
	/**
	 * @getter getPageSize 
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @setter setPageSize 
	 */
	public FileAddrQuery setPageSize(int pageSize) {
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
	/**
	 * @getter getFileIdList 
	 */
	public List<Long> getFileIdList() {
		return fileIdList;
	}
	/**
	 * @setter setFileIdList 
	 */
	public void setFileIdList(List<Long> fileIdList) {
		this.fileIdList = fileIdList;
	}
}
