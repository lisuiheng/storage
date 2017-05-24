/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.storage.domain.service.file.model;

import lombok.Data;

import java.util.Date;

/**
 *
 * @class FileModel
 * @author 徐琼
 * @create Date 2016年4月22日 下午3:47:57
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 文件模型
 */
@Data
public class FileModel {
    // ID file_id
    private Long id;
    // 归属集团比那好 head_corp_id
    private Long headCorpId;
    // MD5 file_md5
    private String md5;
    // 大小 file_size
    private Long size;
    // 后缀名 file_postfix
    private String postfix;
    // 存储数 file_storage_count
    private Long storageCount;
    // 活跃数 file_active_count
    private Long activeCount;
    // 视图数 file_view_count
    private Long viewCount;
    // 创建人 create_oper
    private Long createOper;
    // 创建时间 create_time
    private Date createTime;
    // 修改时间 last_modify_time
    private Date lastModifyTime;
    private Long lastModifyOper;
    // 状态 file_state
    private Integer state;
}