package cn.com.kxcomm.storage.domain.proxy;


import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.service.storage.service.FileStoragePrositionService;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_HEAD_CORP_ID;
import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_LOGIN_OPER_ID;
import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_SYSCODE;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ScheduledTasks {
    private final Logger log = getLogger(ScheduledTasks.class);

    private long lastRefreshStorageFileTime = 0;

    private final ProxyConfig config;
    private final FileStoragePrositionService storagePrositionService;
    private final FileServerService fileServerService;
    private final FileAddrService fileAddrService;

    public ScheduledTasks(ProxyConfig config, FileStoragePrositionService storagePrositionService, FileServerService fileServerService, FileAddrService fileAddrService) {
        this.config = config;
        this.storagePrositionService = storagePrositionService;
        this.fileServerService = fileServerService;
        this.fileAddrService = fileAddrService;
    }

//    @Scheduled(fixedRate = 60000)
    public void refreshStorageSpace() {
        config.getClientApiMap().forEach((address, clientApi) -> {
            FileStoragePrositionModel storagePrositionModel = config.getStorageMap().get(address);

            SpaceRequest spaceRequest = new SpaceRequest(address, SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID, SYSTEM_SYSCODE);
            SpaceResponse spaceResponse = null;
            try {
                spaceResponse = (SpaceResponse) clientApi.send(spaceRequest);
            } catch (StorageException e) {
                log.error(e.getMessage());
            }
            storagePrositionService.refreshSpace(spaceResponse.getTotalSpace(), spaceResponse.getFreeSpace(), spaceResponse.getUsableSpace(),
                    storagePrositionModel.getId(), SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID);
        });
    }


//    public void refreshFileAddr() {
//        config.getClientApiMap().forEach(((address, clientApi) -> {
//            FileStoragePrositionModel storagePrositionModel = config.getStorageMap().get(address);
//            ListFileRequest listFileRequest = new ListFileRequest(lastRefreshStorageFileTime, address, SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID, SYSTEM_SYSCODE);
//            ListFileResponse listFileResponse = null;
//            try {
//                listFileResponse = (ListFileResponse) clientApi.send(listFileRequest);
//            } catch (StorageException e) {
//                log.error(e.getMessage());
//            }
//            assert listFileResponse != null;
//            listFileResponse.getFiles().forEach(file -> {
//                storagePrositionService.
//            });
//        }));
//    }
}
