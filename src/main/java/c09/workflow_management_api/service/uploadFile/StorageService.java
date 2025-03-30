package c09.workflow_management_api.service.uploadFile;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    String storeWithUUID(MultipartFile file);
}
