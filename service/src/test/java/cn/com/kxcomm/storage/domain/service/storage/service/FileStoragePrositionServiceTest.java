package cn.com.kxcomm.storage.domain.service.storage.service;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNull;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileStoragePrositionServiceTest {
    private final long id = 1L;
    private final long headCorpId = 1L;

    @Autowired
    private FileStoragePrositionService fileStoragePrositionService;

    @Test
    @Rollback
    public void delete() throws Exception {
        FileStoragePrositionModel model = addReturn();
        fileStoragePrositionService.delete(model.getId(), model.getHeadCorpId(), model.getCreateOper());
        model = fileStoragePrositionService.getByKey(model.getId(), model.getHeadCorpId());
        assertNull(model);
    }

    private FileStoragePrositionModel addReturn() {
        FileStoragePrositionModel model = fileStoragePrositionService.getByKey(id, headCorpId);
        model = fileStoragePrositionService.add(model, model.getHeadCorpId(), model.getCreateOper());
        return model;
    }
}