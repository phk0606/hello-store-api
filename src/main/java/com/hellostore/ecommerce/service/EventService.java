package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.EventDto;
import com.hellostore.ecommerce.dto.EventSearchCondition;
import com.hellostore.ecommerce.entity.Event;
import com.hellostore.ecommerce.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventImageService eventImageService;

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

    public EventDto getEvent(Long eventId) {
        return eventRepository.getEvent(eventId);
    }
}
