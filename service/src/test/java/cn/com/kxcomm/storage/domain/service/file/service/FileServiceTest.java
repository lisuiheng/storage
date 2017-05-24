package cn.com.kxcomm.storage.domain.service.file.service;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.model.query.FileAddrQuery;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.model.query.FileQuery;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.model.query.FileViewQuery;
import cn.com.kxcomm.storage.domain.service.view.service.FileViewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class FileServiceTest {
    private final Logger log = LoggerFactory.getLogger(FileServiceTest.class);

    private final long headCorpId = 1L;
    private final long loginOperId = 1L;
    private final String sysCode = "com";
    private final long id = 1494818648823L;

    @Autowired
    private FileService fileService;
    @Autowired
    private FileViewService fileViewService;
    @Autowired
    private FileAddrService fileAddrService;

    @Test
    public void add() throws Exception {
        FileModel fileModel = fileService.getByKey(id, headCorpId);
        fileService.add(fileModel, headCorpId, loginOperId);
    }

    @Test
    @Rollback
    public void delete() throws Exception {
        FileModel fileModel = fileService.getByKey(id, headCorpId);
        assert fileModel != null;
        FileModel model = fileService.add(fileModel, headCorpId, loginOperId);
        fileService.delete(model.getId(), headCorpId, loginOperId);
        fileModel = fileService.getByKey(model.getId(), headCorpId);
        assertNull(fileModel);
    }

    @Test
    public void getMd5() throws Exception {
        FileModel expectedModel = fileService.getByKey(id, headCorpId);
        FileModel actualModel= fileService.getByMd5(expectedModel.getMd5(), headCorpId);
        assertEquals(expectedModel, actualModel);
        actualModel = fileService.getByMd5("", headCorpId);
        assertNull(actualModel);
    }

    @Test
    @Rollback
    public void add1() {
        String postfix = "txt";
        String dir = "20170515";
        String fileName = "20170515110559484461" + "." + postfix;
        String relativePath = dir + "/" + fileName;
        long size = 3;
        String md5 = "202cb962ac59075b964b07152d234b70";
        long storageCount = 2;
        long storageId = 1;
        String fileViewName = "1.txt";
        String fileViewCode = fileService.add(relativePath, size, md5, storageCount, storageId, fileViewName, headCorpId, loginOperId, sysCode);
        assertNotNull(fileViewCode);
        //TODO see file back up

        //if md5 exit not save
        FileQuery fileQuery = new FileQuery();
        fileQuery.setMd5(md5);
        assertEquals(1, fileService.getList(fileQuery, headCorpId).size());

        FileViewQuery fileViewQuery = new FileViewQuery();
        fileViewQuery.setCode(fileViewCode);
        List<FileViewModel> fileViewModels = fileViewService.getList(fileViewQuery, headCorpId);
        assertEquals(1, fileViewModels.size());
        FileViewModel fileViewModel = fileViewModels.get(0);
        assertEquals(fileViewName, fileViewModel.getName());
        Long fileId = fileViewModel.getFileId();
        assertNotNull(fileId);

        FileModel fileModel = fileService.getByKey(fileId, headCorpId);
        assertEquals(java.util.Optional.ofNullable(size), java.util.Optional.ofNullable(fileModel.getSize()));
        assertEquals(md5, fileModel.getMd5());
        assertEquals(postfix, fileModel.getPostfix());

        FileAddrQuery fileAddrQuery = new FileAddrQuery();
        fileAddrQuery.setFileId(fileId);
        List<FileAddrModel> fileAddrModels = fileAddrService.getList(fileAddrQuery, headCorpId);
        FileAddrModel fileAddrModel = fileAddrModels.get(0);
        assertEquals(dir, fileAddrModel.getStorageDir());
        assertEquals(fileName, fileAddrModel.getStorageName());
        assertNotNull(fileAddrModel.getServerId());
    }
}