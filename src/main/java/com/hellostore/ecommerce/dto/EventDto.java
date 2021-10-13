package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ImageFile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class EventDto {

    private Long eventId;
    private List<Long> eventIds;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDateA;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDateB;
    private String content;

    private ImageFile imageFile;

    @Setter
    private byte[] image;

    @QueryProjection
    public EventDto(Long eventId, String title, String description, LocalDate eventDateA, LocalDate eventDateB, String content, ImageFile imageFile) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDateA = eventDateA;
        this.eventDateB = eventDateB;
        this.content = content;
        this.imageFile = imageFile;
    }

    @QueryProjection
    public EventDto(Long eventId, String title, String description, LocalDate eventDateA, LocalDate eventDateB, ImageFile imageFile) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDateA = eventDateA;
        this.eventDateB = eventDateB;
        this.imageFile = imageFile;
    }
}
