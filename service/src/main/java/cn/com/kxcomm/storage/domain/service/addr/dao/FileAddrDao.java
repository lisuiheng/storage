package cn.com.kxcomm.storage.domain.service.addr.dao;

import cn.com.kxcomm.storage.domain.service.addr.model.query.FileAddrQuery;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public class FileAddrDao {
    private final SqlSession sqlSession;

    public FileAddrDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public FileAddrModel getByKey(long id, long headCorpId) {
        HashMap<Object, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("headCorpId", headCorpId);
        params.put("state", Constants.STATE_NORMAL);
        return sqlSession.selectOne(Constants.SQL_KEY_GET_LIST_FILE_ADDR, params);
    }

    public List<FileAddrModel> getList(FileAddrQuery query) {
        return sqlSession.selectList(Constants.SQL_KEY_GET_LIST_FILE_ADDR, query);
    }

    public void updateByKey(FileAddrModel model) {
        sqlSession.update(Constants.SQL_KEY_UPDATE_FILE_ADDR, model);
    }

    public FileAddrModel add(FileAddrModel model) {
        sqlSession.update(Constants.SQL_KEY_ADD_FILE_ADDR, model);
        return getByKey(model.getId(), model.getHeadCorpId());
    }
}
