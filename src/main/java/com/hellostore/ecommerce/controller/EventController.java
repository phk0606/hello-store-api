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
}
