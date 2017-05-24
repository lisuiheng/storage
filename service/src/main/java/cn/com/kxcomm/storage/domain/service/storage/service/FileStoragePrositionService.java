package cn.com.kxcomm.storage.domain.service.storage.service;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;

import cn.com.kxcomm.storage.domain.service.storage.dao.FileStoragePrositionDao;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.model.query.FileStoragePrositionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileStoragePrositionService {
    private final FileStoragePrositionDao fileStoragePrositionDao;

    private final Logger log = LoggerFactory.getLogger(FileStoragePrositionService.class);

    @Autowired
    public FileStoragePrositionService(FileStoragePrositionDao fileStoragePrositionDao) {
        this.fileStoragePrositionDao = fileStoragePrositionDao;
    }



    public void refreshSpace(long total, long free, long used,long storageId, long headCorpId, long loginOperId) {
        FileStoragePrositionModel model = new FileStoragePrositionModel();
        model.setId(storageId);
        model.setTotal(total);
        model.setAvailable(free);
        model.setUsed(used);
        updateByKey(model, headCorpId, loginOperId);
        log.info("refresh space ");
    }

    public List<FileStoragePrositionModel> getAllList(long headCorpId) {
        return getList(new FileStoragePrositionQuery(), headCorpId);
    }


    public FileStoragePrositionModel getByKey(long id, long headCorpId) {
        return fileStoragePrositionDao.getByKey(id, headCorpId);
    }

    public List<FileStoragePrositionModel> getList(FileStoragePrositionQuery query, long headCorpId) {
        query.setHeadCorpId(headCorpId);
        if(query.getState() == null) {
            query.setState(Constants.STATE_NORMAL);
        }
        return fileStoragePrositionDao.getList(query);
    }

    public void updateByKey(FileStoragePrositionModel model, long headCorpId, long loginOperId) {
        model.setHeadCorpId(headCorpId);
        model.setLastModifyOper(loginOperId);
        model.setLastModifyTime(new Date());
        fileStoragePrositionDao.updateByKey(model);
    }

    public void delete(long id, long headCorpId, long loginOperId) {
        FileStoragePrositionModel model = new FileStoragePrositionModel();
        model.setId(id);
        model.setState(Constants.STATE_DELETE);
        updateByKey(model, headCorpId, loginOperId);
    }

    public FileStoragePrositionModel add(FileStoragePrositionModel model, long headCorpId, long loginOperId) {
        model.setId(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_STORAGE));
        model.setHeadCorpId(headCorpId);
        model.setCreateTime(new Date());
        model.setCreateOper(loginOperId);
        model.setState(Constants.STATE_NORMAL);
        return fileStoragePrositionDao.add(model);
    }
}
