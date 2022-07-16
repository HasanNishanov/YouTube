package com.company.entity;

import com.company.enums.VideoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video")
@NoArgsConstructor
public class VideoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", nullable = false, updatable = false, insertable = false)
    private AttachEntity attach;

    @Column(name = "review_id")
    private String reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", updatable = false, insertable = false)
    private AttachEntity review;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false, updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, updatable = false, insertable = false)
    private CategoryEntity category;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private Integer time;

    @Column(nullable = false)
    private Integer sharedCount = 0;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PUBLIC;

    @Column(name = "view_count")
    private Integer viewCount;

    public VideoEntity(String uuid) {
        this.uuid = uuid;
    }
}
