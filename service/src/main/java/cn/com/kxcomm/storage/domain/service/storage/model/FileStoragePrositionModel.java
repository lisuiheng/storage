/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.storage.domain.service.storage.model;

import lombok.Data;

import java.net.SocketAddress;
import java.util.Date;

/**
 * 
 * @class FileStoragePrositionModel
 * @author 徐琼
 * @create Date 2016年4月22日 下午5:33:31
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 存储位置模型
 */
@Data
public class FileStoragePrositionModel {
	// ID storage_prosition_id
	private Long id;
	// 归属集团编号 head_corp_id
	private Long headCorpId;
	// 服务器ID server_id
	private Long serverId;
	// 名称 storage_prosition_name
	private String name;
	// 类型 storage_prosition_type
	private Integer type;
	// 端口 storage_prosition_port
	private Integer port;
	// 地址类型 storage_prosition_addr_type
	private Integer addrType;
	// 地址url storage_prosition_addr_url
	private String addrUrl;
	// 总存储 storage_prosition_total
	private Long total;
	// 可用存储 storage_prosition_available
	private Long available;
	// 已使用存储 storage_prosition_used
	private Long used;
	// 文件数 storage_prosition_file_count
	private Long fileCount;
	// 文件数上限 storage_prosition_file_count_uplimit
	private Long fileCountUplimit;
	// 文件大小上限 storage_prosition_file_uplimit
	private Long fileUplimit;
	// 下载数 storage_prosition_download_count
	private Long downloadCount;
	// 下载大小 storage_prosition_download_size
	private Long downloadSize;
	// 创建人 create_oper
	private Long createOper;
	// 创建时间 create_time
	private Date createTime;
	// 修改人 last_modify_oper
	private Long lastModifyOper;
	// 修改时间 last_modify_time
	private Date lastModifyTime;
	// 状态 storage_prosition_state
	private Integer state;
	// 排序 show_order
	private Long showOrder;
	// 备注 memo
	private String memo;


}