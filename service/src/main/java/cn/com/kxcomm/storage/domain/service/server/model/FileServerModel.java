package cn.com.kxcomm.storage.domain.service.server.model;

import lombok.Data;

import java.util.Date;

/**
 *
 * @class FileServerModel
 * @author 徐琼
 * @create Date 2016年4月22日 下午4:49:05
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 模型
 */
@Data
public class FileServerModel {
    // ID server_id
    private Long id;
    // 归属集团编号 head_corp_id
    private Long headCorpId;
    // name server_name
    private String name;
    // 区域ID area_id
    private Long areaId;
    // 区域编码 area_code
    private String areaCode;
    // 机房ID engine_room_id
    private Long engineRoomId;
    // 机房编码 engine_room_code
    private String engineRoomCode;
    // 机柜ID cabinets_id
    private Long cabinetsId;
    // 机柜编码 cabinets_code
    private String cabinetsCode;
    // IP server_ip
    private String ip;
    // 存储总数 server_storage_total
    private Long storageTotal;
    // 可用存储 server_storage_available
    private Long storageAvailable;
    // 已使用存储 server_storage_used
    private Long storageUsed;
    // 存储文件数 server_storage_file_count
    private Long storageFileCount;
    // 存储文件数上限 server_storage_file_count_uplimit
    private Long storageFileCountUplimit;
    // 文件大小上限 server_storage_file_uplimit
    private String storageFileUplimit;
    // 文件下载数 server_file_download_count
    private Long fileDownloadCount;
    // 文件下载大小 server_file_download_size
    private Long fileDownloadSize;
    // 创建人 create_oper
    private Long createOper;
    // 创建时间 create_time
    private Date createTime;
    // 修改人 last_modify_oper
    private Long lastModifyOper;
    // 修改时间 last_modify_time
    private Date lastModifyTime;
    // 状态 server_state
    private Integer state;
    // 排序 show_order
    private Long showOrder;
    // 备注 memo
    private String memo;
}