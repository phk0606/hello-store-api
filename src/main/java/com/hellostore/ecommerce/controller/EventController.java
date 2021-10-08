package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.EventDto;
import com.hellostore.ecommerce.dto.EventSearchCondition;
import com.hellostore.ecommerce.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @PostMapping("/createEvent")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createNotice(@RequestPart EventDto eventDto, @RequestParam MultipartFile eventImage) {
        log.debug("noticeDto: {}", eventDto);
        eventService.createEvent(eventDto, eventImage);
    }

    @GetMapping("/getEvents")
    public Page<EventDto> getEvents(EventSearchCondition eventSearchCondition,
                                     Pageable pageable) {
        log.debug("eventSearchCondition: {}", eventSearchCondition);
        return eventService.getEvents(eventSearchCondition, pageable);
    }

    @GetMapping("/getEvent")
    public EventDto getEvent(@RequestParam Long eventId) throws IOException {
        log.debug("eventId: {}", eventId);
        return eventService.getEvent(eventId);
    }

    @PutMapping("/modifyEvent")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyEvent(@RequestPart EventDto eventDto, @RequestParam MultipartFile eventImage) {

        eventService.modifyEvent(eventDto, eventImage);
    }

    @DeleteMapping("/removeEvents")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeEvents(@RequestBody EventDto eventDto) {

        List<Long> eventIds = eventDto.getEventIds();
        log.debug("eventIds: {}", eventIds);
        eventService.removeEvents(eventIds);
    }
}
