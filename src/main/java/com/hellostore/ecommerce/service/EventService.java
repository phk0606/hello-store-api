package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.EventDto;
import com.hellostore.ecommerce.dto.EventSearchCondition;
import com.hellostore.ecommerce.entity.Event;
import com.hellostore.ecommerce.repository.EventImageRepository;
import com.hellostore.ecommerce.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventImageService eventImageService;
    private final EventImageRepository eventImageRepository;

    @Transactional
    public void createEvent(EventDto eventDto, MultipartFile eventImage) {

        Event event = Event.builder().title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .eventDateA(eventDto.getEventDateA())
                .eventDateB(eventDto.getEventDateB())
                .content(eventDto.getContent())
                .build();

        Event event1 = eventRepository.save(event);

        if (eventImage != null) {
            eventImageService.uploadEventImage(eventImage, event1);
        }
    }

    public Page<EventDto> getEvents(EventSearchCondition eventSearchCondition, Pageable pageable) {
        return eventRepository.getEvents(eventSearchCondition, pageable);
    }

    public EventDto getEvent(Long eventId) throws IOException {
        EventDto event = eventRepository.getEvent(eventId);
        byte[] bytes = Files.readAllBytes(Paths.get(event.getFilePath(), event.getFileName()));
        event.setImage(bytes);
        return event;
    }

    @Transactional
    public void modifyEvent(EventDto eventDto, MultipartFile eventImage) {

        Event event = Event.builder()
                .id(eventDto.getEventId())
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .content(eventDto.getContent())
                .eventDateA(eventDto.getEventDateA())
                .eventDateB(eventDto.getEventDateB()).build();

        Event event1 = eventRepository.modifyEvent(event);

        if (eventImage != null) {
            eventImageRepository.removeEventImage(eventDto.getEventId());
            eventImageService.uploadEventImage(eventImage, event1);
        }
    }

    @Transactional
    public void removeEvents(List<Long> eventIds) {

        eventImageRepository.removeEventImages(eventIds);
        eventRepository.removeEvents(eventIds);
    }
}
