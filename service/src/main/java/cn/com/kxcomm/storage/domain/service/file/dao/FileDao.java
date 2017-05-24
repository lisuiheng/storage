package cn.com.kxcomm.storage.domain.service.file.dao;

import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.model.query.FileQuery;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FileDao {
    private final SqlSession sqlSession;

    public FileDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public FileModel getByKey(long id, long headCorpId) {
        HashMap<Object, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("headCorpId", headCorpId);
        params.put("state", Constants.STATE_NORMAL);
        return sqlSession.selectOne(Constants.SQL_KEY_GET_LIST_FILE, params);
    }

    public List<FileModel> getList(FileQuery query) {
        return sqlSession.selectList(Constants.SQL_KEY_GET_LIST_FILE, query);
    }

    public void updateByKey(FileModel model) {
        sqlSession.update(Constants.SQL_KEY_UPDATE_FILE, model);
    }


    public FileModel add(FileModel model) {
        sqlSession.update(Constants.SQL_KEY_ADD_FILE, model);
        return getByKey(model.getId(), model.getHeadCorpId());
    }
}
