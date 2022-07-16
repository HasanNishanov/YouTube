package com.company.entity;

import com.company.enums.LikeStatus;
import com.company.enums.NotificationType;
import com.company.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_like")
public class VideoLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer profileId;
    @JoinColumn(name = "profile_id", nullable = false, updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoId;
    @JoinColumn(name = "video_id", nullable = false, updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private VideoEntity video;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
