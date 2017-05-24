package cn.com.kxcomm.storage.domain.service.storage.dao;


import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.model.query.FileStoragePrositionQuery;
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
public class FileStoragePrositionDaoTest {
    private final long headCorpId = 1L;
    private final long id = 1L;

    @Autowired
    private FileStoragePrositionDao fileStoragePrositionDao;

    @Test
    public void getByKey() throws Exception {
        FileStoragePrositionModel model = fileStoragePrositionDao.getByKey(id, headCorpId);
        assertNotNull(model);
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        FileStoragePrositionQuery query = new FileStoragePrositionQuery();
        FileStoragePrositionModel model = addReturn();
        query.setId(model.getId());
        List<FileStoragePrositionModel> expects = new ArrayList<>();
        expects.add(model);
        List<FileStoragePrositionModel> actuals = fileStoragePrositionDao.getList(query);
        assertEquals(expects, actuals);
    }

    @Test
    @Rollback
    public void updateByKey() throws Exception {
        FileStoragePrositionModel model = fileStoragePrositionDao.getByKey(id, headCorpId);
        String expect = "hello";
        model.setMemo(expect);
        fileStoragePrositionDao.updateByKey(model);
        model =  fileStoragePrositionDao.getByKey(id, headCorpId);
        assertNotNull(model);
        String actual = model.getMemo();
        assertEquals(actual, expect);
    }

    @Test
    @Rollback
    public void add() throws Exception {
        FileStoragePrositionModel model = addReturn();
        assertNotNull(model);
    }

    private FileStoragePrositionModel addReturn() {
        FileStoragePrositionModel model = fileStoragePrositionDao.getByKey(id, headCorpId);
        long newId = model.getId() + System.nanoTime();
        model.setId(newId);
        fileStoragePrositionDao.add(model);
        model = fileStoragePrositionDao.getByKey(newId, headCorpId);
        return model;
    }


}