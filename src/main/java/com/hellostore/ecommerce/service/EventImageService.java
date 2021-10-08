package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.Event;
import com.hellostore.ecommerce.entity.EventImage;
import com.hellostore.ecommerce.repository.EventImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventImageService {

    private final EventImageRepository eventImageRepository;

    @Value("${file.store.path}")
    private String fileStorePath;

    @Transactional
    public void uploadEventImage(MultipartFile eventImage,
                                          Event event) {

            String originalFilename = eventImage.getOriginalFilename();
            log.debug("OriginalFilename: {}", originalFilename);

            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            long fileSize = eventImage.getSize();

            if (!Files.exists(Paths.get(fileStorePath))) {

                try {
                    Files.createDirectories(Paths.get(fileStorePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (InputStream inputStream = eventImage.getInputStream()) {

                Files.copy(inputStream, Paths.get(fileStorePath, fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            EventImage eventImage1 = EventImage.builder().originalFileName(originalFilename)
                    .fileName(fileName).filePath(fileStorePath)
                    .fileSize(fileSize)
                    .event(event)
                    .build();

            log.debug("eventImage1: {}", eventImage1);
            eventImageRepository.createEventImage(eventImage1);
    }
}
