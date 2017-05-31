//package cn.com.kxcomm.storage.domain.proxy;
//
//
//import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
//import cn.com.kxcomm.storage.domain.service.file.service.FileService;
//import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
//import cn.com.kxcomm.storage.domain.service.storage.service.FileStoragePrositionService;
//import org.slf4j.Logger;
//import org.springframework.stereotype.Component;
//
//import static org.slf4j.LoggerFactory.getLogger;
//
//@Component
//public class ScheduledTasks {
//    private final Logger log = getLogger(ScheduledTasks.class);
//
//    private long lastRefreshStorageFileTime = 0;
//
//    private final ProxyConfig config;
//    private final FileStoragePrositionService storagePrositionService;
//    private final FileServerService fileServerService;
//    private final FileAddrService fileAddrService;
//    private final FileService fileService;
//
//    public ScheduledTasks(ProxyConfig config, FileStoragePrositionService storagePrositionService, FileServerService fileServerService, FileAddrService fileAddrService, FileService fileService) {
//        this.config = config;
//        this.storagePrositionService = storagePrositionService;
//        this.fileServerService = fileServerService;
//        this.fileAddrService = fileAddrService;
//        this.fileService = fileService;
//    }
//
//    /**
//     * wen db storage count < actual file storage coiunt(find file addr count) back up file
//     * for high availability , The file back to the different storage as far as possible
//     *
//     */
//    //TODO uplaod do
//    public void backup() {
////        fileService.getNeedBackUpFile();
//    }
//
////    @Scheduled(fixedRate = 60000)
////    public void refreshStorageSpace() {
////        config.getClientApiMap().forEach((address, clientApi) -> {
////            FileStoragePrositionModel storagePrositionModel = config.getStorageMap().get(address);
////
////            SpaceRequest spaceRequest = new SpaceRequest(address, SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID, SYSTEM_SYSCODE);
////            SpaceResponse spaceResponse = null;
////            try {
////                spaceResponse = (SpaceResponse) clientApi.send(spaceRequest);
////            } catch (StorageException e) {
////                log.error(e.getMessage());
////            }
////            assert spaceResponse != null;
////            storagePrositionService.refreshSpace(spaceResponse.getTotalSpace(), spaceResponse.getFreeSpace(), spaceResponse.getUsableSpace(),
////                    storagePrositionModel.getId(), SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID);
////        });
////    }
//
//
////    public void refreshFileAddr() {
////        config.getClientApiMap().forEach(((address, clientApi) -> {
////            FileStoragePrositionModel storagePrositionModel = config.getStorageMap().get(address);
////            ListFileRequest listFileRequest = new ListFileRequest(lastRefreshStorageFileTime, address, SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID, SYSTEM_SYSCODE);
////            ListFileResponse listFileResponse = null;
////            try {
////                listFileResponse = (ListFileResponse) clientApi.send(listFileRequest);
////            } catch (StorageException e) {
////                log.error(e.getMessage());
////            }
////            assert listFileResponse != null;
////            listFileResponse.getFiles().forEach(file -> {
////                storagePrositionService.
////            });
////        }));
////    }
//}
