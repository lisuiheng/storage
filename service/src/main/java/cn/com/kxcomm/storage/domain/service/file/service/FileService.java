package cn.com.kxcomm.storage.domain.service.file.service;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.file.dao.FileDao;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.model.query.FileQuery;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.service.FileViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileService {
    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileDao fileDao;

    private final FileAddrService fileAddrService;
    private final FileViewService fileViewService;

    @Autowired
    public FileService(FileDao fileDao, FileAddrService fileAddrService, FileViewService fileViewService) {
        this.fileDao = fileDao;
        this.fileAddrService = fileAddrService;
        this.fileViewService = fileViewService;
    }

    public String add(String relativePath, long size, String md5, long storageCount, long storageId, String fileViewName, long headCorpId, long loginOperId, String sysCode) {
        int dirIndex = relativePath.lastIndexOf("/");
        String dir = relativePath.substring(0, dirIndex);
        String fileName = relativePath.substring(dirIndex + 1, relativePath.length());
        int postfixIndex = fileName.lastIndexOf(".");
        String postfix = fileName.substring(postfixIndex + 1, fileName.length());

        FileModel fileModel = new FileModel();
        fileModel.setSize(size);
        fileModel.setMd5(md5);
        fileModel.setStorageCount(storageCount);
        fileModel.setPostfix(postfix);

        //if md5 exit not save
        FileModel byMd5 = getByMd5(md5, headCorpId);
        if(byMd5 == null) {
            fileModel = add(fileModel, headCorpId, loginOperId);
        } else {
            fileModel = byMd5;
        }

        Optional<FileModel> fileModelOp = Optional.of(fileModel);
        Optional<Long> fileIdOp = fileModelOp.map(FileModel::getId);
        if(fileIdOp.isPresent()) {
            FileAddrModel fileAddrModel = fileAddrService.add(fileIdOp.get(), storageId, fileName, dir, headCorpId, loginOperId);
            FileViewModel fileViewModel = fileViewService.add(fileIdOp.get(), fileViewName, headCorpId, loginOperId, sysCode);
            return fileViewModel.getCode();
        } else {
            throw new RuntimeException("fileId is null");
        }
    }

    public FileModel getByMd5(String md5, long headCorpId) {
        FileQuery fileQuery = new FileQuery();
        fileQuery.setMd5(md5);
        List<FileModel> fileModels = getList(fileQuery, headCorpId);
        if(fileModels.size() == 0) {
            return null;
        } else if(fileModels.size() > 2) {
            log.warn("md5 cloud not match over 1");
        }
        return fileModels.get(0);
    }

    public FileModel getByKey(long id, long headCorpId) {
        return fileDao.getByKey(id, headCorpId);
    }

    public List<FileModel> getList(FileQuery query, long headCorpId) {
        query.setHeadCorpId(headCorpId);
        if(query.getState() == null) {
            query.setState(Constants.STATE_NORMAL);
        }
        return fileDao.getList(query);
    }

    public void updateByKey(FileModel model, long headCorpId, long loginOperId) {
        model.setHeadCorpId(headCorpId);
        model.setLastModifyOper(loginOperId);
        model.setLastModifyTime(new Date());
        fileDao.updateByKey(model);
    }

    public void delete(long id, long headCorpId, long loginOperId) {
        FileModel model = new FileModel();
        model.setId(id);
        model.setState(Constants.STATE_DELETE);
        updateByKey(model, headCorpId, loginOperId);
    }

    public FileModel add(FileModel model, long headCorpId, long loginOperId) {
        model.setId(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE));
        model.setHeadCorpId(headCorpId);
        model.setCreateTime(new Date());
        model.setCreateOper(loginOperId);
        model.setState(Constants.STATE_NORMAL);
        return fileDao.add(model);
    }
}
