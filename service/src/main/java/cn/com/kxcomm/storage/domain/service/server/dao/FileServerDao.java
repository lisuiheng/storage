package cn.com.kxcomm.storage.domain.service.server.dao;

import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.server.model.FileServerModel;
import cn.com.kxcomm.storage.domain.service.server.model.query.FileServerQuery;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FileServerDao {
    private final SqlSession sqlSession;

    public FileServerDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public FileServerModel getByKey(long id, long headCorpId) {
        HashMap<Object, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("headCorpId", headCorpId);
        params.put("state", Constants.STATE_NORMAL);
        return sqlSession.selectOne(Constants.SQL_KEY_GET_LIST_FILE_SERVER, params);
    }

    public List<FileServerModel> getList(FileServerQuery query) {
        return sqlSession.selectList(Constants.SQL_KEY_GET_LIST_FILE_SERVER, query);
    }

    public void updateByKey(FileServerModel model) {
        sqlSession.update(Constants.SQL_KEY_UPDATE_FILE_SERVER, model);
    }


    public FileServerModel add(FileServerModel model) {
        sqlSession.update(Constants.SQL_KEY_ADD_FILE_SERVER, model);
        return getByKey(model.getId(), model.getHeadCorpId());
    }
}
