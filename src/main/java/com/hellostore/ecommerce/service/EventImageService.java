package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.Event;
import com.hellostore.ecommerce.entity.EventImage;
import com.hellostore.ecommerce.entity.ImageFile;
import com.hellostore.ecommerce.repository.EventImageRepository;
import com.hellostore.ecommerce.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventImageService {

    private final EventImageRepository eventImageRepository;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadEventImage(MultipartFile multipartFile,
                                          Event event) throws IOException {

        ImageFile imageFile = fileUtil.fileUpload(multipartFile);

        EventImage eventImage1 = EventImage.builder()
                .imageFile(imageFile)
                    .event(event)
                    .build();

        log.debug("eventImage1: {}", eventImage1);
        eventImageRepository.createEventImage(eventImage1);
    }

    @Transactional
    public void removeEventImage(Long eventId) throws IOException {
        EventImage eventImage = eventImageRepository.getEventImage(eventId);
        fileUtil.deleteIfExists(eventImage.getImageFile().getFilePath(), eventImage.getImageFile().getFileName());
    }
}
