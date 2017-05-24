package cn.com.kxcomm.storage.domain.service.view.dao;

import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.model.query.FileViewQuery;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FileViewDao {
    private final SqlSession sqlSession;

    public FileViewDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public FileViewModel getByKey(long id, long headCorpId) {
        HashMap<Object, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("headCorpId", headCorpId);
        params.put("state", Constants.STATE_NORMAL);
        return sqlSession.selectOne(Constants.SQL_KEY_GET_LIST_FILE_VIEW, params);
    }

    public List<FileViewModel> getList(FileViewQuery query) {
        return sqlSession.selectList(Constants.SQL_KEY_GET_LIST_FILE_VIEW, query);
    }

    public void updateByKey(FileViewModel model) {
        sqlSession.update(Constants.SQL_KEY_UPDATE_FILE_VIEW, model);
    }


    public FileViewModel add(FileViewModel model) {
        sqlSession.update(Constants.SQL_KEY_ADD_FILE_VIEW, model);
        return getByKey(model.getId(), model.getHeadCorpId());
    }
}
