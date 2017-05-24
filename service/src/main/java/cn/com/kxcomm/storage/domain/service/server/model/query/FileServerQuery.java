package cn.com.kxcomm.storage.domain.service.server.model.query;


import cn.com.kxcomm.common.base.OrderField;
import cn.com.kxcomm.storage.domain.service.server.model.FileServerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @class FileServerQuery
 * @author 徐琼
 * @create Date 2016年03月29日 14:20:32
 * @modified By
 * @modified Date
 * @why & what
 * @since JDK1.7
 * @version 001.00.00
 * @description 条件查询
 */
public class FileServerQuery extends FileServerModel {

    // 序列化
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    // 每页数
    protected int pageSize;
    // 起始行
    protected int startRow;
    // 页码
    protected int pageNo = 1;

    // ID集合
    private List<Long> idList = null;

    // Sql查询字段
    protected String fields;

    // 排序列表
    protected List<OrderField> orderFields = new ArrayList<OrderField>();

    // 文件数量小于最高上限
    private boolean isLessThanFileCountUplimit;
    // 文件存储小于最高上限
    private boolean isLessThanAvailable;

    /**
     * @getter getPageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @setter setPageSize
     */
    public FileServerQuery setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.startRow = (pageNo - 1) * this.pageSize;
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
        this.startRow = (pageNo - 1) * this.pageSize;
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
     * @getter getIsLessThanFileCountUplimit
     */
    public Boolean getIsLessThanFileCountUplimit() {
        return isLessThanFileCountUplimit;
    }

    /**
     * @setter setIsLessThanFileCountUplimit
     */
    public void setIsLessThanFileCountUplimit(Boolean isLessThanFileCountUplimit) {
        this.isLessThanFileCountUplimit = isLessThanFileCountUplimit;
    }

    /**
     * @getter getIsLessThanAvailable
     */
    public Boolean getIsLessThanAvailable() {
        return isLessThanAvailable;
    }

    /**
     * @setter setIsLessThanAvailable
     */
    public void setIsLessThanAvailable(Boolean isLessThanAvailable) {
        this.isLessThanAvailable = isLessThanAvailable;
    }

    /**
     * @getter getIdList
     */
    public List<Long> getIdList() {
        return idList;
    }

    /**
     * @setter setIdList
     */
    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

}