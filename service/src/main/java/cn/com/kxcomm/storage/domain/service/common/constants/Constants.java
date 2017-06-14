package cn.com.kxcomm.storage.domain.service.common.constants;

import cn.com.kxcomm.common.constants.DataConstants;

public interface Constants {
    // 地址类型--socket
    public final static Integer ADDR_TYPE_SOCKET = 1;
    // 地址类型--webservice
    public final static Integer ADDR_TYPE_WEBSERVICE = 2;

    Integer STATE_DELETE = DataConstants.STATE_DELETE_OF_DEFAULT_DATA;
    Integer STATE_NORMAL = DataConstants.STATE_NORMAL_OF_DEFAULT_DATA;
    String  SEQUENCE_ID_CODE_FILE_ADDR = "";
    String  SEQUENCE_ID_CODE_FILE = "";
    String  SEQUENCE_ID_CODE_FILE_SERVER = "";
    String  SEQUENCE_ID_CODE_FILE_VIEW = "";
    String  SEQUENCE_ID_CODE_FILE_STORAGE = "";

    String  SEQUENCE_ID_CODE_FILE_VIEW_CODE = "";

    /**
     * sqlsession key
     */
    String SQL_KEY_BASE_FILE_ADDR = "cn.com.kxcomm.storage.domain.service.addr.dao.FileAddrDao.";
    String SQL_KEY_BASE_FILE = "cn.com.kxcomm.storage.domain.service.file.dao.FileDao.";
    String SQL_KEY_BASE_FILE_SERVER = "cn.com.kxcomm.storage.domain.service.server.dao.FileServerDao.";
    String SQL_KEY_BASE_FILE_STORAGE = "cn.com.kxcomm.storage.domain.service.storage.dao.FileStoragePrositionDao.";
    String SQL_KEY_BASE_FILE_VIEW = "cn.com.kxcomm.storage.domain.service.view.dao.FileViewDao.";

    String SQL_KEY_GET_LIST = "getList";
    String SQL_KEY_ADD = "add";
    String SQL_KEY_UPDATE = "updateByKey";

    String SQL_KEY_GET_LIST_FILE_ADDR = SQL_KEY_BASE_FILE_ADDR +  SQL_KEY_GET_LIST;
    String SQL_KEY_ADD_FILE_ADDR = SQL_KEY_BASE_FILE_ADDR + SQL_KEY_ADD;
    String SQL_KEY_UPDATE_FILE_ADDR = SQL_KEY_BASE_FILE_ADDR + SQL_KEY_UPDATE;

    String SQL_KEY_GET_LIST_FILE = SQL_KEY_BASE_FILE + SQL_KEY_GET_LIST;
    String SQL_KEY_ADD_FILE = SQL_KEY_BASE_FILE + SQL_KEY_ADD;
    String SQL_KEY_UPDATE_FILE = SQL_KEY_BASE_FILE + SQL_KEY_UPDATE;

    String SQL_KEY_GET_LIST_FILE_SERVER = SQL_KEY_BASE_FILE_SERVER +  SQL_KEY_GET_LIST;
    String SQL_KEY_ADD_FILE_SERVER = SQL_KEY_BASE_FILE_SERVER + SQL_KEY_ADD;
    String SQL_KEY_UPDATE_FILE_SERVER = SQL_KEY_BASE_FILE_SERVER + SQL_KEY_UPDATE;

    String SQL_KEY_GET_LIST_FILE_STORAGE = SQL_KEY_BASE_FILE_STORAGE +  SQL_KEY_GET_LIST;
    String SQL_KEY_ADD_FILE_STORAGE = SQL_KEY_BASE_FILE_STORAGE + SQL_KEY_ADD;
    String SQL_KEY_UPDATE_FILE_STORAGE = SQL_KEY_BASE_FILE_STORAGE + SQL_KEY_UPDATE;

    String SQL_KEY_GET_LIST_FILE_VIEW = SQL_KEY_BASE_FILE_VIEW +  SQL_KEY_GET_LIST;
    String SQL_KEY_ADD_FILE_VIEW = SQL_KEY_BASE_FILE_VIEW + SQL_KEY_ADD;
    String SQL_KEY_UPDATE_FILE_VIEW = SQL_KEY_BASE_FILE_VIEW + SQL_KEY_UPDATE;

}
