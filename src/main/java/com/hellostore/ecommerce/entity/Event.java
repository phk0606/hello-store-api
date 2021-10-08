package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;
    private String description;
    private LocalDate eventDateA;
    private LocalDate eventDateB;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private EventImage eventImage;

    @Lob
    private String content;

    @Builder
    public Event(Long id, String title, String description, LocalDate eventDateA, LocalDate eventDateB, EventImage eventImage, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDateA = eventDateA;
        this.eventDateB = eventDateB;
        this.eventImage = eventImage;
        this.content = content;
    }
}
