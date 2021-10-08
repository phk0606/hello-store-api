package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String filePath;
    private String fileName;
    private long fileSize;
    private String originalFileName;

    @Setter
    private byte[] image;

    @QueryProjection
    public EventDto(Long eventId, String title, String description, LocalDate eventDateA, LocalDate eventDateB, String content, String filePath, String fileName, long fileSize, String originalFileName) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDateA = eventDateA;
        this.eventDateB = eventDateB;
        this.content = content;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.originalFileName = originalFileName;
    }

    @QueryProjection
    public EventDto(Long eventId, String title, String description, LocalDate eventDateA, LocalDate eventDateB, String filePath, String fileName, long fileSize) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDateA = eventDateA;
        this.eventDateB = eventDateB;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}
