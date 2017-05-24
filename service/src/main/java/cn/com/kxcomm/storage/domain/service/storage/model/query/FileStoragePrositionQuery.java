package cn.com.kxcomm.storage.domain.service.storage.model.query;

import cn.com.kxcomm.common.base.OrderField;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @class FileStoragePrositionQuery
 * @author 徐琼
 * @create Date 2016年03月29日 17:06:03
 * @modified By
 * @modified Date
 * @why & what
 * @since JDK1.7
 * @version 001.00.00
 * @description 条件查询
 */
public class FileStoragePrositionQuery extends FileStoragePrositionModel {

    // 序列化
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    // 每页数
    protected int pageSize;
    // 起始行
    protected int startRow;
    // 页码
    protected int pageNo = 1;

    private List<Long> idList;
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
    public FileStoragePrositionQuery setPageSize(int pageSize) {
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
     * @getter isLessThanFileCountUplimit
     */
    public boolean getIsLessThanFileCountUplimit() {
        return isLessThanFileCountUplimit;
    }
    /**
     * @setter setLessThanFileCountUplimit
     */
    public void setIsLessThanFileCountUplimit(boolean isLessThanFileCountUplimit) {
        this.isLessThanFileCountUplimit = isLessThanFileCountUplimit;
    }
    /**
     * @getter isLessThanAvailable
     */
    public boolean getIsLessThanAvailable() {
        return isLessThanAvailable;
    }
    /**
     * @setter setLessThanAvailable
     */
    public void setIsLessThanavailable(boolean isLessThanAvailable) {
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
