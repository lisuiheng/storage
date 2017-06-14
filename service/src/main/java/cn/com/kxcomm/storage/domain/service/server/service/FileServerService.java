package cn.com.kxcomm.storage.domain.service.server.service;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.file.model.query.FileQuery;
import cn.com.kxcomm.storage.domain.service.server.dao.FileServerDao;
import cn.com.kxcomm.storage.domain.service.server.model.FileServerModel;
import cn.com.kxcomm.storage.domain.service.server.model.query.FileServerQuery;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.model.query.FileStoragePrositionQuery;
import cn.com.kxcomm.storage.domain.service.storage.service.FileStoragePrositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.*;
import java.util.*;

@Service
public class FileServerService {
    private final FileServerDao fileServerDao;
    private final FileStoragePrositionService storagePrositionService;

    @Autowired
    public FileServerService(FileServerDao fileServerDao, FileStoragePrositionService storagePrositionService) {
        this.fileServerDao = fileServerDao;
        this.storagePrositionService = storagePrositionService;
    }

    public Set<SocketAddress> getAdress(long heaCorpId) {
        return getAdressMap(heaCorpId).keySet();
    }

    public Map<Long, InetSocketAddress> getStorageAddressMap(long headCorpId) {
        FileServerQuery fileServerQuery = new FileServerQuery();
        List<FileServerModel> fileServerModels = getList(fileServerQuery, headCorpId);

        Map<Long, InetSocketAddress>  storageAddressMap = new HashMap<>();
        fileServerModels.forEach((server) -> {
            FileStoragePrositionQuery storagePrositionQuery = new FileStoragePrositionQuery();
            storagePrositionQuery.setServerId(server.getId());
            storagePrositionQuery.setAddrType(Constants.ADDR_TYPE_SOCKET);
            List<FileStoragePrositionModel> storagePrositionModels = storagePrositionService.getList(storagePrositionQuery, headCorpId);
            storagePrositionModels.forEach((storage) -> {
                storageAddressMap.put(storage.getId(), new InetSocketAddress(server.getIp(), storage.getPort()));
            });
        });
        return storageAddressMap;
    }


    public Map<SocketAddress, FileStoragePrositionModel> getAdressMap(long heaCorpId) {
        FileServerQuery fileServerQuery = new FileServerQuery();
        List<FileServerModel> fileServerModels = getList(fileServerQuery, heaCorpId);

        Map<SocketAddress, FileStoragePrositionModel> socketAddressFileStoragePrositionModelMap = new HashMap<>();
        fileServerModels.forEach((server) -> {
            FileStoragePrositionQuery storagePrositionQuery = new FileStoragePrositionQuery();
            storagePrositionQuery.setServerId(server.getId());
            List<FileStoragePrositionModel> storagePrositionModels = storagePrositionService.getList(storagePrositionQuery, heaCorpId);
            storagePrositionModels.forEach((storage) -> {
                socketAddressFileStoragePrositionModelMap.put(new InetSocketAddress(server.getIp(), storage.getPort()), storage);
            });
        });
        return socketAddressFileStoragePrositionModelMap;
    }

    public FileServerModel getByKey(long id, long headCorpId) {
        return fileServerDao.getByKey(id, headCorpId);
    }

    public List<FileServerModel> getList(FileServerQuery query, long headCorpId) {
        query.setHeadCorpId(headCorpId);
        if(query.getState() == null) {
            query.setState(Constants.STATE_NORMAL);
        }
        return fileServerDao.getList(query);
    }

    public void updateByKey(FileServerModel model, long headCorpId, long loginOperId) {
        model.setHeadCorpId(headCorpId);
        model.setLastModifyOper(loginOperId);
        model.setLastModifyTime(new Date());
        fileServerDao.updateByKey(model);
    }

    public void delete(long id, long headCorpId, long loginOperId) {
        FileServerModel model = new FileServerModel();
        model.setId(id);
        model.setState(Constants.STATE_DELETE);
        updateByKey(model, headCorpId, loginOperId);
    }

    public FileServerModel add(FileServerModel model, long headCorpId, long loginOperId) {
        model.setId(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_SERVER));
        model.setHeadCorpId(headCorpId);
        model.setCreateTime(new Date());
        model.setCreateOper(loginOperId);
        model.setState(Constants.STATE_NORMAL);
        return fileServerDao.add(model);
    }


}
