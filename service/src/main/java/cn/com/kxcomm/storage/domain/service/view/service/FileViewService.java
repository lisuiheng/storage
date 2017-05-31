package cn.com.kxcomm.storage.domain.service.view.service;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.view.dao.FileViewDao;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.model.query.FileViewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileViewService {
    private final FileViewDao fileViewDao;

    @Autowired
    public FileViewService(FileViewDao fileViewDao) {
        this.fileViewDao = fileViewDao;
    }



    public FileViewModel getByViewCode(String fileViewCode, long headCorpId) {
        FileViewQuery fileViewQuery = new FileViewQuery();
        fileViewQuery.setCode(fileViewCode);
        List<FileViewModel> fileViewModels = getList(fileViewQuery, headCorpId);
        if(fileViewModels.size() != 1) {
            throw new RuntimeException(String.format("fileViewCode %d get worng list %s", fileViewCode, fileViewModels));
        } else {
            return fileViewModels.get(0);
        }
    }

    public FileViewModel add(long fileId, String fileName, String fileViewCode, long headCorpId, long loginOperId, String sysCode) {
        FileViewModel fileViewModel = new FileViewModel();
        fileViewModel.setFileId(fileId);
        fileViewModel.setName(fileName);
        fileViewModel.setCode(fileViewCode);
        return add(fileViewModel, headCorpId, loginOperId, sysCode);
    }

    public FileViewModel getByKey(long id, long headCorpId) {
        return fileViewDao.getByKey(id, headCorpId);
    }

    public List<FileViewModel> getList(FileViewQuery query, long headCorpId) {
        query.setHeadCorpId(headCorpId);
        if(query.getState() == null) {
            query.setState(Constants.STATE_NORMAL);
        }
        return fileViewDao.getList(query);
    }

    public void updateByKey(FileViewModel model, long headCorpId, long loginOperId) {
        model.setHeadCorpId(headCorpId);
        model.setLastModifyOper(loginOperId);
        model.setLastModifyTime(new Date());
        fileViewDao.updateByKey(model);
    }

    public void delete(long id, long headCorpId, long loginOperId) {
        FileViewModel model = new FileViewModel();
        model.setId(id);
        model.setState(Constants.STATE_DELETE);
        updateByKey(model, headCorpId, loginOperId);
    }

    public FileViewModel add(FileViewModel model, long headCorpId, long loginOperId, String sysCode) {
        model.setId(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_VIEW));
        if(model.getCode() == null) {
            model.setCode(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_VIEW_CODE).toString());
        }
        model.setHeadCorpId(headCorpId);
        model.setSysCode(sysCode);
        model.setCreateTime(new Date());
        model.setCreateOper(loginOperId);
        model.setState(Constants.STATE_NORMAL);
        return fileViewDao.add(model);
    }
}
