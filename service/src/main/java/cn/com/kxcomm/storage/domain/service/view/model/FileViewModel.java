/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.storage.domain.service.view.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @class FileViewModel 
 * @author 徐琼
 * @create Date 2016年4月22日 下午5:51:49
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 视图模型
 */
@Data
public class FileViewModel {
	// ID file_view_id
	private Long id;
	// 归属集团编号 head_corp_id
	private Long headCorpId;
	// 名称 file_view_name
	private String name;
	// 文件ID file_id
	private Long fileId;
	// 编码 file_view_code
	private String code;
	// 系统编码 sys_code
	private String sysCode;
	// 活跃数 file_view_active_count
	private Long activeCount;
	// 存储数 file_view_storage_count
	private Long storageCount;
	// 用户ID user_id
	private String userId;
	// 创建人 create_oper
	private Long createOper;
	// 创建时间 create_time
	private Date createTime;
	private Long lastModifyOper;
	// 最后修改时间 last_modify_time
	private Date lastModifyTime;
	// 状态 file_view_state
	private Integer state;

}