package c09.workflow_management_api.service.uploadFile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService() {
        this.rootLocation = Paths.get("src/main/java/c09/workflow_management_api/images");
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = Path.of(file.getOriginalFilename()).getFileName().toString(); // chống path traversal
        Path destinationFile = this.rootLocation.resolve(filename).normalize().toAbsolutePath();

        if (!destinationFile.startsWith(rootLocation.toAbsolutePath())) {
            throw new RuntimeException("Cannot store file outside of the storage directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return filename;
    }

    @Override
    public String storeWithUUID(MultipartFile file) {
        String filename = UUID.randomUUID() + "_" + Path.of(file.getOriginalFilename()).getFileName().toString();
        Path destinationFile = this.rootLocation.resolve(filename).normalize().toAbsolutePath();

        if (!destinationFile.startsWith(rootLocation.toAbsolutePath())) {
            throw new RuntimeException("Cannot store file outside of the storage directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return filename;
    }
}
