package cn.com.kxcomm.storage.domain.service.server.dao;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.server.model.FileServerModel;
import cn.com.kxcomm.storage.domain.service.server.model.query.FileServerQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class FileServerDaoTest {
    private final long headCorpId = 1L;
    private final long id = 1L;

    @Autowired
    private FileServerDao fileServerDao;

    @Test
    public void getByKey() throws Exception {
        FileServerModel model = fileServerDao.getByKey(id, headCorpId);
        assertNotNull(model);
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        FileServerQuery query = new FileServerQuery();
        FileServerModel model = addReturn();
        query.setId(model.getId());
        List<FileServerModel> expects = new ArrayList<>();
        expects.add(model);
        List<FileServerModel> actuals = fileServerDao.getList(query);
        assertEquals(expects, actuals);
    }

    @Test
    @Rollback
    public void updateByKey() throws Exception {
        FileServerModel model = fileServerDao.getByKey(id, headCorpId);
        String expect = "hello";
        model.setMemo(expect);
        fileServerDao.updateByKey(model);
        model =  fileServerDao.getByKey(id, headCorpId);
        assertNotNull(model);
        String actual = model.getMemo();
        assertEquals(actual, expect);
    }

    @Test
    @Rollback
    public void add() throws Exception {
        FileServerModel model = addReturn();
        assertNotNull(model);
    }

    private FileServerModel addReturn() {
        FileServerModel model = fileServerDao.getByKey(id, headCorpId);
        long newId = model.getId() + 1;
        model.setId(newId);
        fileServerDao.add(model);
        model = fileServerDao.getByKey(newId, headCorpId);
        return model;
    }



}