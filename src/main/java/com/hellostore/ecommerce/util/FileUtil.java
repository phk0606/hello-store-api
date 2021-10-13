package com.hellostore.ecommerce.util;

import com.hellostore.ecommerce.entity.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@Slf4j
public class FileUtil {

    @Value("${file.store.path}")
    private String fileStorePath;

    public ImageFile fileUpload(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();
        log.debug("OriginalFilename: {}", originalFilename);

        if(originalFilename.contains("_")) {
            String fileNameWithImageType[] = originalFilename.split("_");
            originalFilename = fileNameWithImageType[1];
        }

        String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
        long fileSize = multipartFile.getSize();

        if (!Files.exists(Paths.get(fileStorePath))) {

            try {
                Files.createDirectories(Paths.get(fileStorePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {

            Files.copy(inputStream, Paths.get(fileStorePath, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ImageFile(originalFilename, fileName, fileStorePath, fileSize);
    }

    public void deleteIfExists(String fileStorePath, String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(fileStorePath, fileName));
    }
}
