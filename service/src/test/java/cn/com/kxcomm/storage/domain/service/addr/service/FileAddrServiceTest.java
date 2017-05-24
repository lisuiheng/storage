package cn.com.kxcomm.storage.domain.service.addr.service;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.addr.dao.FileAddrDaoTest;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileAddrServiceTest {
    private final Logger log = LoggerFactory.getLogger(FileAddrDaoTest.class);

    private final long id = 1494817559528L;
    private final long headCorpId = 1L;
    private final long loginOperId = 1L;

    @Autowired
    private FileAddrService fileAddrService;

    @Test
    public void add() throws Exception {
        FileAddrModel addrModel = fileAddrService.getByKey(id, headCorpId);
        fileAddrService.add(addrModel, headCorpId, loginOperId);
    }

    @Test
    @Rollback
    public void delete() throws Exception {
        FileAddrModel model = fileAddrService.getByKey(id, headCorpId);
        assertNotNull(model);
        model = fileAddrService.add(model, headCorpId, loginOperId);
        fileAddrService.delete(model.getId(), headCorpId, loginOperId);
        model = fileAddrService.getByKey(model.getId(), headCorpId);
        assertNull(model);
    }
}