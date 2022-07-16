package com.company.entity;

import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JoinColumn(name = "profile_id", nullable = false)
    @OneToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private Integer commentId;
    @JoinColumn(name = "comment_id", nullable = false, updatable = false,insertable = false)
    @OneToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    private CommentEntity comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
