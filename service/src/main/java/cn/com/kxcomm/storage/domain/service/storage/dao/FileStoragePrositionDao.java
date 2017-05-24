package cn.com.kxcomm.storage.domain.service.storage.dao;

import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.model.query.FileStoragePrositionQuery;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FileStoragePrositionDao {
    private final SqlSession sqlSession;

    public FileStoragePrositionDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public FileStoragePrositionModel getByKey(long id, long headCorpId) {
        HashMap<Object, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("headCorpId", headCorpId);
        params.put("state", Constants.STATE_NORMAL);
        return sqlSession.selectOne(Constants.SQL_KEY_GET_LIST_FILE_STORAGE, params);
    }

    public List<FileStoragePrositionModel> getList(FileStoragePrositionQuery query) {
        return sqlSession.selectList(Constants.SQL_KEY_GET_LIST_FILE_STORAGE, query);
    }

    public void updateByKey(FileStoragePrositionModel model) {
        sqlSession.update(Constants.SQL_KEY_UPDATE_FILE_STORAGE, model);
    }


    public FileStoragePrositionModel add(FileStoragePrositionModel model) {
        sqlSession.update(Constants.SQL_KEY_ADD_FILE_STORAGE, model);
        return getByKey(model.getId(), model.getHeadCorpId());
    }
}
