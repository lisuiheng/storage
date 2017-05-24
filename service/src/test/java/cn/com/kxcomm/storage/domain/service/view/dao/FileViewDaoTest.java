package cn.com.kxcomm.storage.domain.service.view.dao;


import cn.com.kxcomm.storage.domain.service.Application;

import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.model.query.FileViewQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class FileViewDaoTest {
    private final long headCorpId = 1L;
    private final long id = 1L;

    @Autowired
    private FileViewDao fileViewDao;

    @Test
    public void getByKey() throws Exception {
        FileViewModel model = fileViewDao.getByKey(id, headCorpId);
        assertNotNull(model);
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        FileViewQuery query = new FileViewQuery();
        FileViewModel model = addReturn();
        query.setId(model.getId());
        List<FileViewModel> expects = new ArrayList<>();
        expects.add(model);
        List<FileViewModel> actuals = fileViewDao.getList(query);
        assertEquals(expects, actuals);
    }

    @Test
    @Rollback
    public void updateByKey() throws Exception {
        FileViewModel model = fileViewDao.getByKey(id, headCorpId);
        String expect = "hello";
        model.setName(expect);
        fileViewDao.updateByKey(model);
        model =  fileViewDao.getByKey(id, headCorpId);
        assertNotNull(model);
        String actual = model.getName();
        assertEquals(actual, expect);
    }

    @Test
    @Rollback
    public void add() throws Exception {
        FileViewModel model = addReturn();
        assertNotNull(model);
    }

    private FileViewModel addReturn() {
        FileViewModel model = fileViewDao.getByKey(id, headCorpId);
        long newId = model.getId() + System.nanoTime();
        model.setId(newId);
        fileViewDao.add(model);
        model = fileViewDao.getByKey(newId, headCorpId);
        return model;
    }


}