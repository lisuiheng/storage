package cn.com.kxcomm.storage.domain.service.server.service;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.server.model.FileServerModel;
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
public class FileServerServiceTest {
    private final long id = 1L;
    private final long headCorpId = 1L;

    @Autowired
    private FileServerService fileServerService;

    @Test
    @Rollback
    public void delete() throws Exception {
        FileServerModel model = addReturn();
        fileServerService.delete(model.getId(), model.getHeadCorpId(), model.getCreateOper());
        model = fileServerService.getByKey(model.getId(), model.getHeadCorpId());
        assertNull(model);
    }

    private FileServerModel addReturn() {
        FileServerModel model = fileServerService.getByKey(id, headCorpId);
        model = fileServerService.add(model, model.getHeadCorpId(), model.getCreateOper());
        return model;
    }
}