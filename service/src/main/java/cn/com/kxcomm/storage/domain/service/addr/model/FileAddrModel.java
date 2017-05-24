package cn.com.kxcomm.storage.domain.service.addr.model;

import lombok.Data;

import java.util.Date;

/**
 *
 * @class FileAddrModel
 * @author 徐琼
 * @create Date 2016年4月22日 下午3:31:36
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 地址模型
 */
@Data
public class FileAddrModel {

    // ID file_addr_id
    private Long id;
    // 归属集团编号 head_corp_id
    private Long headCorpId;
    // 文件ID file_id
    private Long fileId;
    // 存储地址ID storage_position_id
    private Long storagePositionId;
    // 服务器ID server_id
    private Long serverId;
    // 存储名称 file_addr_storage_name
    private String storageName;
    // 存储目录 file_addr_storage_dir
    private String storageDir;
    // 类型 file_addr_type
    private Integer type;
    // 地址 file_addr_url
    private String url;
    // 下载数 file_addr_download_count
    private Long downloadCount;
    // 创建时间 create_time
    private Date createTime;
    private Long createOper;
    // 修改时间 last_modify_time
    private Date lastModifyTime;
    private Long lastModifyOper;
    // 状态 file_addr_state
    private Integer state;
    // 备注 memo
    private String memo;

    private String path;

    public String getPath() {
        return storageDir + "/" + storageName;
    }
}
