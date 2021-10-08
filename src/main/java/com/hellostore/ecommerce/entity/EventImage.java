package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_image_id")
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;

    private long fileSize;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public EventImage(String originalFileName, String fileName, String filePath, long fileSize, Event event) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.event = event;
    }
}
