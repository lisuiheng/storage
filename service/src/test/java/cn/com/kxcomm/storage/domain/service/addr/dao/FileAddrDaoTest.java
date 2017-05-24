package cn.com.kxcomm.storage.domain.service.addr.dao;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.model.query.FileAddrQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileAddrDaoTest {
    private final Logger log = LoggerFactory.getLogger(FileAddrDaoTest.class);
    private final long headCorpId = 1L;
    private final long id = 1494817559528L;

    @Autowired
    private FileAddrDao fileAddrDao;


    @Test
    public void testDao() {
        log.info(fileAddrDao.toString());
    }

    @Test
    public void testService() {
        FileAddrModel fileAddrModel = fileAddrDao.getByKey(id, headCorpId);
        log.info( fileAddrModel.getStorageName());
    }

    @Test
    public void testGetList() {
        FileAddrQuery fileAddrQuery = new FileAddrQuery();
        List<FileAddrModel> fileAddrModels = fileAddrDao.getList(fileAddrQuery);
        log.info(fileAddrModels.toString());
    }

    @Test
    public void testUpdate() {
        FileAddrModel fileAddrModel = fileAddrDao.getByKey(id, headCorpId);
        fileAddrModel.setMemo("hello");
        fileAddrDao.updateByKey(fileAddrModel);
    }
}

