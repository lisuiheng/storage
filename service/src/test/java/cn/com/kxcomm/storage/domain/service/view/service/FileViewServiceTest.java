package cn.com.kxcomm.storage.domain.service.view.service;

import cn.com.kxcomm.storage.domain.service.Application;

import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileViewServiceTest {
    private final long id = 1L;
    private final long headCorpId = 1L;
    private final long loginOperId = 1L;
    private final String sysCode = "com";

    @Autowired
    private FileViewService fileViewService;

    @Test
    @Rollback
    public void delete() throws Exception {
        FileViewModel model = addReturn();
        fileViewService.delete(model.getId(), model.getHeadCorpId(), model.getCreateOper());
        model = fileViewService.getByKey(model.getId(), model.getHeadCorpId());
        assertNull(model);
    }

    @Test
    @Rollback
    public void addByFileIdAndName() throws Exception {
        long expectFileId = 1L;
        String expectFileName = "1.txt";
        FileViewModel fileViewModel = fileViewService.add(expectFileId, expectFileName, null , headCorpId, loginOperId, sysCode);
        assertNotNull(fileViewModel);
        assertEquals(java.util.Optional.of(expectFileId), java.util.Optional.of(fileViewModel.getFileId()));
        assertEquals(expectFileName, fileViewModel.getName());
        assertNotNull(fileViewModel.getCode());
        assertNotNull(fileViewModel.getId());
        assertNotNull(fileViewModel.getHeadCorpId());
        assertNotNull(fileViewModel.getCreateOper());
        assertNotNull(fileViewModel.getCreateTime());
    }

    private FileViewModel addReturn() {
        FileViewModel model = fileViewService.getByKey(id, headCorpId);
        model = fileViewService.add(model, model.getHeadCorpId(), model.getCreateOper(), sysCode);
        return model;
    }

}