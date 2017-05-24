package cn.com.kxcomm.storage.domain.service.file.dao;

import cn.com.kxcomm.storage.domain.service.Application;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.model.query.FileQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FileDaoTest {
    private final Logger log = LoggerFactory.getLogger(FileDaoTest.class);

    private final long headCorpId = 1L;
    private final long id = 1494818648823L;

    @Autowired
    private FileDao fileDao;

    @Test
    public void getByKey() throws Exception {
        FileModel fileModel = fileDao.getByKey(id, headCorpId);
        log.info(fileModel.toString());
    }

    @Test
    public void getList() throws Exception {
        FileQuery fileAddrQuery = new FileQuery();
        List<FileModel> fileModels = fileDao.getList(fileAddrQuery);
        log.info(fileModels.toString());
    }

    @Test
    public void updateByKey() throws Exception {
        FileModel fileAddrModel = fileDao.getByKey(id, headCorpId);
        fileAddrModel.setLastModifyTime(new Date());
        fileDao.updateByKey(fileAddrModel);
    }

}