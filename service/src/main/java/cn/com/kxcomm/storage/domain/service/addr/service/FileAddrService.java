package cn.com.kxcomm.storage.domain.service.addr.service;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.addr.model.query.FileAddrQuery;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.addr.dao.FileAddrDao;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.service.FileStoragePrositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileAddrService {
    private final FileAddrDao fileAddrDao;
    private final FileStoragePrositionService storagePrositionService;

    @Autowired
    public FileAddrService(FileAddrDao fileAddrDao, FileStoragePrositionService storagePrositionService) {
        this.fileAddrDao = fileAddrDao;
        this.storagePrositionService = storagePrositionService;
    }

    public FileAddrModel getByFileId(long filedId, long headCorpId) {
        FileAddrQuery fileAddrQuery = new FileAddrQuery();
        fileAddrQuery.setFileId(filedId);
        List<FileAddrModel> fileAddrModels = getList(fileAddrQuery, headCorpId);
        return fileAddrModels.get(0);
    }

    public FileAddrModel add(long fileId, long storageId, String fileName, String dir, long headCorpId, long loginOperId) {
        FileStoragePrositionModel storagePrositionModel = storagePrositionService.getByKey(storageId, headCorpId);
        FileAddrModel fileAddrModel = new FileAddrModel();
        fileAddrModel.setFileId(fileId);
        fileAddrModel.setServerId(storagePrositionModel.getServerId());
        fileAddrModel.setStoragePositionId(storageId);
        fileAddrModel.setStorageName(fileName);
        fileAddrModel.setStorageDir(dir);
        return add(fileAddrModel, headCorpId, loginOperId);
    }

    public FileAddrModel getByKey(long id, long headCorpId) {
        return fileAddrDao.getByKey(id, headCorpId);
    }

//    public Set<Long> refreshFileAddr(List<ListFileResponse.File> files, long storageId, long headCorpId, long loginOperId) {
//        FileAddrQuery query = new FileAddrQuery();
//        query.setStoragePositionId(storageId);
//        List<FileAddrModel> fileAddrModels = getList(query, headCorpId);
//
//        files.forEach(file -> {
//            fileAddrModels.stream().
//
//        });
//        return null;
//    }

    public List<FileAddrModel> getList(FileAddrQuery query, long headCorpId) {
        query.setHeadCorpId(headCorpId);
        if(query.getState() == null) {
            query.setState(Constants.STATE_NORMAL);
        }
        return fileAddrDao.getList(query);
    }

    public void updateByKey(FileAddrModel model, long headCorpId, long loginOperId) {
        model.setHeadCorpId(headCorpId);
        model.setLastModifyOper(loginOperId);
        model.setLastModifyTime(new Date());
        fileAddrDao.updateByKey(model);
    }

    public void delete(long id, long headCorpId, long loginOperId) {
        FileAddrModel model = new FileAddrModel();
        model.setId(id);
        model.setState(Constants.STATE_DELETE);
        updateByKey(model, headCorpId, loginOperId);
    }

    public FileAddrModel add(FileAddrModel model, long headCorpId, long loginOperId) {
        model.setId(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_ADDR));
        model.setHeadCorpId(headCorpId);
        model.setCreateTime(new Date());
        model.setCreateOper(loginOperId);
        model.setState(Constants.STATE_NORMAL);
        return fileAddrDao.add(model);
    }
}
