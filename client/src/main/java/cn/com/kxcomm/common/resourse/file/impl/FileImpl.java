package cn.com.kxcomm.common.resourse.file.impl;

import cn.com.kxcomm.common.resourse.file.FileProvide;

import java.io.File;

public class FileImpl implements FileProvide {
    @Override
    public Long upload(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) {
        return null;
    }

    @Override
    public String downLoad(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) {
        return null;
    }
}
