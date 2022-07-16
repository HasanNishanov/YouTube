package com.company.entity;

import com.company.enums.ChannelStatus;
import com.company.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false, insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "order_number")
    private Integer order;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaylistStatus status = PlaylistStatus.ACTIVE;

    @Column
    private LocalDateTime updatedDate;

    @Column(name = "review_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, insertable = false, updatable = false)
    private AttachEntity attach;


}
